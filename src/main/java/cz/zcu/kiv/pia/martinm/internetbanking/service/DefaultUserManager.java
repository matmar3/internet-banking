package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.dao.UserDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Date: 23.12.2018
 *
 * @author Martin Matas
 */
@Service("userManager")
public class DefaultUserManager implements UserManager, UserDetailsService {

    private UserDao userDao;

    public DefaultUserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getCurrentUser() {
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (auth instanceof User) {
            return userDao.findById(((User) auth).getId()).orElse(null);
        }
        return null;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        return userDao.findByUsername(username);
    }

}
