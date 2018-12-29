package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.AccountDto;
import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.UserDto;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

    public InternetBankingController(UserManager userManager, AccountManager accountManager, ModelMapper modelMapper) {
        this.userManager = userManager;
        this.accountManager = accountManager;
        this.modelMapper = modelMapper;
    }

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

    @RequestMapping("/transactions")
    public ModelAndView showTransactionsRelatedToAuthorizedUser() {
        User user = userManager.getCurrentUser();

        ModelAndView mav = new ModelAndView("ib/transaction_history");
        mav.addObject("authorizedUser", user);

        return mav;
    }

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
