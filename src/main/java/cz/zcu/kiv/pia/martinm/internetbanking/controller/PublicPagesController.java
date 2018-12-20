package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
class PublicPagesController {

    @Autowired
    private UserManager um;

    @GetMapping({"", "/index"})
    ModelAndView indexHandler() {
        User u = um.getUser("user001");
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("user", u);
        return mav;
    }

    @GetMapping("/login")
    ModelAndView login() {
        return new ModelAndView("login");
    }

    /*
    @GetMapping("/registration")
    String registration() {
        return "registration";
    }*/

}