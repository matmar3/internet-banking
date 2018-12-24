package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Date: 24.12.2018
 *
 * @author Martin Matas
 */
@Controller
public class AdministrationController extends GenericController {

    @RequestMapping({"/admin/", "/admin/index"})
    ModelAndView indexHandler() {
        return new ModelAndView("admin_index");
    }

}
