package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.EntityNotFoundException;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.UserDto;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.UserDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link DefaultUserManager}.
 *
 * Date: 03.01.2019
 *
 * @author Martin Matas
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultUserManagerTest {

    @MockBean
    private UserDao userDao;

    @MockBean
    private PasswordEncoder encoder;

    @MockBean
    private MessageProvider messageProvider;

    private DefaultUserManager userManager;

    private User admin, customer, customer2;

    @Before
    public void setUp() {

        userManager = new DefaultUserManager(userDao, encoder, messageProvider);

        admin = new User("Jan", "Kratochvíl", "870515/2213", "kratochvil.jan@gmail.com");
        admin.setId(1);
        admin.setRole(User.Role.ADMIN.name());
        admin.setEnabled(true);

        customer = new User("Brad", "Pitt", "630913/1985", "brad@pitt.com");
        customer.setId(2);
        customer.setRole(User.Role.CUSTOMER.name());

        customer2 = new User("Filip", "Král", "940815/2118", "kral.filip@gmail.com");
        customer2.setId(3);
        customer2.setRole(User.Role.CUSTOMER.name());

    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByWrongUsername() {
        String unknownUsername = "nothing";

        when(userDao.findByUsername(unknownUsername)).thenReturn(null);
        userManager.loadUserByUsername(unknownUsername);
        verify(userDao, times(1)).findByUsername(unknownUsername);
    }

    @Test
    public void loadUserByUsername() {
        String username = "A18B1C9P";

        when(userDao.findByUsername(username)).thenReturn(customer);
        userManager.loadUserByUsername(username);
        verify(userDao, times(1)).findByUsername(username);
    }

    @Test(expected = AccessDeniedException.class)
    public void createAsCustomer() {
        AuthorizedUserManager aum = userManager.authorize(customer);

        aum.create(new UserDto());
    }

    @Test
    public void createAsAdmin() {
        UserDto userDraft = new UserDto();
        userDraft.setFirstName("Pepa");
        userDraft.setLastName("Stehlík");
        userDraft.setBirthNumber("19900505/2110");
        userDraft.setEmail("stehlik@seznam.cz");

        final User expected = new User("Pepa", "Stehlík", "19900505/2110", "stehlik@seznam.cz");

        when(userDao.save(any(User.class))).thenReturn(expected);

        AuthorizedUserManager aum = userManager.authorize(admin);
        aum.create(userDraft);

        verify(userDao, times(1)).save(expected);
    }

    @Test(expected = AccessDeniedException.class)
    public void removeAsCustomer() {
        AuthorizedUserManager aum = userManager.authorize(customer);
        aum.remove(1);
    }

    @Test
    public void removeCustomerAsAdmin() {
        AuthorizedUserManager aum = userManager.authorize(admin);

        User before = new User("Pepa", "Stehlík", "19900505/2110", "stehlik@seznam.cz");
        before.setRole(User.Role.CUSTOMER.name());

        User expected = new User("Pepa", "Stehlík", "19900505/2110", "stehlik@seznam.cz");
        expected.setRole(User.Role.CUSTOMER.name());
        expected.setEnabled(false);

        Integer userID = 1;

        when(userDao.save(any(User.class))).thenReturn(expected);
        when(userDao.findById(userID)).thenReturn(Optional.of(before));

        aum.remove(userID);

        verify(userDao, times(1)).save(expected);
        verify(userDao, times(1)).findById(userID);
    }

    @Test(expected = AccessDeniedException.class)
    public void removeAdminAsAdmin() {
        AuthorizedUserManager aum = userManager.authorize(admin);

        User before = new User("Pepa", "Stehlík", "19900505/2110", "stehlik@seznam.cz");
        before.setRole(User.Role.ADMIN.name());

        Integer userID = 1;

        when(userDao.findById(userID)).thenReturn(Optional.of(before));

        aum.remove(userID);

        verify(userDao, times(1)).findById(userID);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeNotExistingUserAsAdmin() {
        AuthorizedUserManager aum = userManager.authorize(admin);

        Integer userID = 1;

        when(userDao.findById(userID)).thenReturn(Optional.empty());

        aum.remove(userID);

        verify(userDao, times(1)).findById(userID);
    }

    @Test
    public void editOtherUserAsAdmin() {
        Integer userID = 1;
        UserDto userDraft = new UserDto();
        AuthorizedUserManager aum = userManager.authorize(admin);

        when(userDao.getOne(userID)).thenReturn(customer2);
        when(userDao.save(any(User.class))).thenReturn(customer2);

        aum.edit(userID, userDraft);

        verify(userDao, times(1)).getOne(userID);
        verify(userDao, times(1)).save(customer2);
    }

    @Test(expected = AccessDeniedException.class)
    public void editOtherUserAsCustomer() {
        AuthorizedUserManager aum = userManager.authorize(customer);

        Integer userID = 1;
        UserDto userDraft = new UserDto();

        when(userDao.getOne(userID)).thenReturn(customer2);

        aum.edit(userID, userDraft);

        verify(userDao, times(1)).getOne(userID);
    }

    @Test
    public void editSelf() {
        Integer userID = 5;
        UserDto userDraft = new UserDto();
        AuthorizedUserManager aum = userManager.authorize(customer2);

        when(userDao.getOne(userID)).thenReturn(customer2);
        when(userDao.save(any(User.class))).thenReturn(customer2);

        aum.edit(userID, userDraft);

        verify(userDao, times(1)).getOne(userID);
        verify(userDao, times(1)).save(customer2);
    }

    @Test(expected = AccessDeniedException.class)
    public void findAllUsersAsCustomer() {
        AuthorizedUserManager aum = userManager.authorize(customer);
        aum.findAllUsers();
    }

    @Test
    public void findAllUsersAsAdmin() {
        AuthorizedUserManager aum = userManager.authorize(admin);

        customer.setEnabled(false);
        customer2.setEnabled(true);
        List<User> users = new ArrayList<>();
        users.add(customer);
        users.add(customer2);

        when(userDao.findAll()).thenReturn(users);

        List<User> result = aum.findAllUsers();

        verify(userDao, times(1)).findAll();
        assertThat(result, hasSize(1));
    }
}