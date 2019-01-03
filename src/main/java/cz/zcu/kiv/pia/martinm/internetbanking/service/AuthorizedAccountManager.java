package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionDto;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

/**
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
public interface AuthorizedAccountManager {

    void createAccount(AccountDto newAccount, User owner);

    Account findAccountById(int id);

    Page<Transaction> findAllTransactionsByAccount(Account account, Pageable pageable);

    void performTransaction(TransactionDto transaction);

    boolean isTransactionValid(TransactionDto transaction, BindingResult result);

    String generateAccountNumber();

    String generateCardNumber();

}
