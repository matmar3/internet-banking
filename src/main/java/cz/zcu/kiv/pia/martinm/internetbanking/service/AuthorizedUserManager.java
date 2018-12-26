package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.UserForm;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;

import java.util.List;

/**
 * Date: 25.12.2018
 *
 * @author Martin Matas
 */
public interface AuthorizedUserManager {

    User remove(Integer id);

    User edit(User user);

    User create(UserForm user);

    List<User> findAllUsers();

}
