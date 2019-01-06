package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.EntityNotFoundException;
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

import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of UserManager and UserDetailsService,
 * for more details see {@link TuringTest} or {@link UserDetailsService}.
 *
 * Date: 23.12.2018
 *
 * @author Martin Matas
 */
@Service("userManager")
public class DefaultUserManager implements UserManager, UserDetailsService {

    private UserDao userDao;

    private PasswordEncoder encoder;

    private MessageProvider messageProvider;

    public DefaultUserManager(UserDao userDao, PasswordEncoder encoder, MessageProvider messageProvider) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.messageProvider = messageProvider;
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

        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(messageProvider.getMessage("error.usernameNotFound"));
        }

        return user;
    }

    @Override
    public AuthorizedUserManager authorize(User user) {
        return new DefaultAuthorizedUserManager(user);
    }

    /**
     * Implementation of AuthorizedUserManager, for more details see {@link AuthorizedUserManager}.
     */
    private class DefaultAuthorizedUserManager implements AuthorizedUserManager {

        private User currentUser;

        DefaultAuthorizedUserManager(User currentUser) {
            this.currentUser = currentUser;
        }

        @Override
        public User remove(Integer id) {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.user.removeOtherUser"));
            }

            User user = userDao.findById(id).orElse(null);
            if (user == null) {
                throw new EntityNotFoundException(messageProvider.getMessage("error.entityNotFound", User.class.getSimpleName()));
            }

            if (user.getRole().equals(User.Role.ADMIN.name())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.user.removeAdmin"));
            }

            user.setEnabled(false);
            return userDao.save(user);
        }

        @Override
        public User edit(Integer id, UserDto modifiedUser) {
            User user = userDao.getOne(id);

            if (!currentUser.getRole().equals(User.Role.ADMIN.name()) && !currentUser.getId().equals(user.getId())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.user.editOtherUser"));
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
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.user.createAccount"));
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

            return user;
        }

        @Override
        public List<User> findAllUsers() {
            if (!currentUser.getRole().equals(User.Role.ADMIN.name())) {
                throw new AccessDeniedException(messageProvider.getMessage("error.accessDenied.showOtherUserData"));
            }

            List<User> users = userDao.findAll();
            users.removeIf(u -> !u.isEnabled());
            return users;
        }

        @Override
        public String generatePassword() {
            return RandomStringGenerator.generateNumeric(4);
        }

        @Override
        public String generateUsername() {
            return RandomStringGenerator.generateAlphanumeric(8);
        }

    }
}
