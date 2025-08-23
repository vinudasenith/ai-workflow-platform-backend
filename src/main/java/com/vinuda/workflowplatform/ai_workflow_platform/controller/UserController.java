package com.vinuda.workflowplatform.ai_workflow_platform.controller;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Organization;
import com.vinuda.workflowplatform.ai_workflow_platform.model.User;
import com.vinuda.workflowplatform.ai_workflow_platform.service.Userservice;
import com.vinuda.workflowplatform.ai_workflow_platform.service.OrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import com.vinuda.workflowplatform.ai_workflow_platform.security.JwtUtil;

@RestController
@RequestMapping("/api/user")

public class UserController {

    @Autowired
    private Userservice userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OrganizationService organizationService;

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        Organization org = organizationService.getOrganizationByName(user.getOrganizationName());
        if (org == null) {
            response.put("message", "Organization not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        user.setTenantId(org.getTenantId());

        // Force approval workflow
        user.setApproved(false);

        // If no role provided, default to User
        if (user.getRole() == null) {
            user.setRole(User.Role.User);
        }

        User registeredUser = userService.registerUser(user);

        if (registeredUser != null) {
            response.put("message", "User registered successfully,Waiting for admin approval");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "User registration failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            if (authentication.isAuthenticated()) {
                User loggedInUser = userService.findByEmail(user.getEmail());

                String token = jwtUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "email", user.getEmail(),
                        "tenantId", loggedInUser.getTenantId(),
                        "role", loggedInUser.getRole().name()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials or user not approved yet");
        }
    }

    // Get all user
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Check if user is admin
    @GetMapping("/is-admin/{email}")
    public ResponseEntity<Map<String, String>> isAdmin(@PathVariable String email) {
        boolean isAdmin = userService.isAdmin(email);
        Map<String, String> response = new HashMap<>();
        if (isAdmin) {
            response.put("message", "User is admin");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "User is not admin");
            return ResponseEntity.ok(response);
        }
    }

    // Check if user is customer
    @GetMapping("/is-customer/{email}")
    public ResponseEntity<Map<String, String>> isCustomer(@PathVariable String email) {
        boolean isCustomer = userService.isCustomer(email);
        Map<String, String> response = new HashMap<>();
        if (isCustomer) {
            response.put("message", "User is customer");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "User is not customer");
            return ResponseEntity.ok(response);
        }
    }

    // Approve user
    @PutMapping("/approve/{email}")
    public ResponseEntity<Map<String, String>> approveUser(@PathVariable String email) {
        boolean isApproved = userService.approveUser(email);
        Map<String, String> response = new HashMap<>();
        if (isApproved) {
            response.put("message", "User approved successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "User approval failed");
            return ResponseEntity.ok(response);
        }
    }

}