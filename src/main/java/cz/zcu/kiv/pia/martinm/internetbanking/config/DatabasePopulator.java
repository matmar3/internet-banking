package cz.zcu.kiv.pia.martinm.internetbanking.config;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionDto;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.AccountDao;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.UserDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.AccountManager;
import cz.zcu.kiv.pia.martinm.internetbanking.service.AuthorizedAccountManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * Date: 29.12.2018
 *
 * @author Martin Matas
 */
@Configuration
public class DatabasePopulator {

    private UserDao userDao;

    private AccountDao accountDao;

    private AccountManager accountManager;

    private PasswordEncoder encoder;

    public DatabasePopulator(UserDao userDao, AccountDao accountDao, AccountManager accountManager, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.accountManager = accountManager;
        this.encoder = encoder;
    }

    @PostConstruct
    @Transactional
    void populateDB() {

        // Users

        User admin = new User("Jan", "Kratochvíl", "870515/2213", "kratochvil.jan@gmail.com");
        admin.setUsername("Admin001");
        admin.setPassword(encoder.encode("1234"));
        admin.setRole(User.Role.ADMIN.name());
        admin.setEnabled(true);
        userDao.save(admin);

        User user1 = new User("Brad", "Pitt", "630913/1985", "brad@pitt.com");
        user1.setUsername("User0001");
        user1.setPassword(encoder.encode("0001"));
        user1.setRole(User.Role.CUSTOMER.name());
        user1.setEnabled(true);
        userDao.save(user1);

        User user2 = new User("Filip", "Král", "940815/2118", "kral.filip@gmail.com");
        user2.setAddress("Pražská", "1023", "100 02", "Praha");
        user2.setUsername("User0002");
        user2.setPassword(encoder.encode("0002"));
        user2.setRole(User.Role.CUSTOMER.name());
        user2.setEnabled(true);
        userDao.save(user2);

        // Accounts

        AuthorizedAccountManager authorizedAccountManager = accountManager.authorize(admin);

        Account account1 = new Account("USD", authorizedAccountManager.generateAccountNumber(), authorizedAccountManager.generateCardNumber(), user1);
        account1.setBalance(new BigDecimal(7358));
        accountDao.save(account1);

        Account account2 = new Account("CZK", authorizedAccountManager.generateAccountNumber(), authorizedAccountManager.generateCardNumber(), user2);
        account2.setBalance(new BigDecimal(130000));
        accountDao.save(account2);

        // Transactions

        AuthorizedAccountManager authorizedAccountManager1 = accountManager.authorize(user1);
        AuthorizedAccountManager authorizedAccountManager2 = accountManager.authorize(user2);

        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 10);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 7200);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 45);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 173);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 129);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 1200);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 4500);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 10);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 15);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 43);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 12);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 11);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 400);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 329);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 400);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 1200);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 329);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 8215);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 43);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 700);


        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 10);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 7200);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 45);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 173);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 129);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 1200);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 4500);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 10);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 15);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 43);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 12);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 11);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 400);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 329);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 400);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 1200);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 329);
        generateTransaction(authorizedAccountManager2, account2.getAccountNumber(), account1.getAccountNumber(), 8215);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 43);
        generateTransaction(authorizedAccountManager1, account1.getAccountNumber(), account2.getAccountNumber(), 700);

        TransactionDto transactionTemplate = new TransactionDto();
        transactionTemplate.setSenderAccountNumber(account1.getAccountNumber());
        transactionTemplate.setReceiverAccountNumber(account2.getAccountNumber());
        transactionTemplate.setSentAmount(new BigDecimal(2121));

        authorizedAccountManager1.createTemplate(transactionTemplate, user1);

    }

    private void generateTransaction(AuthorizedAccountManager aam, String senderAccountNumber, String receiverAccountNumber, int amount) {
        TransactionDto transaction = new TransactionDto();
        transaction.setSenderAccountNumber(senderAccountNumber);
        transaction.setReceiverAccountNumber(receiverAccountNumber);
        transaction.setSentAmount(new BigDecimal(amount));

        aam.performTransaction(transaction);
    }

}
