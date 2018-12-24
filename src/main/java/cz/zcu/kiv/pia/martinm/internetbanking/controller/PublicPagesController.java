package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
class PublicPagesController extends GenericController {

    private UserManager um;

    public PublicPagesController(UserManager um) {
        this.um = um;
    }

    @RequestMapping({"", "/index"})
    ModelAndView indexHandler() {
        return new ModelAndView("index");
    }

    @RequestMapping("/login")
    ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/login-failed")
    ModelAndView loginFailed() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("errorMessage", "Wrong username or password.");
        return mav;
    }

    @RequestMapping("/role-check")
    String checkRoles(HttpServletRequest request) {
        User user = um.getCurrentUser();

        if (user.getRole().equals(User.Role.ADMIN.name())) {
            return redirect(request, "admin");
        }
        else if (user.getRole().equals(User.Role.USER.name())) {
            return redirect(request, "ib");
        }

        return redirect(request, "logout");
    }

}