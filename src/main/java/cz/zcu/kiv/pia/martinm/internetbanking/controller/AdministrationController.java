package cz.zcu.kiv.pia.martinm.internetbanking.controller;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.UserDto;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;
import cz.zcu.kiv.pia.martinm.internetbanking.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
    private ModelMapper modelMapper;
    private TuringTestProvider turingTestProvider;

    public AdministrationController(UserManager um, ModelMapper mm, TuringTestProvider ttp) {
        this.userManager = um;
        this.modelMapper = mm;
        this.turingTestProvider = ttp;
    }

    @RequestMapping({"/", "/index"})
    public ModelAndView showUsersOverview() {
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
    public String showUserForm(Model model) {
        User user = userManager.getCurrentUser();

        if (!model.containsAttribute("newUser")) {
            model.addAttribute("newUser", new UserDto());
        }
        model.addAttribute("turingTest", turingTestProvider.generateRandomTest());
        model.addAttribute("authorizedUser", user);

        return "admin/create_user";
    }

    @PostMapping("/create-user")
    public String createUserHandler(Model model, @Valid @ModelAttribute("newUser") UserDto newUser, BindingResult result) {
        User u = userManager.getCurrentUser();

        boolean turingTestResult = turingTestProvider.testAnswer(newUser.getTuringTestId(), newUser.getTuringTestAnswer());
        if (result.hasErrors() || !turingTestResult) {
            if (!turingTestResult) result.addError(new FieldError("newUser", "turingTestAnswer", "Wrong answer"));
            newUser.setTuringTestId(null);
            model.addAttribute("turingTest", turingTestProvider.generateRandomTest());
            model.addAttribute("authorizedUser", u);
            return "admin/create_user";
        }

        AuthorizedUserManager aum = userManager.authorize(u);
        aum.create(newUser);
        return redirect("/admin/index");
    }

    @RequestMapping("/remove/user/{id}")
    public String removeUserHandler(HttpServletRequest request, @PathVariable Integer id) {
        AuthorizedUserManager aum = userManager.authorize(userManager.getCurrentUser());
        aum.remove(id);

        return redirectBack(request);
    }

    @RequestMapping("/profile")
    public String showCurrentUserProfile(Model model) {
        User user = userManager.getCurrentUser();

        if (!model.containsAttribute("modifiedUser")) {
            model.addAttribute("modifiedUser", modelMapper.map(user, UserDto.class));
        }
        model.addAttribute("authorizedUser", user);
        model.addAttribute("modifyUserActionUrl", "/admin/profile");

        return "admin/admin_profile";
    }

    @PostMapping("/profile")
    public String modifyUserHandler(@ModelAttribute("modifiedUser") UserDto modifiedUser, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/admin_profile";
        }

        User user = userManager.getCurrentUser();
        AuthorizedUserManager aum = userManager.authorize(user);
        aum.edit(user.getId(), modifiedUser);
        return redirect("/admin/index");
    }

}
