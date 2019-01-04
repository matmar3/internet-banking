package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.MessageContainer;
import cz.zcu.kiv.pia.martinm.internetbanking.service.MessageProvider;
import cz.zcu.kiv.pia.martinm.internetbanking.service.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        return new ModelAndView("index");
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

}