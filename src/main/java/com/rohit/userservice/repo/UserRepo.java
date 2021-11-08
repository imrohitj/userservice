package com.rohit.userservice.repo;

import com.rohit.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/** Logic for listing user for the UI */
public interface UserRepo extends JpaRepository<User, String> {

    /** Get a list of all matching user contains input email. */
    List<User> findByEmailContaining(String email);

    /** Retrieve user by user id. */
    User findByUserId(String userId);
}
