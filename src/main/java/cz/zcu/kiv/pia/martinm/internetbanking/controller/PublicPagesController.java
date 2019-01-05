package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.CurrencyUtils;
import cz.zcu.kiv.pia.martinm.internetbanking.service.MessageContainer;
import cz.zcu.kiv.pia.martinm.internetbanking.service.MessageProvider;
import cz.zcu.kiv.pia.martinm.internetbanking.service.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Date: 17.12.2018
 *
 * @author Martin Matas
 */
@Controller
class PublicPagesController extends GenericController {

    private UserManager um;

    private MessageProvider messageProvider;

    public PublicPagesController(UserManager um, MessageProvider messageProvider) {
        this.um = um;
        this.messageProvider = messageProvider;
    }

    @RequestMapping({"", "/index"})
    public ModelAndView indexHandler() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("exchangeRates", getExchangeRatesMap());

        return mav;
    }

    @RequestMapping("/contact")
    public ModelAndView contactHandler() {
        return new ModelAndView("contact");
    }

    @RequestMapping("/terms-and-conditions")
    public ModelAndView termsAndConditionsHandler() {
        return new ModelAndView("terms_conditions");
    }

    @RequestMapping("/login")
    public String login() {
        User user = um.getCurrentUser();

        if (user != null) {
            return redirect("role-check");
        }

        return "login";
    }

    @RequestMapping("/login-failed")
    public ModelAndView loginFailed() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("message", new MessageContainer(
                MessageContainer.Type.WARNING,
                messageProvider.getMessage("error.login.wrongCredentials")
        ));
        return mav;
    }

    @RequestMapping("/role-check")
    public String checkRoles() {
        User user = um.getCurrentUser();

        if (user == null) {
            return redirect("login-failed");
        }
        else if (user.getRole().equals(User.Role.ADMIN.name())) {
            return redirect("admin/");
        }
        else if (user.getRole().equals(User.Role.CUSTOMER.name())) {
            return redirect("ib/");
        }

        return redirect("login-failed");
    }

    @RequestMapping("/403")
    public ModelAndView handle403(Exception e)   {
        ModelAndView mav = new ModelAndView("errorPages/403");
        mav.addObject("exception", e);
        return mav;
    }

    private Map<String, String> getExchangeRatesMap() {
        Map<String, String> exchangeRates = new TreeMap<>();

        exchangeRates.put("EUR -> CZK",
                CurrencyUtils.convert(
                        new BigDecimal(1), Currency.getInstance("EUR"), Currency.getInstance("CZK")
                ).stripTrailingZeros().toPlainString()
        );
        exchangeRates.put("USD -> EUR",
                CurrencyUtils.convert(
                        new BigDecimal(1), Currency.getInstance("USD"), Currency.getInstance("EUR")
                ).stripTrailingZeros().toPlainString()
        );
        exchangeRates.put("USD -> GBP",
                CurrencyUtils.convert(
                        new BigDecimal(1), Currency.getInstance("USD"), Currency.getInstance("GBP")
                ).stripTrailingZeros().toPlainString()
        );
        exchangeRates.put("USD -> AUD",
                CurrencyUtils.convert(
                        new BigDecimal(1), Currency.getInstance("USD"), Currency.getInstance("AUD")
                ).stripTrailingZeros().toPlainString()
        );
        return exchangeRates;
    }

}