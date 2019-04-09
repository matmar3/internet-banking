package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.AccountManager;
import cz.zcu.kiv.pia.martinm.internetbanking.service.AuthorizedAccountManager;
import cz.zcu.kiv.pia.martinm.internetbanking.service.TransactionTemplateManager;
import cz.zcu.kiv.pia.martinm.internetbanking.service.UserManager;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Date: 26.03.2019
 *
 * @author Martin Matas
 */
@RestController
@RequestMapping("/rest/api")
public class RESTController {

	/**
	 * user manager
	 */
	private UserManager userManager;

	/**
	 * account manager
	 */
	private AccountManager accountManager;

	/**
	 * transaction template manager
	 */
	private TransactionTemplateManager templateManager;

	/**
	 * Instance of tool ModelMapper
	 */
	private ModelMapper modelMapper;

	public RESTController(
			UserManager userManager,
			AccountManager accountManager,
			TransactionTemplateManager templateManager,
			ModelMapper modelMapper) {
		this.userManager = userManager;
		this.accountManager = accountManager;
		this.templateManager = templateManager;
		this.modelMapper = modelMapper;
	}

	@RequestMapping(value = "/profile")
	public ResponseEntity<User> returnProfile() {
		User user = userManager.getCurrentUser();

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/accounts")
	public ResponseEntity<Set<Account>> returnUserAccounts() {
		User user = userManager.getCurrentUser();
		Set<Account> accounts = user.getAccounts();

		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	@RequestMapping("/account/{id}")
	public ResponseEntity<Account> returnUserAccount(@PathVariable String id) {

		if (id == null) return ResponseEntity.notFound().build();

		int accountId;

		try {
			accountId = new Integer(id);
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		}

		User user = userManager.getCurrentUser();
		AuthorizedAccountManager aam = accountManager.authorize(user);
		Account account = aam.findAccountById(accountId);

		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	@RequestMapping("/account/{id}/transactions")
	public ResponseEntity<List<Transaction>> returnTransactionsRelatedToAuthorizedUser(@PathVariable String id,
                               @RequestParam(value = "page", defaultValue = "0", required = false) String page,
                               @RequestParam(value = "size", defaultValue = "10", required = false) String size) {

		if (id == null) return ResponseEntity.notFound().build();

		int accountId, pageNumber, pageSize;

		try {
			accountId = new Integer(id);
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		}

		try {
			pageNumber = new Integer(page);
			pageSize = new Integer(size);

		} catch (NumberFormatException e) {
			pageNumber = 0;
			pageSize = 10;
		}

		User user = userManager.getCurrentUser();
		AuthorizedAccountManager aam = accountManager.authorize(user);
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Account account = aam.findAccountById(accountId);
		Page<Transaction> transactions = aam.findAllTransactionsByAccount(account, pageable);

		if (pageNumber >= transactions.getTotalPages() && transactions.getTotalPages() > 0) {
			pageable = PageRequest.of(transactions.getTotalPages() - 1, pageSize);
			account = aam.findAccountById(accountId);
			transactions = aam.findAllTransactionsByAccount(account, pageable);
		}

		return new ResponseEntity<>(transactions.getContent(), HttpStatus.OK);
	}

    @RequestMapping("/account/{id}/transactions/count")
    public ResponseEntity<Long> returnCountOfTransactionsRelatedToAuthorizedUser(@PathVariable String id) {

        if (id == null) return ResponseEntity.notFound().build();

        int accountId;

        try {
            accountId = new Integer(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        User user = userManager.getCurrentUser();
        AuthorizedAccountManager aam = accountManager.authorize(user);
        Pageable pageable = Pageable.unpaged();
        Account account = aam.findAccountById(accountId);
        Page<Transaction> transactions = aam.findAllTransactionsByAccount(account, pageable);

        return new ResponseEntity<>(transactions.getTotalElements(), HttpStatus.OK);
    }
}
