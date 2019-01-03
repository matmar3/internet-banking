package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.EntityNotFoundException;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.AccountDao;
import cz.zcu.kiv.pia.martinm.internetbanking.dao.TransactionDao;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Date: 03.01.2019
 *
 * @author Martin Matas
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultAccountManagerTest {

    @MockBean
    private AccountDao accountDao;

    @MockBean
    private TransactionDao transactionDao;

    @MockBean
    private MessageProvider messageProvider;

    private DefaultAccountManager accountManager;

    private User admin, customer, customer2;

    @Before
    public void setUp() {
        accountManager = new DefaultAccountManager(accountDao, transactionDao, messageProvider);

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

    @Test
    public void createAccountAsAdmin() {
        Account account = new Account("CZK", "1020304050/2700", "1020304050607080", customer);
        AuthorizedAccountManager aam = accountManager.authorize(admin);

        when(accountDao.save(account)).thenReturn(account);
        aam.createAccount(new AccountDto(), customer);
        verify(accountDao, times(1)).save(account);
    }

    @Test(expected = AccessDeniedException.class)
    public void createAccountAsCustomer() {
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        aam.createAccount(new AccountDto(), customer2);
    }

    @Test(expected = AccessDeniedException.class)
    public void createAccountForAdmin() {
        AuthorizedAccountManager aam = accountManager.authorize(admin);
        aam.createAccount(new AccountDto(), admin);
    }

    @Test
    public void findAccountById() {
        Account account = new Account("CZK", "1020304050/2700", "1020304050607080", customer);
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        Integer someID = 1;

        when(accountDao.findById(someID)).thenReturn(Optional.of(account));
        assertThat(aam.findAccountById(someID), is(equalTo(account)));
        verify(accountDao, times(1)).findById(someID);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findNoExistingAccountById() {
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        Integer someID = 1;

        when(accountDao.findById(someID)).thenReturn(Optional.empty());
        aam.findAccountById(someID);
        verify(accountDao, times(1)).findById(someID);
    }

    @Test(expected = AccessDeniedException.class)
    public void findForeignAccountById() {
        Account account = new Account("CZK", "1020304050/2700", "1020304050607080", customer2);
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        Integer someID = 1;

        when(accountDao.findById(someID)).thenReturn(Optional.of(account));
        aam.findAccountById(someID);
        verify(accountDao, times(1)).findById(someID);
    }

    @Test
    public void findAllTransactionsByAccount_returnsEmptyPage() {
        Account account = new Account("CZK", "1020304050/2700", "1020304050607080", customer);
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        PageRequest pageRequest = PageRequest.of(0, 20);

        when(transactionDao.findAllByAccount(account, pageRequest)).thenReturn(Page.empty());
        assertThat(aam.findAllTransactionsByAccount(account, pageRequest).getContent(), hasSize(0));
        verify(transactionDao, times(1)).findAllByAccount(account, pageRequest);
    }

    @Test
    public void findAllTransactionsByAccount_returnsOne() {
        Account account = new Account("CZK", "1020304050/2700", "1020304050607080", customer);
        Transaction transaction = new Transaction("100", null, "1020304050/1000", "100", account, "1020304050/2700", new Date(), new Date(), "", "", "", "");
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        PageRequest pageRequest = PageRequest.of(0, 20);
        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction);

        when(transactionDao.findAllByAccount(account, pageRequest)).thenReturn(new PageImpl<>(expected));

        Page<Transaction> result = aam.findAllTransactionsByAccount(account, pageRequest);
        assertThat(result.getContent(), hasSize(1));
        assertThat(result.getTotalPages(), is(equalTo(1)));
        assertThat(result.getContent().get(0).getSender(), is(equalTo(account)));

        verify(transactionDao, times(1)).findAllByAccount(account, pageRequest);
    }

    @Test(expected = AccessDeniedException.class)
    public void findAllForeignTransactionsByAccount() {
        AuthorizedAccountManager aam = accountManager.authorize(customer2);
        Account account = new Account("CZK", "1020304050/2700", "1020304050607080", customer);
        PageRequest pageRequest = PageRequest.of(0, 20);

        aam.findAllTransactionsByAccount(account, pageRequest);
    }

    @Test
    public void findAllTransactionsByAccountAsAdmin() {
        AuthorizedAccountManager aam = accountManager.authorize(admin);
        Account account = new Account("CZK", "1020304050/2700", "1020304050607080", customer);
        PageRequest pageRequest = PageRequest.of(0, 20);

        when(transactionDao.findAllByAccount(account, pageRequest)).thenReturn(Page.empty());
        assertThat(aam.findAllTransactionsByAccount(account, pageRequest).getContent(), hasSize(0));
        verify(transactionDao, times(1)).findAllByAccount(account, pageRequest);
    }

    /*@Test
    public void performTransaction() {

    }*/

}