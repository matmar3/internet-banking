package cz.zcu.kiv.pia.martinm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
class PublicPagesController {

    @GetMapping({"", "/index"})
    ModelAndView indexHandler() {
        return new ModelAndView("index");
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