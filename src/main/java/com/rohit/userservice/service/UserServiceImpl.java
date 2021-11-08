package com.rohit.userservice.service;

import com.rohit.userservice.model.Role;
import com.rohit.userservice.model.User;
import com.rohit.userservice.repo.RoleRepo;
import com.rohit.userservice.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;


/**
 * Implementation of User service.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) throws Exception{
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("Create new User {}", user);
        try {

            User newUser = userRepo.save(user);
            addUserRole(newUser.getEmail(), "ROLE_USER");
            return newUser;

        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Role createRole(Role role) throws Exception{
        try {
            log.info("Create new Role {}", role);
            return roleRepo.save(role);
        } catch (Exception e) {
            log.error("Error creating Role: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void addUserRole(String userName, String roleName) throws Exception{

        List<User> users = userRepo.findByEmailContaining(userName);

        if(users.size() == 0){
            log.info("User doesn't exist with email {}", userName);
            throw new Exception("Serach string can't be empty");
        }

        User user = users.get(0);

        Role role = roleRepo.findByName(roleName);


        user.getRoles().add(role);

    }

    @Override
    public List<User> getUsersByEmail(String email) throws Exception{

        List<User> users = new ArrayList<>();
        try {

            if(StringUtils.isEmpty(email)){
                log.info("Search string is empty");
                throw new Exception("Serach string can't be empty");
            } else {
                String s = email.trim().toLowerCase();
                users  = userRepo.findByEmailContaining(s);
            }

        } catch (Exception e) {
            log.error("Exception while trying to search users with email id {}",email );
            throw new Exception("Exception while trying to search users with email id "+email+ "\t"+e.getMessage());
        }

        return  users;

    }

    @Override
    public User getUserByUserId(String userId) throws Exception {
        try {
            User user = userRepo.findByUserId(userId);
            if(user == null){
                log.info("User Id doesn't exist {}",userId);
                throw new Exception("User Id doesn't exist");
            }
        } catch (Exception e) {
            log.error("Exception while trying to retrieve user details for user id {}",userId );
            throw new Exception("Exception while trying to retrieve user details for user id "+userId+ "\t"+e.getMessage());
        }
        return userRepo.findByUserId(userId);
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        List<User> users = userRepo.findByEmailContaining(userName);

        if(users.size() == 0){
            log.error("User {} doesn't exist in database", userName);
            throw new UsernameNotFoundException("User doesn't exist");
        } else {
            User user =  users.get(0);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(
                    role -> {
                        authorities.add(new SimpleGrantedAuthority(role.getName()));
                    }
            );
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        }
    }


}


