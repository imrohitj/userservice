package com.rohit.userservice;

import com.rohit.userservice.model.Role;
import com.rohit.userservice.model.User;
import com.rohit.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {

			userService.createRole(new Role(null, "ROLE_USER"));
			userService.createRole(new Role(null, "ROLE_ADMIN"));
			userService.createRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.createUser(new User(null, "First_1", "Last_1", "12345", "first.last_1@gmail.com", new ArrayList<Role>()));
			userService.createUser(new User(null, "First_2", "Last_1", "12345", "first.last_2@gmail.com", new ArrayList<Role>()));
			userService.createUser(new User(null, "First_3", "Last_1", "12345", "first.last_3@gmail.com", new ArrayList<Role>()));


			userService.addUserRole("first.last_1@gmail.com", "ROLE_USER");
			userService.addUserRole("first.last_1@gmail.com", "ROLE_ADMIN");
			userService.addUserRole("first.last_1@gmail.com", "ROLE_SUPER_ADMIN");
			userService.addUserRole("first.last_2@gmail.com", "ROLE_ADMIN");
			userService.addUserRole("first.last_3@gmail.com", "ROLE_SUPER_ADMIN");

		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
