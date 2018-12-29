package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.Bank;
import cz.zcu.kiv.pia.martinm.internetbanking.RandomNumberGenerator;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionDto;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.AccountDao;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.TransactionDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;

/**
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
@Service
public class DefaultAccountManager implements AccountManager {

    private AccountDao accountDao;

    private TransactionDao transactionDao;

    public DefaultAccountManager(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
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
        public Account createAccount(AccountDto newAccount, User owner) {
            if (owner.getRole().equals(User.Role.ADMIN.name())) {
                throw new AccessDeniedException("Admin cannot have an account");
            }
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !owner.getId().equals(currentUser.getId())) {
                throw new AccessDeniedException("Cannot create account for other user");
            }

            String cardNumber = generateCardNumber();
            String accountNumber = generateAccountNumber();
            return accountDao.save(new Account(
               newAccount.getCurrency().getCurrencyCode(),
               accountNumber,
               cardNumber,
               owner
            ));
        }

        @Override
        public Account findAccountById(int id) {
            Account account = accountDao.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Account with given ID not found."));

            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(account.getUser().getId())) {
                throw new AccessDeniedException("Cannot show other user's account");
            }

            return account;
        }

        @Override
        public List<Transaction> findAllTransactionsByAccount(Account account) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(account.getUser().getId())) {
                throw new AccessDeniedException("Cannot show other user's accounts");
            }

            return transactionDao.findAllByAccount(account);
        }

        @Override
        public Transaction performTransaction(TransactionDto transactionDto) {
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

            // TODO validace transake v Controlleru jeste pred timto vsim

            return updateEntities(sender, transactionDto.getSentAmount(), receiver, receivedAmount, newTransaction);
        }

        private Transaction updateEntities(Account sender, BigDecimal sentAmount, Account receiver, BigDecimal receivedAmount, Transaction transaction) {
            transaction = transactionDao.save(transaction);

            if (sender != null) {
                sender.setBalance(sender.getBalance().subtract(sentAmount));
                accountDao.save(sender);
            }
            if (receiver != null) {
                receiver.setBalance(receiver.getBalance().add(receivedAmount));
                accountDao.save(receiver);
            }

            return transaction;
        }

        @Override
        public String generateAccountNumber() {
            String firstPart = RandomNumberGenerator.generate(6);
            String secondPart = RandomNumberGenerator.generate(10);
            return String.format(ACCOUNT_NUMBER_FORMAT, firstPart, secondPart, Bank.CODE);
        }

        @Override
        public String generateCardNumber() {
            return String.format(CARD_NUMBER_FORMAT, RandomNumberGenerator.generate(16));
        }
    }

}
