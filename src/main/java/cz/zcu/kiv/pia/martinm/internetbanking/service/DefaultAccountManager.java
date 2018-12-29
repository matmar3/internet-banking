package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.RandomNumberGenerator;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.AccountDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
@Service
public class DefaultAccountManager implements AccountManager {

    private AccountDao accountDao;

    public DefaultAccountManager(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public AuthorizedAccountManager authorize(User user) {
        return new DefaultAuthorizedAccountManager(user);
    }

    private class DefaultAuthorizedAccountManager implements AuthorizedAccountManager {

        private User currentUser;

        private final String ACCOUNT_NUMBER_FORMAT = "%1$6s-%2$10s";

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
            return null;
        }

        @Override
        public List<Transaction> findAllTransactionsByAccount(Account account) {
            return null;
        }

        @Override
        public Transaction performTransaction(Account sender, String receiver, Integer amount, ZonedDateTime date, String description) {
            return null;
        }

        @Override
        public String generateAccountNumber() {
            String firstPart = RandomNumberGenerator.generate(6);
            String secondPart = RandomNumberGenerator.generate(10);
            return String.format(ACCOUNT_NUMBER_FORMAT, firstPart, secondPart);
        }

        @Override
        public String generateCardNumber() {
            return String.format(CARD_NUMBER_FORMAT, RandomNumberGenerator.generate(16));
        }
    }

}
