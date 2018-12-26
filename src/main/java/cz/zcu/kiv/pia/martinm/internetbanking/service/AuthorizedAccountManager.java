package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;

/**
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
public interface AuthorizedAccountManager {

    Account createAccount(User user);

    String generateAccountNumber();

    String generateCardNumber();

}
