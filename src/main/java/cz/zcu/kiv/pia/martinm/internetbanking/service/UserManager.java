package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.dao.UserDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.stereotype.Service;

/**
 * Date: 16.12.2018
 *
 * @author Martin Matas
 */
@Service
public class UserManager {

    private UserDao userDao;

    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUser(String login) {
        return userDao.findByLogin(login);
    }

}
