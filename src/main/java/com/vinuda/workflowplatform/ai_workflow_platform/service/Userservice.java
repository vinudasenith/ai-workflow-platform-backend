package com.vinuda.workflowplatform.ai_workflow_platform.service;

import com.vinuda.workflowplatform.ai_workflow_platform.model.User;
import com.vinuda.workflowplatform.ai_workflow_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class Userservice {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // register new user
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return null;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setApproved(false);
            return userRepository.save(user);
        }
    }

    // fetch user by email and password
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null & passwordEncoder.matches(password, user.getPassword())) {
            if (!user.isApproved()) {
                return null;
            }
            return user;
        } else {
            return null;
        }
    }

    // approve user
    public boolean approveUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setApproved(true);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    // check if user is admin
    public boolean isAdmin(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getRole().equals("admin");
    }

    // check if user is customer
    public boolean isCustomer(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getRole().equals("customer");

    }

    // save user
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
