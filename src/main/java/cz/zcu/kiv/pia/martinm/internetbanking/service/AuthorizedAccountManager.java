package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionTemplateDto;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.TransactionTemplate;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
public interface AuthorizedAccountManager {

    Account createAccount(AccountDto newAccount, User owner);

    Account findAccountById(int id);

    Page<Transaction> findAllTransactionsByAccount(Account account, Pageable pageable);

    Transaction performTransaction(TransactionDto transaction);

    TransactionTemplate createTemplate(TransactionTemplateDto newTemplate, User user);

    List<TransactionTemplate> findAllTransactionTemplatesByUser(User user);

    TransactionTemplate findTransactionTemplateById(User user, Integer id);

    void removeTemplate(Integer id);

    TransactionTemplate modifyTemplate(TransactionTemplateDto modifyTemplate);

    String generateAccountNumber();

    String generateCardNumber();

}
