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
 * Provides authorized operations with accounts and transactions.
 *
 * Date: 26.12.2018
 *
 * @author Martin Matas
 */
public interface AuthorizedAccountManager {

    /**
     * Creates new account from form values for given owner.
     *
     * @param newAccount - account values
     * @param owner - account's owner
     */
    void createAccount(AccountDto newAccount, User owner);

    /**
     * Finds account by given account's identifier.
     *
     * @param id - account's identifier
     * @return founded account or null
     */
    Account findAccountById(int id);

    /**
     * Finds all transactions related to given account. Result set of transactions is
     * influenced by given pageable parameter which defines size of result set and offset,
     * where results set starts.
     *
     * @param account - sender or receiver of transactions
     * @param pageable - defines size of result set and offset
     * @return one page of transactions
     */
    Page<Transaction> findAllTransactionsByAccount(Account account, Pageable pageable);

    /**
     * Performs transaction based on given transaction values.
     *
     * @param transaction - transaction values
     */
    void performTransaction(TransactionDto transaction);

    /**
     * Tests if given created transaction values are valid. It is second stage of validation, which cannot
     * be tested via annotation (entities are needed e.g. sender's balance).
     *
     * @param transaction - transaction values
     * @param result - validation result, can be used to delegate error message into form field
     * @return if is transaction valid
     */
    boolean isTransactionValid(TransactionDto transaction, BindingResult result);

    /**
     * Generates account number.
     *
     * @return generated account number
     */
    String generateAccountNumber();

    /**
     * Generates card number.
     *
     * @return generated card number.
     */
    String generateCardNumber();

}
