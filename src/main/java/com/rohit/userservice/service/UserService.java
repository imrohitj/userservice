package com.rohit.userservice.service;

import com.rohit.userservice.model.Role;
import com.rohit.userservice.model.User;

import java.util.List;

/**
 * Provide operations for create, update, retrieve {@link User} objects.
 */
public interface UserService {

    /**
     * Creates a new {@link User}
     * <p>
     * The following fields are required
     * <ul>
     * <li>{@link User#email}</li>
     * </ul>
     *
     * @param user the user to create.
     * @return the new user with it's id filled in.
     * @throws Exception
     */
    User createUser(User user) throws Exception;

    /**
     * Creates a new {@link Role}
     * <p>
     * The following fields are required
     * <ul>
     * <li>{@link Role#name}</li>
     * </ul>
     *
     * @param role the role to create.
     * @return the new role with it's id filled in.
     * @throws Exception
     */
    Role createRole(Role role) throws Exception;

    /**
     * Add a new role to user.
     * <p>
     * The following fields are required
     * <ul>
     * <li>{@link User#email}</li>
     * <li>{@link Role#name}</li>
     * </ul>
     *
     * @param email user's email.
     * @param name  role name.
     * @return the new role with it's id filled in.
     * @throws Exception
     */
    void addUserRole(String email, String name) throws Exception;

    /**
     * Retrieve list of all users contains input string.
     *
     * @param email, search text.
     * @return the list of all matching users.
     * @throws Exception
     */
    List<User> getUsersByEmail(String email) throws Exception;

    /**
     * Retrieve user by userId.
     *
     * @param uuid, user's uuid .
     * @return matching user.
     * @throws Exception
     */
    User getUserByUserId(String uuid) throws Exception;

    /**
     * Retrieve list of all users.
     *
     * @return list of all application.
     * @throws Exception
     */
    List<User> getAllUser();
}
