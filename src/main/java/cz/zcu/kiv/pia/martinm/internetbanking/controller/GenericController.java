package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: 24.12.2018
 *
 * @author Martin Matas
 */
public class GenericController {

    static void fillUserAttribute(Model model, User currentUser) {
        model.addAttribute("user", currentUser);
    }

    static String redirectBack(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }

    static String redirect(String page) {
        return "redirect:" + page;
    }

}
