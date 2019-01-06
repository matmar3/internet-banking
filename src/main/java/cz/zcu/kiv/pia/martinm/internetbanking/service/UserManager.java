package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;

/**
 * Provides basic operations with users.
 *
 * Date: 16.12.2018
 *
 * @author Martin Matas
 */
public interface UserManager extends Authorizable<AuthorizedUserManager> {

    /**
     * Returns currently logged user.
     *
     * @return instance of currently logged user or null
     */
    User getCurrentUser();

}
