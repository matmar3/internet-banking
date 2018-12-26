package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.UserForm;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.AccountManager;
import cz.zcu.kiv.pia.martinm.internetbanking.service.AuthorizedAccountManager;
import cz.zcu.kiv.pia.martinm.internetbanking.service.AuthorizedUserManager;
import cz.zcu.kiv.pia.martinm.internetbanking.service.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    private UserManager userManager;
    private AccountManager accountManager;

    public AdministrationController(UserManager um, AccountManager am) {
        this.userManager = um;
        this.accountManager = am;
    }

    @RequestMapping({"/", "/index"})
    ModelAndView indexHandler() {
        User user = userManager.getCurrentUser();

        AuthorizedUserManager aum = userManager.authorize(user);
        List<User> listOfUsers = aum.findAllUsers();
        listOfUsers.removeIf(u -> u.getRole().equals(User.Role.ADMIN.name()));

        ModelAndView mav = new ModelAndView("admin/admin_index");
        mav.addObject("customers", listOfUsers);
        mav.addObject("authorizedUser", user);

        return mav;
    }

    @RequestMapping("/create-user")
    String createUserForm(Model model) {
        User user = userManager.getCurrentUser();

        if (!model.containsAttribute("newUser")) {
            model.addAttribute("newUser", new UserForm());
        }
        model.addAttribute("authorizedUser", user);

        return "admin/create_user";
    }

    @PostMapping("/create-user")
    String createUserHandler(@Valid @ModelAttribute("newUser") UserForm newUser, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/create_user";
        }

        User u = userManager.getCurrentUser();
        AuthorizedUserManager aum = userManager.authorize(u);
        AuthorizedAccountManager aam = accountManager.authorize(u);
        User createdUser = aum.create(newUser);
        aam.createAccount(createdUser);
        return redirect("/admin/index");
    }

    @RequestMapping("/remove/user/{id}")
    String removeUserHandler(HttpServletRequest request, @PathVariable Integer id) {
        AuthorizedUserManager aum = userManager.authorize(userManager.getCurrentUser());
        aum.remove(id);

        return redirectBack(request);
    }

}
