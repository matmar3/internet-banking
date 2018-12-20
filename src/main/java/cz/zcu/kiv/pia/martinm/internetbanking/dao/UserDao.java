package cz.zcu.kiv.pia.martinm.internetbanking.dao;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Date: 15.12.2018
 *
 * @author Martin Matas
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User findByLogin(String login);

}
