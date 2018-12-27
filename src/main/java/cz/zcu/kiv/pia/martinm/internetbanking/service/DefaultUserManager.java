package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.UserDto;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.UserDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebInputException;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Date: 23.12.2018
 *
 * @author Martin Matas
 */
@Service("userManager")
public class DefaultUserManager implements UserManager, UserDetailsService {

    private UserDao userDao;

    private PasswordEncoder encoder;

    public DefaultUserManager(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
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

    @Override
    public AuthorizedUserManager authorize(User user) {
        return new DefaultAuthorizedUserManager(user);
    }

    private class DefaultAuthorizedUserManager implements AuthorizedUserManager {

        private final String USERNAME_FORMAT = "User%04d";

        private User currentUser;

        DefaultAuthorizedUserManager(User currentUser) {
            this.currentUser = currentUser;
        }

        @Override
        public User remove(Integer id) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name())) {
                throw new AccessDeniedException("Only admin can remove a user");
            }

            User user = userDao.findById(id).orElse(null);
            if (user == null) {
                throw new RuntimeException("User not exists."); // TODO UserNotFOundException
            }

            if (user.getRole().equals(User.Role.ADMIN.name())) {
                throw new AccessDeniedException("Cannot remove admin's account");
            }

            user.setEnabled(false);
            return userDao.save(user);
        }

        @Override
        public User edit(Integer id, UserDto modifiedUser) {
            User user = userDao.getOne(id);

            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(user.getId())) {
                throw new AccessDeniedException("User cannot edit other user's account");
            }

            user.setMobileNumber(modifiedUser.getMobileNumber());
            user.setBirthDate(modifiedUser.getBirthDate());
            user.setAddress(
                    modifiedUser.getStreet(),
                    modifiedUser.getHouseNumber(),
                    modifiedUser.getZipCode(),
                    modifiedUser.getCity()
            );
            return userDao.save(user);
        }

        @Override
        public User create(UserDto newUser){
            if (!currentUser.getRole().equals(User.Role.ADMIN.name())) {
                throw new AccessDeniedException("Users are not allowed to create new accounts");
            }

            if (newUser.getFirstName() == null || newUser.getLastName() == null || newUser.getBirthNumber() == null || newUser.getEmail() == null) {
                throw new ServerWebInputException("Mandatory parameters not filled.");
            }

            String rawPassword = generatePassword();
            String username = generateUsername();
            User user = new User(
                    newUser.getFirstName(),
                    newUser.getLastName(),
                    newUser.getBirthNumber(),
                    newUser.getEmail());
            user.setAddress(
                    newUser.getStreet(),
                    newUser.getHouseNumber(),
                    newUser.getZipCode(),
                    newUser.getCity());
            user.setBirthDate(newUser.getBirthDate());
            user.setMobileNumber(newUser.getMobileNumber());
            user.setRole(User.Role.CUSTOMER.name());
            user.setUsername(username);
            user.setPassword(encoder.encode(rawPassword));
            userDao.save(user);

            /*try {
                notificationService.sendWelcome(user, rawPassword)
            } catch (e) {
                throw new RuntimeException(
                        "User has been created but email notification failed for reason: $e", e)
            }*/

            return user;
        }

        @Override
        public List<User> findAllUsers() {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name())) {
                throw new AccessDeniedException("Users are not allowed to see other accounts");
            }

            List<User> users = userDao.findAll();
            users.removeIf(u -> !u.isEnabled());
            return users;
        }

        @Override
        public String generatePassword() {
            return String.format("%04d", userDao.count()); // TODO zapnout RandomNumberGenerator.generate(4);
        }

        @Override
        public String generateUsername() {
            return String.format(USERNAME_FORMAT, userDao.count());
        }

    }
}
