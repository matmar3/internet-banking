package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionTemplateDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.UserDto;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Currency;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Transaction;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.TransactionTemplate;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for handling requests pointing to internet banking section.
 *
 * Date: 27.12.2018
 *
 * @author Martin Matas
 */
@Controller
@RequestMapping("/ib")
public class InternetBankingController extends GenericController {

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

    /**
     * Creates instance of InternetBankingController and initialize required services.
     * @param userManager - user manager
     * @param accountManager - account manager
     * @param templateManager - transaction template manager
     * @param modelMapper - instance of model mapper
     */
    public InternetBankingController(
            UserManager userManager,
            AccountManager accountManager,
            TransactionTemplateManager templateManager,
            ModelMapper modelMapper) {
        this.userManager = userManager;
        this.accountManager = accountManager;
        this.templateManager = templateManager;
        this.modelMapper = modelMapper;
    }

    /* ---------------- ACCOUNT ------------------- */

    /**
     * Handles request to welcoming page of internet banking section.
     *
     * @param model - ui model attributes
     * @return name of view
     */
    @RequestMapping({"/", "/index"})
    public String showIBOverview(Model model) {
        User user = userManager.getCurrentUser();

        if (!model.containsAttribute("newAccount")) {
            model.addAttribute("newAccount", new AccountDto());
        }
        model.addAttribute("authorizedUser", user);
        model.addAttribute("currencies", getPossibleCurrencies());
        model.addAttribute("accounts", user.getAccounts());

        return "ib/overview";
    }

    /**
     * Handles data processing for creating account.
     *
     * @param newAccount - created account (form output)
     * @param result - validation result
     * @return name of view
     */
    @PostMapping("/create-account")
    public String createAccountHandler(@ModelAttribute("newAccount") AccountDto newAccount, BindingResult result) {
        if (result.hasErrors()) {
            return redirect("/ib/index");
        }

        User user = userManager.getCurrentUser();
        AuthorizedAccountManager aam = accountManager.authorize(user);
        aam.createAccount(newAccount, user);
        return redirect("/ib/index");
    }

    /**
     * Handles requests to transaction history of some account.
     *
     * @param model - ui model attributes
     * @param id - identifier of account entity
     * @param page - page number
     * @param size - count of transactions per page
     * @return name of view
     */
    @RequestMapping("/account/{id}")
    public String showTransactionsRelatedToAuthorizedUser(
            Model model, @PathVariable String id,
            @RequestParam(value = "page", defaultValue = "0", required = false) String page,
            @RequestParam(value = "size", defaultValue = "10", required = false) String size) {

        if (id == null) return "errorPages/400";

        int accountId, pageNumber, pageSize;

        try {
            accountId = new Integer(id);
        } catch (NumberFormatException e) {
            return "errorPages/400";
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

        model.addAttribute("account", account);
        model.addAttribute("authorizedUser", user);
        model.addAttribute("totalPages", transactions.getTotalPages());
        model.addAttribute("transactions", transactions.get().collect(Collectors.toList()));
        model.addAttribute("pageRequest", pageable);

        return "ib/transaction_history";
    }

    /* ---------------- TRANSACTION ------------------- */

    /**
     * Handles request to page with form for creating transaction.
     *
     * @param model - ui model attributes
     * @return name of view
     */
    @RequestMapping("/create-transaction")
    public String showTransactionForm(Model model) {
        User user = userManager.getCurrentUser();
        AuthorizedTemplateManager atm = templateManager.authorize(user);

        if (!model.containsAttribute("newTransaction")) {
            model.addAttribute("newTransaction", new TransactionDto());
        }
        model.addAttribute("authorizedUser", user);
        model.addAttribute("templates", atm.findAllTemplatesByUser(user));
        model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));

        return "ib/create_transaction";
    }

    /**
     * Handles data processing of form for creating transaction. Created transaction is validated
     * via annotations in DTO. Errors will be stored in instance of BindingResult.
     *
     * @param model - ui model attributes
     * @param newTransaction - created transaction
     * @param result - validation result
     * @return name of view
     */
    @PostMapping("/create-transaction")
    public String createTransactionHandler(Model model, @Valid @ModelAttribute("newTransaction") TransactionDto newTransaction, BindingResult result) {
        User user = userManager.getCurrentUser();
        AuthorizedAccountManager aam = accountManager.authorize(user);
        AuthorizedTemplateManager atm = templateManager.authorize(user);

        if (result.hasErrors() || !aam.isTransactionValid(newTransaction, result)) {
            model.addAttribute("authorizedUser", user);
            model.addAttribute("templates", atm.findAllTemplatesByUser(user));
            model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));
            return "ib/create_transaction";
        }

        aam.performTransaction(newTransaction);
        return redirect("/ib/index");
    }

    /* ---------------- USER ------------------- */

    /**
     * Handles request for user profile page.
     *
     * @param model - holder for model attributes
     * @return name of view
     */
    @RequestMapping("/profile")
    public String showCurrentUserProfile(Model model) {
        User user = userManager.getCurrentUser();

        if (!model.containsAttribute("modifiedUser")) {
            model.addAttribute("modifiedUser", modelMapper.map(user, UserDto.class));
        }
        model.addAttribute("authorizedUser", user);
        model.addAttribute("modifyUserActionUrl", "/ib/profile");

        return "ib/user_profile";
    }

    /**
     * Handles data processing of form for editing user.
     *
     * @param modifiedUser - modified user (form output)
     * @param result - validation result
     * @return name of view
     */
    @PostMapping("/profile")
    public String modifyUserHandler(@ModelAttribute("modifiedUser") UserDto modifiedUser, BindingResult result) {
        if (result.hasErrors()) {
            return "ib/user_profile";
        }

        User user = userManager.getCurrentUser();
        AuthorizedUserManager aum = userManager.authorize(user);
        aum.edit(user.getId(), modifiedUser);
        return redirect("/ib/index");
    }

    /* ---------------- TEMPLATES ------------------- */

    /**
     * Handles requests to page with templates related to current user.
     *
     * @param model - ui model attributes
     * @return name of view
     */
    @RequestMapping(value = "/templates")
    public String showAllTemplates(Model model) {
        User user = userManager.getCurrentUser();
        AuthorizedTemplateManager atm = templateManager.authorize(user);

        if (!model.containsAttribute("newTemplate")) {
            model.addAttribute("newTemplate", new TransactionTemplateDto());
        }
        model.addAttribute("authorizedUser", user);
        model.addAttribute("templates", atm.findAllTemplatesByUser(user));
        model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));

        return "ib/templates";
    }

    /**
     * Handles requests to page with form for creating transaction template.
     *
     * @param model - ui model attributes
     * @param newTemplate - created template
     * @param result - validation result
     * @return name of view
     */
    @PostMapping(value = "/templates/create")
    public String createTransactionTemplateHandler(
            Model model, @ModelAttribute("newTemplate") TransactionTemplateDto newTemplate, BindingResult result) {
        User user = userManager.getCurrentUser();
        AuthorizedTemplateManager atm = templateManager.authorize(user);

        if (result.hasErrors()) {
            model.addAttribute("authorizedUser", user);
            model.addAttribute("templates", atm.findAllTemplatesByUser(user));
            model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));
            return "ib/templates";
        }

        atm.createTemplate(newTemplate, user);
        return redirect("/ib/templates");
    }

    /**
     * Handles requests to page with template detail.
     *
     * @param model - ui model attribute
     * @param id - identifier of transaction template entity
     * @return name of view
     */
    @RequestMapping("/templates/{id}")
    public String modifyTransactionTemplate(Model model, @PathVariable String id) {
        if (id == null) return "errorPages/400";

        Integer templateId;

        try {
            templateId = new Integer(id);
        } catch (NumberFormatException e) {
            return "errorPages/400";
        }

        User user = userManager.getCurrentUser();
        AuthorizedTemplateManager atm = templateManager.authorize(user);
        TransactionTemplate storedTemplate = atm.findTemplateById(user, templateId);
        TransactionTemplateDto template = modelMapper.map(storedTemplate, TransactionTemplateDto.class);

        if (!model.containsAttribute("modifyTemplate")) {
            model.addAttribute("modifyTemplate", template);
        }
        model.addAttribute("authorizedUser", user);
        model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));

        return "ib/edit_template";
    }

    /**
     * Handles requests to page with form for editing transaction template.
     *
     * @param model - ui model attributes
     * @param modifyTemplate - modified template
     * @param result - validation result
     * @return name of view
     */
    @PostMapping(value = "/templates/modify")
    public String modifyTransactionTemplateHandler(
            Model model, @ModelAttribute("modifyTemplate") TransactionTemplateDto modifyTemplate, BindingResult result) {
        User user = userManager.getCurrentUser();
        AuthorizedTemplateManager atm = templateManager.authorize(user);

        if (result.hasErrors()) {
            model.addAttribute("authorizedUser", user);
            model.addAttribute("templates", atm.findAllTemplatesByUser(user));
            model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));
            return "ib/edit_template";
        }

        atm.editTemplate(modifyTemplate);
        return redirect("/ib/templates");
    }

    /**
     * Handles request to page with template detail.
     *
     * @param id - identifier of transaction template entity
     * @return transaction template in JSON format or error message as JSON response
     */
    @RequestMapping(value = "/templates/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String retrieveTransactionTemplate(@PathVariable String id) {
        if (id == null) return "Bad request";

        Integer templateId;

        try {
            templateId = new Integer(id);
        } catch (NumberFormatException e) {
            return "Bad request";
        }

        User user = userManager.getCurrentUser();
        AuthorizedTemplateManager atm = templateManager.authorize(user);

        TransactionTemplate storedTemplate = atm.findTemplateById(user, templateId);
        // Mapping to transaction because of missing templateName in form
        TransactionDto template = modelMapper.map(storedTemplate, TransactionDto.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(template);
        } catch (JsonProcessingException e) {
            return "JSON processing failed";
        }
    }

    /**
     * Handles request for removing transaction template.
     *
     * @param id - identifier of transaction template entity.
     * @return name of view
     */
    @RequestMapping("/remove/template/{id}")
    public String createTransactionTemplate(@PathVariable String id) {
        User user = userManager.getCurrentUser();
        AuthorizedTemplateManager atm = templateManager.authorize(user);

        if (id == null) return "errorPages/400";

        Integer templateId;

        try {
            templateId = new Integer(id);
        } catch (NumberFormatException e) {
            return "errorPages/400";
        }

        atm.removeTemplate(templateId);
        return redirect("/ib/templates");
    }

    /* ---------------- AUXILIARY METHODS ------------------- */

    /**
     * Transform given set into sorted map of accounts. Accounts are sorted by account number.
     *
     * @param accounts - set of accounts
     * @return sorted map of accounts
     */
    private Map<String, String> getPossibleAccounts(Set<Account> accounts) {
        TreeMap<String, String> options = new TreeMap<>();
        options.put("", "--");
        for (Account a: accounts) {
            options.put(a.getAccountNumber(), a.getAccountNumber() + " (" + a.getCurrency() + ")");
        }

        return options;
    }

    /**
     * Returns sorted map of currencies that can be used. Currencies are sorted
     * by currency code.
     *
     * @return sorted map of currencies.
     */
    private Map<String, String> getPossibleCurrencies() {
        TreeMap<String, String> options = new TreeMap<>();
        options.put("", "--");
        for (Currency c: Currency.values()) {
            options.put(c.name(), c.name());
        }

        return options;
    }
}
