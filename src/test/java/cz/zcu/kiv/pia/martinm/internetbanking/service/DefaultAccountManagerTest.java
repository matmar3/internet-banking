package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.EntityNotFoundException;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionDto;
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
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void performTransactionToUnknownAccount() {
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        final BigDecimal amount = new BigDecimal(1);
        final String senderAccountNumber = "1020304050/2700";
        final String receiverAccountNumber = "1020304050/1000";

        Account account = new Account("CZK", "1020304050/2700", "1020304050607080", customer);
        TransactionDto newTransaction = new TransactionDto();
        newTransaction.setReceiverAccountNumber(receiverAccountNumber);
        newTransaction.setSenderAccountNumber(senderAccountNumber);
        newTransaction.setSentAmount(amount);


        when(accountDao.findByAccountNumber(senderAccountNumber)).thenReturn(account);
        when(accountDao.findByAccountNumber(receiverAccountNumber)).thenReturn(null);

        aam.performTransaction(newTransaction);

        verify(accountDao, times(1)).findByAccountNumber(senderAccountNumber);
        verify(accountDao, times(1)).findByAccountNumber(receiverAccountNumber);
    }

    @Test
    public void performTransactionToKnownAccount() {
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        final BigDecimal amount = new BigDecimal(1);
        final String senderAccountNumber = "1020304050/2700";
        final String receiverAccountNumber = "1020304010/2700";

        Account account = new Account("CZK", senderAccountNumber, "1020304050607080", customer);
        Account account2 = new Account("CZK", receiverAccountNumber, "8070605040302010", customer2);
        TransactionDto newTransaction = new TransactionDto();
        newTransaction.setReceiverAccountNumber(receiverAccountNumber);
        newTransaction.setSenderAccountNumber(senderAccountNumber);
        newTransaction.setSentAmount(amount);


        when(accountDao.findByAccountNumber(senderAccountNumber)).thenReturn(account);
        when(accountDao.findByAccountNumber(receiverAccountNumber)).thenReturn(account2);

        aam.performTransaction(newTransaction);

        verify(accountDao, times(1)).findByAccountNumber(senderAccountNumber);
        verify(accountDao, times(1)).findByAccountNumber(receiverAccountNumber);
    }

    @Test(expected = AccessDeniedException.class)
    public void isTransactionValid_transactionPerformedAsAdmin() {
        AuthorizedAccountManager aam = accountManager.authorize(admin);
        final String senderAccountNumber = "1020304050/2700";

        Account account = new Account("CZK", senderAccountNumber, "1020304050607080", customer);
        TransactionDto newTransaction = new TransactionDto();
        newTransaction.setSenderAccountNumber(senderAccountNumber);

        when(accountDao.findByAccountNumber(senderAccountNumber)).thenReturn(account);
        aam.isTransactionValid(newTransaction, null);
        verify(accountDao, times(1)).findByAccountNumber(senderAccountNumber);
    }

    @Test(expected = EntityNotFoundException.class)
    public void isTransactionValid_senderNotExists() {
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        final String senderAccountNumber = "1020304050/2700";

        TransactionDto newTransaction = new TransactionDto();
        newTransaction.setSenderAccountNumber(senderAccountNumber);

        when(accountDao.findByAccountNumber(senderAccountNumber)).thenReturn(null);
        aam.isTransactionValid(newTransaction, null);
        verify(accountDao, times(1)).findByAccountNumber(senderAccountNumber);
    }

    @Test
    public void isTransactionValid_receiverIsSameAccount() {
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        final String accountNumber = "1020304050/2700";
        final BigDecimal amount = new BigDecimal(1);

        Account account = new Account("CZK", accountNumber, "1020304050607080", customer);
        account.setBalance(amount);
        TransactionDto newTransaction = new TransactionDto();
        newTransaction.setSenderAccountNumber(accountNumber);
        newTransaction.setReceiverAccountNumber(accountNumber);
        newTransaction.setSentAmount(amount);

        when(accountDao.findByAccountNumber(accountNumber)).thenReturn(account);
        BindingResult bindingResult = mock(BindingResult.class);
        bindingResult.addError(null);

        boolean result = aam.isTransactionValid(newTransaction, bindingResult);
        assertThat(result, is(false));

        verify(accountDao, times(1)).findByAccountNumber(accountNumber);
        verify(bindingResult, times(1)).addError(null);
    }

    @Test
    public void isTransactionValid_senderDoesNotHaveEnoughMoney() {
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        final String senderAccountNumber = "1020304050/2700";
        final String receiverAccountNumber = "1020304010/2700";
        final BigDecimal amount = new BigDecimal(1);

        Account account = new Account("CZK", senderAccountNumber, "1020304050607080", customer);
        TransactionDto newTransaction = new TransactionDto();
        newTransaction.setSenderAccountNumber(senderAccountNumber);
        newTransaction.setReceiverAccountNumber(receiverAccountNumber);
        newTransaction.setSentAmount(amount);

        when(accountDao.findByAccountNumber(senderAccountNumber)).thenReturn(account);
        BindingResult bindingResult = mock(BindingResult.class);
        bindingResult.addError(null);

        boolean result = aam.isTransactionValid(newTransaction, bindingResult);
        assertThat(result, is(false));

        verify(accountDao, times(1)).findByAccountNumber(senderAccountNumber);
        verify(bindingResult, times(1)).addError(null);
    }

    @Test
    public void isTransactionValid_Valid() {
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        final String senderAccountNumber = "1020304050/2700";
        final String receiverAccountNumber = "1020304010/2700";
        final BigDecimal amount = new BigDecimal(15);
        final BigDecimal balance = new BigDecimal(1500);

        Account account = new Account("CZK", senderAccountNumber, "1020304050607080", customer);
        account.setBalance(balance);
        TransactionDto newTransaction = new TransactionDto();
        newTransaction.setSenderAccountNumber(senderAccountNumber);
        newTransaction.setReceiverAccountNumber(receiverAccountNumber);
        newTransaction.setSentAmount(amount);

        when(accountDao.findByAccountNumber(senderAccountNumber)).thenReturn(account);

        boolean result = aam.isTransactionValid(newTransaction, null);
        assertThat(result, is(true));

        verify(accountDao, times(1)).findByAccountNumber(senderAccountNumber);
    }

    @Test
    public void generateAccountNumber() {
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        final int accountNumberLength = 22;

        String generatedAccountNumber = aam.generateAccountNumber();
        assertThat(generatedAccountNumber.length(), is(equalTo(accountNumberLength)));
    }

    @Test
    public void generateCardNumber() {
        AuthorizedAccountManager aam = accountManager.authorize(customer);
        final int cardNumberLength = 16;

        String generatedCardNumber = aam.generateCardNumber();
        assertThat(generatedCardNumber.length(), is(equalTo(cardNumberLength)));
    }

}