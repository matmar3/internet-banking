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
import java.util.Map;
import java.util.TreeMap;

/**
 * Controller handles request pointing to public section which is
 * accessible without authorization.
 *
 * Date: 17.12.2018
 *
 * @author Martin Matas
 */
@Controller
class PublicPagesController extends GenericController {

    /**
     * Unauthorized user manager
     */
    private UserManager um;

    /**
     * Message provider instance
     */
    private MessageProvider messageProvider;

    /**
     * Creates instance of PublicPagesController and initialize required services.
     * @param um - unauthorized user manager
     * @param messageProvider - instance of message provider
     */
    public PublicPagesController(UserManager um, MessageProvider messageProvider) {
        this.um = um;
        this.messageProvider = messageProvider;
    }

    /**
     * Handles request to homepage of application.
     *
     * @return instance of model with specific view
     */
    @RequestMapping({"", "/index"})
    public ModelAndView indexHandler() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("exchangeRates", getExchangeRatesMap());

        return mav;
    }

    /**
     * Handles requests to page with contact information.
     *
     * @return instance of model with specific view
     */
    @RequestMapping("/contact")
    public ModelAndView contactHandler() {
        return new ModelAndView("contact");
    }

    /**
     * Handles requests to page with terms and conditions.
     *
     * @return instance of model with specific view
     */
    @RequestMapping("/terms-and-conditions")
    public ModelAndView termsAndConditionsHandler() {
        return new ModelAndView("terms_conditions");
    }

    /**
     * Handles requests to login page. If user is authorized, then is automatically
     * logged in authorized section.
     *
     * @return name of view
     */
    @RequestMapping("/login")
    public String login() {
        User user = um.getCurrentUser();

        if (user != null) {
            return redirect("role-check");
        }

        return "login";
    }

    /**
     * Handles requests to login page when authentication failed.
     *
     * @return instance of model with specific view
     */
    @RequestMapping("/login-failed")
    public ModelAndView loginFailed() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("message", new MessageContainer(
                MessageContainer.Type.WARNING,
                messageProvider.getMessage("error.login.wrongCredentials")
        ));
        return mav;
    }

    /**
     * Checks user's authorization and redirect him to appropriate page
     * based on user's authorization.
     *
     * @return name of view
     */
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

    /**
     * Exception in this controller. Handles requests to error page
     * when {@link org.springframework.security.access.AccessDeniedException} occurs.
     *
     * @return instance of model with specific view
     */
    @RequestMapping("/403")
    public ModelAndView handle403()   {
        return new ModelAndView("errorPages/403");
    }

    /**
     * Retrieves some of actual exchange rates and returns them as sorted map of rates.
     *
     * @return sorted map of some actual rates
     */
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