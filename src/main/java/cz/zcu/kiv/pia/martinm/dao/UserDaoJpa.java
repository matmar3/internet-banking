package cz.zcu.kiv.pia.martinm.dao;

import cz.zcu.kiv.pia.martinm.domain.User;

import javax.persistence.EntityManager;

/**
 * Date: 15.12.2018
 *
 * @author Martin Matas
 */
public class UserDaoJpa extends GenericDaoJpa<User, Integer> implements UserDao {

    /**
     *
     * @param em entity manager to be used by this dao
     */
    public UserDaoJpa(EntityManager em) {
        super(em, User.class);
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

}