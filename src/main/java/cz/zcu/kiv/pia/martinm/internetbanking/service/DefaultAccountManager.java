package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.Bank;
import cz.zcu.kiv.pia.martinm.internetbanking.EntityNotFoundException;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionDto;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.AccountDao;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.TransactionDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

/**
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
@Service
public class DefaultAccountManager implements AccountManager {

    private AccountDao accountDao;

    private TransactionDao transactionDao;

    private MessageProvider messageProvider;

    public DefaultAccountManager(
            AccountDao accountDao,
            TransactionDao transactionDao,
            MessageProvider messageProvider) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.messageProvider = messageProvider;
    }

    @Override
    public AuthorizedAccountManager authorize(User user) {
        return new DefaultAuthorizedAccountManager(user);
    }

    private class DefaultAuthorizedAccountManager implements AuthorizedAccountManager {

        private User currentUser;

        private final String ACCOUNT_NUMBER_FORMAT = "%1$6s-%2$10s/%3$d";

        private final String CARD_NUMBER_FORMAT = "%1$16s";

        DefaultAuthorizedAccountManager(User currentUser) {
            this.currentUser = currentUser;
        }

        @Override
        public void createAccount(AccountDto newAccount, User owner) {
            if (owner.getRole().equals(User.Role.ADMIN.name())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.adminCannotHaveAccount"));
            }
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !owner.getId().equals(currentUser.getId())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.cannotCreateAccountForOtherUser"));
            }

            String cardNumber = generateCardNumber();
            String accountNumber = generateAccountNumber();
            accountDao.save(new Account(
               newAccount.getCurrency(),
               accountNumber,
               cardNumber,
               owner
            ));
        }

        @Override
        public Account findAccountById(int id) {
            Account account = accountDao.findById(id).orElse(null);

            if (account == null) {
                throw new EntityNotFoundException(messageProvider.getMessage("error.entityNotFound", Account.class));
            }

            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(account.getUser().getId())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.cannotShowOtherUserData"));
            }

            return account;
        }

        @Override
        public Page<Transaction> findAllTransactionsByAccount(Account account, Pageable pageable) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(account.getUser().getId())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.cannotShowOtherUserData"));
            }

            return transactionDao.findAllByAccount(account, pageable);
        }

        @Override
        public void performTransaction(TransactionDto transactionDto) {
            BigDecimal receivedAmount;
            String receiverCurrency;

            Account receiver = accountDao.findByAccountNumber(transactionDto.getReceiverAccountNumber());
            Account sender = accountDao.findByAccountNumber(transactionDto.getSenderAccountNumber());

            if (receiver == null) {
                receivedAmount = transactionDto.getSentAmount();
                receiverCurrency = sender.getCurrency();
            }
            else {
                receivedAmount = CurrencyUtils.convert(
                        transactionDto.getSentAmount(),
                        Currency.getInstance(sender.getCurrency()),
                        Currency.getInstance(receiver.getCurrency())
                );
                receiverCurrency = receiver.getCurrency();
            }

            Transaction newTransaction = new Transaction(
                    CurrencyUtils.format(receivedAmount, receiverCurrency),
                    receiver,
                    transactionDto.getReceiverAccountNumber(),
                    CurrencyUtils.format(transactionDto.getSentAmount(), sender.getCurrency()),
                    sender,
                    sender.getAccountNumber(),
                    new Date(),
                    (transactionDto.getDueDate() == null) ? new Date() : transactionDto.getDueDate(),
                    transactionDto.getConstantSymbol(),
                    transactionDto.getVariableSymbol(),
                    transactionDto.getSpecificSymbol(),
                    transactionDto.getMessage()
            );

            updateEntities(sender, transactionDto.getSentAmount(), receiver, receivedAmount, newTransaction);
        }

        private void updateEntities(Account sender, BigDecimal sentAmount, Account receiver, BigDecimal receivedAmount, Transaction transaction) {
            if (sender != null) {
                sender.setBalance(sender.getBalance().subtract(sentAmount));
                accountDao.save(sender);
            }
            if (receiver != null) {
                receiver.setBalance(receiver.getBalance().add(receivedAmount));
                accountDao.save(receiver);
            }

            transactionDao.save(transaction);
        }

        @Override
        public boolean isTransactionValid(TransactionDto transaction, BindingResult result) {
            Account sender = accountDao.findByAccountNumber(transaction.getSenderAccountNumber());
            boolean valid = true;

            if (sender == null) {
                throw new EntityNotFoundException(messageProvider.getMessage("error.entityNotFound", Account.class));
            }

            if (!currentUser.getId().equals(sender.getUser().getId())) {
                throw new AccessDeniedException(
                        messageProvider.getMessage("error.accessDenied.cannotPerformTransaction.differentAccount")
                );
            }

            if (transaction.getSenderAccountNumber().equals(transaction.getReceiverAccountNumber())) {
                result.addError(
                        new FieldError("newTransaction", "receiverAccountNumber", messageProvider.getMessage("error.fieldMessage.sameAccounts"))
                );
                valid = false;
            }

            if (sender.getBalance().compareTo(transaction.getSentAmount()) < 0) {
                result.addError(
                        new FieldError("newTransaction", "sentAmount", messageProvider.getMessage("error.fieldMessage.notEnoughMoney"))
                );
                valid = false;
            }

            return valid;
        }

        @Override
        public String generateAccountNumber() {
            String firstPart = RandomStringGenerator.generateNumeric(6);
            String secondPart = RandomStringGenerator.generateNumeric(10);
            return String.format(ACCOUNT_NUMBER_FORMAT, firstPart, secondPart, Bank.CODE);
        }

        @Override
        public String generateCardNumber() {
            return String.format(CARD_NUMBER_FORMAT, RandomStringGenerator.generateNumeric(16));
        }
    }

}
