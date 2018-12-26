package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.RandomNumberGenerator;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.AccountDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

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
        public Account createAccount(User user) {
            if (user.getRole().equals(User.Role.ADMIN.name())) {
                throw new AccessDeniedException("Admin cannot have an account");
            }
            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !user.getId().equals(currentUser.getId())) {
                throw new AccessDeniedException("Cannot create account for other user");
            }

            String cardNumber = generateCardNumber();
            String accountNumber = generateAccountNumber();
            return accountDao.save(new Account(
               accountNumber,
               cardNumber,
               user
            ));
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
