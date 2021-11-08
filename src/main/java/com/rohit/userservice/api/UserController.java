package com.rohit.userservice.api;


import com.rohit.userservice.model.Role;
import com.rohit.userservice.model.User;
import com.rohit.userservice.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * Handles all project related request from frontend.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Request API to register new user.
     */
    @PostMapping("/registration")
    public ResponseEntity signupUser(@RequestBody User user) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/create").toUriString());

        try {
            return ResponseEntity.created(uri).body(userService.createUser(user));
        } catch (Exception exception) {
            return ResponseEntity.status(FORBIDDEN.value())
                    .body(exception.getMessage());
        }

    }

    /**
     * Request API to get user details by user id(uuid).
     */
    @GetMapping("/user/getById")
    public ResponseEntity<?> getUserById(@RequestParam("userId") String userId) {

        try {
            return ResponseEntity.ok().body(userService.getUserByUserId(userId));
        } catch (Exception exception) {
            return ResponseEntity.status(FORBIDDEN.value()).body(exception.getMessage());

        }
    }

    /**
     * Request API to get search users by email.
     */
    @GetMapping("/users/searchByEmail")
    public ResponseEntity<?> searchByEmail(@RequestParam("email") String email) {
        try {
            return ResponseEntity.ok().body(userService.getUsersByEmail(email));
        } catch (Exception exception) {
            return ResponseEntity.status(FORBIDDEN.value()).body(exception.getMessage());
        }
    }

    /**
     * Request API to get a list of all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {

        return ResponseEntity.ok().body(userService.getAllUser());
    }

    /**
     * Request API to crete new application role e.g. ROLE_ADMIN, ROLE_SUPER_ADMIN, ROLE_USER.
     */
    @PostMapping("/role/create")
    public ResponseEntity createRole(@RequestBody Role role) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/create").toUriString());
        try {
            return ResponseEntity.created(uri).body(userService.createRole(role));
        } catch (Exception exception) {
            return ResponseEntity.status(FORBIDDEN.value())
                    .body(exception.getMessage());
        }
    }

    /**
     * Request API to assign role to user. A user can have multiple roles.
     */
    @PostMapping("/user/addrole")
    public ResponseEntity<?> addUserRole(@RequestBody UsernameRoleForm form) {
        try {
            userService.addUserRole(form.getUserName(), form.getRole());
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            return ResponseEntity.status(FORBIDDEN.value())
                    .body(exception.getMessage());
        }
    }
}

/** UsernameRoleForm object. */
@Data
class UsernameRoleForm {
    private String userName;
    private String role;

}
