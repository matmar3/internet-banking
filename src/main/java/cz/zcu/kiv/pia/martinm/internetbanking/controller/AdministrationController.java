package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.UserForm;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.AuthorizedUserManager;
import cz.zcu.kiv.pia.martinm.internetbanking.service.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Date: 24.12.2018
 *
 * @author Martin Matas
 */
@Controller
@RequestMapping("/admin")
public class AdministrationController extends GenericController {

    private UserManager um;
    private AuthorizedUserManager aum;

    public AdministrationController(UserManager um) {
        this.um = um;
    }

    @RequestMapping({"/", "/index"})
    ModelAndView indexHandler() {
        User user = um.getCurrentUser();

        aum = um.authorize(user);
        List<User> listOfUsers = aum.findAllUsers();
        listOfUsers.removeIf(u -> u.getRole().equals(User.Role.ADMIN.name()));

        ModelAndView mav = new ModelAndView("admin/admin_index");
        mav.addObject("customers", listOfUsers);

        return mav;
    }

    @RequestMapping("/create-user")
    String createUserForm(Model model) {
        User user = um.getCurrentUser();

        if (!model.containsAttribute("newUser")) {
            model.addAttribute("newUser", new UserForm());
        }

        return "admin/create_user";
    }

    @PostMapping("/create-user")
    String createUserHandler(HttpServletRequest request, RedirectAttributes redirectAttributes, @Valid @ModelAttribute("newUser") UserForm newUser,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "admin/create_user";
        }

        aum = um.authorize(um.getCurrentUser());
        aum.create(newUser);
        return redirect(request, "/admin/index");
    }

}
