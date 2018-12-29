package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
public interface AuthorizedAccountManager {

    Account createAccount(AccountDto newAccount, User owner);

    Account findAccountById(int id);

    List<Transaction> findAllTransactionsByAccount(Account account);

    Transaction performTransaction(Account sender, String receiver, Integer amount, ZonedDateTime date, String description);

    String generateAccountNumber();

    String generateCardNumber();

}
