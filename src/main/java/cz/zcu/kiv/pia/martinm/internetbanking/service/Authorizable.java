package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;

/**
 * Provides user opportunity to authorize himself
 * and gain access to operations of authorized class.
 *
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
public interface Authorizable<T> {

    /**
     * Creates instance of authorized class for some user.
     *
     * @param user - user which can use methods of authorized class
     * @return - instance of authorized class
     */
    T authorize(User user);

}
