package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.Bank;
import cz.zcu.kiv.pia.martinm.internetbanking.RandomNumberGenerator;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionTemplateDto;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.AccountDao;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.TransactionDao;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.TransactionTemplateDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.TransactionTemplate;
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

    private TransactionTemplateDao transactionTemplateDao;

    public DefaultAccountManager(AccountDao accountDao, TransactionDao transactionDao, TransactionTemplateDao transactionTemplateDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.transactionTemplateDao = transactionTemplateDao;
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
            Account account = accountDao.findById(id).orElse(null);

            if (account == null) return null;

            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(account.getUser().getId())) {
                throw new AccessDeniedException("Cannot show other user's account");
            }

            return account;
        }

        @Override
        public Page<Transaction> findAllTransactionsByAccount(Account account, Pageable pageable) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(account.getUser().getId())) {
                throw new AccessDeniedException("Cannot show other user's accounts");
            }

            return transactionDao.findAllByAccount(account, pageable);
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

            return updateEntities(sender, transactionDto.getSentAmount(), receiver, receivedAmount, newTransaction);
        }

        @Override
        public boolean isTransactionValid(TransactionDto transaction, BindingResult result) {
            Account sender = accountDao.findByAccountNumber(transaction.getSenderAccountNumber());
            boolean valid = true;

            if (transaction.getSenderAccountNumber().equals(transaction.getReceiverAccountNumber())) {
                result.addError(new FieldError("newTransaction", "receiverAccountNumber", "Receiver cannot be the same account as sender."));
                valid = false;
            }

            if (sender.getBalance().compareTo(transaction.getSentAmount()) < 0) {
                result.addError(new FieldError("newTransaction", "sentAmount", "Sender does not have enough money."));
                valid = false;
            }

            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !sender.getUser().getId().equals(currentUser.getId()))
                throw new AccessDeniedException("Cannot send transaction from other user's account");

            return valid;
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
        public TransactionTemplate createTemplate(TransactionTemplateDto newTemplate, User user) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(user.getId())) {
                throw new AccessDeniedException("Cannot creates template for other users");
            }

            TransactionTemplate template = new TransactionTemplate(
                    newTemplate.getTemplateName(),
                    newTemplate.getReceiverAccountNumber(),
                    newTemplate.getSentAmount(),
                    newTemplate.getSenderAccountNumber(),
                    newTemplate.getDueDate(),
                    newTemplate.getConstantSymbol(),
                    newTemplate.getVariableSymbol(),
                    newTemplate.getSpecificSymbol(),
                    newTemplate.getMessage(),
                    user
            );

            return transactionTemplateDao.save(template);
        }

        @Override
        public List<TransactionTemplate> findAllTransactionTemplatesByUser(User user) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(user.getId())) {
                throw new AccessDeniedException("Cannot show other user's transaction templates");
            }

            return transactionTemplateDao.findAllByOwner(user);
        }

        @Override
        public TransactionTemplate findTransactionTemplateById(User user, Integer id) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(user.getId())) {
                throw new AccessDeniedException("Cannot show other user's transaction templates");
            }

            return transactionTemplateDao.findByOwnerAndId(user, id);
        }

        @Override
        public void removeTemplate(Integer id) {
            TransactionTemplate template = transactionTemplateDao.findById(id).orElse(null);

            if (template == null) {
                // TODO 404 ?
                throw new AccessDeniedException("Cannot remove not existing template");
            }

            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(template.getOwner().getId())) {
                throw new AccessDeniedException("Cannot remove other user's templates");
            }

            transactionTemplateDao.delete(template);
        }

        @Override
        public TransactionTemplate modifyTemplate(TransactionTemplateDto modifyTemplate) {
            TransactionTemplate template = transactionTemplateDao.findById(modifyTemplate.getId()).orElse(null);

            if (template == null) {
                throw new AccessDeniedException("Cannot remove not existing template");
            }

            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(template.getOwner().getId())) {
                throw new AccessDeniedException("Cannot remove other user's templates");
            }

            template.setTemplateName(modifyTemplate.getTemplateName());
            template.setReceiverAccountNumber(modifyTemplate.getReceiverAccountNumber());
            template.setSenderAccountNumber(modifyTemplate.getSenderAccountNumber());
            template.setSentAmount(modifyTemplate.getSentAmount());
            template.setDueDate(modifyTemplate.getDueDate());
            template.setConstantSymbol(modifyTemplate.getConstantSymbol());
            template.setVariableSymbol(modifyTemplate.getVariableSymbol());
            template.setSpecificSymbol(modifyTemplate.getSpecificSymbol());
            template.setMessage(modifyTemplate.getMessage());
            return transactionTemplateDao.save(template);
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
