package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Date: 27.12.2018
 *
 * @author Martin Matas
 */
@Controller
@RequestMapping("/ib")
public class InternetBankingController extends GenericController {

    private UserManager userManager;

    public InternetBankingController(UserManager userManager) {
        this.userManager = userManager;
    }

    @RequestMapping({"/", "/index"})
    public ModelAndView showIBOverview() {
        User user = userManager.getCurrentUser();

        ModelAndView mav = new ModelAndView("ib/overview");
        mav.addObject("authorizedUser", user);

        return mav;
    }

    @RequestMapping("/transactions")
    public ModelAndView showTransactionsRelatedToAuthorizedUser() {
        User user = userManager.getCurrentUser();

        ModelAndView mav = new ModelAndView("ib/transaction_history");
        mav.addObject("authorizedUser", user);

        return mav;
    }

}
