package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.TransactionTemplateDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.UserDto;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.Account;
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
 * Date: 27.12.2018
 *
 * @author Martin Matas
 */
@Controller
@RequestMapping("/ib")
public class InternetBankingController extends GenericController {

    private UserManager userManager;
    private AccountManager accountManager;
    private ModelMapper modelMapper;

    public InternetBankingController(
            UserManager userManager,
            AccountManager accountManager,
            ModelMapper modelMapper) {
        this.userManager = userManager;
        this.accountManager = accountManager;
        this.modelMapper = modelMapper;
    }

    /* ---------------- ACCOUNT ------------------- */

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

    @RequestMapping("/account/{id}")
    public String showTransactionsRelatedToAuthorizedUser(
            Model model, @PathVariable String id,
            @RequestParam(value = "page", defaultValue = "0", required = false) String page,
            @RequestParam(value = "size", defaultValue = "10", required = false) String size) {

        if (id == null) return "errorPages/400";

        Integer accountId, pageNumber, pageSize;

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

        if (account == null) return "errorPages/400";

        Page<Transaction> transactions = aam.findAllTransactionsByAccount(account, pageable);

        model.addAttribute("account", account);
        model.addAttribute("authorizedUser", user);
        model.addAttribute("totalPages", transactions.getTotalPages());
        model.addAttribute("transactions", transactions.get().collect(Collectors.toList()));
        model.addAttribute("pageRequest", pageable);

        return "ib/transaction_history";
    }

    /* ---------------- TRANSACTION ------------------- */

    @RequestMapping("/create-transaction")
    public String showTransactionForm(Model model) {
        User user = userManager.getCurrentUser();
        AuthorizedAccountManager aam = accountManager.authorize(user);

        if (!model.containsAttribute("newTransaction")) {
            model.addAttribute("newTransaction", new TransactionDto());
        }
        model.addAttribute("authorizedUser", user);
        model.addAttribute("templates", aam.findAllTransactionTemplatesByUser(user));
        model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));

        return "ib/create_transaction";
    }

    @PostMapping("/create-transaction")
    public String createTransactionHandler(Model model, @Valid @ModelAttribute("newTransaction") TransactionDto newTransaction, BindingResult result) {
        User user = userManager.getCurrentUser();
        AuthorizedAccountManager aam = accountManager.authorize(user);

        if (result.hasErrors() || !aam.isTransactionValid(newTransaction, result)) {
            model.addAttribute("authorizedUser", user);
            model.addAttribute("templates", aam.findAllTransactionTemplatesByUser(user));
            model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));
            return "ib/create_transaction";
        }

        aam.performTransaction(newTransaction);
        return redirect("/ib/index");
    }

    /* ---------------- USER ------------------- */

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

    @RequestMapping(value = "/templates")
    public String showAllTemplates(Model model) {
        User user = userManager.getCurrentUser();
        AuthorizedAccountManager aam = accountManager.authorize(user);

        if (!model.containsAttribute("newTemplate")) {
            model.addAttribute("newTemplate", new TransactionTemplateDto());
        }
        model.addAttribute("authorizedUser", user);
        model.addAttribute("templates", aam.findAllTransactionTemplatesByUser(user));
        model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));

        return "ib/templates";
    }

    @PostMapping(value = "/templates/create")
    public String createTransactionTemplateHandler(
            Model model, @ModelAttribute("newTemplate") TransactionTemplateDto newTemplate, BindingResult result) {
        User user = userManager.getCurrentUser();
        AuthorizedAccountManager aam = accountManager.authorize(user);

        if (result.hasErrors()) {
            model.addAttribute("authorizedUser", user);
            model.addAttribute("templates", aam.findAllTransactionTemplatesByUser(user));
            model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));
            return "ib/templates";
        }

        aam.createTemplate(newTemplate, user);
        return redirect("/ib/templates");
    }

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
        AuthorizedAccountManager aam = accountManager.authorize(user);
        TransactionTemplate storedTemplate = aam.findTransactionTemplateById(user, templateId);

        if (storedTemplate == null) return "errorPages/400";

        TransactionTemplateDto template = modelMapper.map(storedTemplate, TransactionTemplateDto.class);

        if (!model.containsAttribute("modifyTemplate")) {
            model.addAttribute("modifyTemplate", template);
        }
        model.addAttribute("authorizedUser", user);
        model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));

        return "ib/edit_template";
    }

    @PostMapping(value = "/templates/modify")
    public String modifyTransactionTemplateHandler(
            Model model, @ModelAttribute("modifyTemplate") TransactionTemplateDto newTemplate, BindingResult result) {
        User user = userManager.getCurrentUser();
        AuthorizedAccountManager aam = accountManager.authorize(user);

        if (result.hasErrors()) {
            model.addAttribute("authorizedUser", user);
            model.addAttribute("templates", aam.findAllTransactionTemplatesByUser(user));
            model.addAttribute("accounts", getPossibleAccounts(user.getAccounts()));
            return "ib/edit_template";
        }

        aam.modifyTemplate(newTemplate);
        return redirect("/ib/templates");
    }

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
        AuthorizedAccountManager aam = accountManager.authorize(user);

        TransactionTemplate storedTemplate = aam.findTransactionTemplateById(user, templateId);

        if (storedTemplate == null) return "Bad request";

        // Mapping to transaction because of missing templateName in form
        TransactionDto template = modelMapper.map(storedTemplate, TransactionDto.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(template);
        } catch (JsonProcessingException e) {
            return "JSON processing failed";
        }
    }

    @RequestMapping("/remove/template/{id}")
    public String createTransactionTemplate(@PathVariable String id) {
        User user = userManager.getCurrentUser();
        AuthorizedAccountManager aam = accountManager.authorize(user);

        if (id == null) return "errorPages/400";

        Integer templateId;

        try {
            templateId = new Integer(id);
        } catch (NumberFormatException e) {
            return "errorPages/400";
        }

        aam.removeTemplate(templateId);
        return redirect("/ib/templates");
    }

    /* ---------------- AUXILIARY METHODS ------------------- */

    private Map<String, String> getPossibleAccounts(Set<Account> accounts) {
        TreeMap<String, String> options = new TreeMap<>();
        options.put("", "--");
        for (Account a: accounts) {
            options.put(a.getAccountNumber(), a.getAccountNumber() + " (" + a.getCurrency() + ")");
        }

        return options;
    }

    private Map<String, String> getPossibleCurrencies() {
        LinkedList<Currency> currencies = new LinkedList<>(Currency.getAvailableCurrencies());
        currencies.sort(Comparator.comparing(Currency::getCurrencyCode));

        TreeMap<String, String> options = new TreeMap<>();
        options.put("", "--");
        for (Currency c: currencies) {
            options.put(c.getCurrencyCode(), c.getCurrencyCode());
        }

        return options;
    }
}
