package cz.zcu.kiv.pia.martinm.dao;

import cz.zcu.kiv.pia.martinm.domain.User;

/**
 * Date: 15.12.2018
 *
 * @author Martin Matas
 */
public interface UserDao extends DataAccessObject<User, Integer> {

    /**
     * Creates user from given instance.
     *
     * @param user - instance of User to save
     */
    User create(User user);

}
