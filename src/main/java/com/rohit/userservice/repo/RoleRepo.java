package com.rohit.userservice.repo;

import com.rohit.userservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/** Logic for listing Roles for the UI */
public interface RoleRepo extends JpaRepository<Role, Long> {

    /** Retrieve Role by name. */
    Role findByName(String name);

}
