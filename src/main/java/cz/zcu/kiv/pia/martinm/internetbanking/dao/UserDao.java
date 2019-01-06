package cz.zcu.kiv.pia.martinm.internetbanking.dao;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User data access object.
 *
 * Date: 15.12.2018
 *
 * @author Martin Matas
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    /**
     * Finds user by username. If no user with given username exists, then returns null.
     *
     * @param login - user's login (=username)
     * @return founded user or null
     */
    User findByUsername(String login);

}
