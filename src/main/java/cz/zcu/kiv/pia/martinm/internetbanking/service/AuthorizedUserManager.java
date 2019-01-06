package cz.zcu.kiv.pia.martinm.internetbanking.service;

import cz.zcu.kiv.pia.martinm.internetbanking.controller.dto.UserDto;
import cz.zcu.kiv.pia.martinm.internetbanking.domain.User;

import java.util.List;

/**
 * Provides authorized operations with users.
 *
 * Date: 25.12.2018
 *
 * @author Martin Matas
 */
public interface AuthorizedUserManager {

    /**
     * Disable user (=remove user) with given identifier.
     *
     * @param id - identifier of the user
     * @return disabled user
     */
    User remove(Integer id);

    /**
     * Edits user data with given user values. ID identify which user
     * will be edited.
     *
     * @param id - identifier of the user
     * @param user - modifies user values
     * @return modified user
     */
    User edit(Integer id, UserDto user);

    /**
     * Creates new user from given user values.
     *
     * @param user - user values
     * @return created user
     */
    User create(UserDto user);

    /**
     * Finds all users that are not disabled.
     *
     * @return list of enabled users
     */
    List<User> findAllUsers();

    /**
     * Generates user's password.
     *
     * @return user's password
     */
    String generatePassword();

    /**
     * Generates user's username.
     *
     * @return user's username
     */
    String generateUsername();

}
