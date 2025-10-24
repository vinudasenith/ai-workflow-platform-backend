package com.vinuda.workflowplatform.ai_workflow_platform.controller;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Department;
import com.vinuda.workflowplatform.ai_workflow_platform.service.DepartmentService;

import com.vinuda.workflowplatform.ai_workflow_platform.model.User;
import com.vinuda.workflowplatform.ai_workflow_platform.service.Userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")

public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private Userservice userService;

    // Register new department
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerDepartment(@RequestBody Department department) {

        Map<String, String> response = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized: no authenticated user"));
        }

        String email;

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid user"));
        }

        department.setTenantId(user.getTenantId());
        Department registeredDepartment = departmentService.createDepartment(department);

        if (registeredDepartment != null) {
            response.put("message", "Department registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Department registration failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Get all departments
    @GetMapping("/all")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    // get all departments by tenantId
    @GetMapping("/tenant/all")
    public ResponseEntity<List<Department>> getAllDepartmentsByTenant() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email;

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String tenantId = user.getTenantId();
        List<Department> departments = departmentService.getDepartmentsByTenantId(tenantId);

        return ResponseEntity.ok(departments);
    }

    // Get departments by organization
    @GetMapping("/organization/{organizationId}")
    public List<Department> getDepartmentsByOrganization(@PathVariable String organizationId) {
        return departmentService.getDepartmentsByOrganization(organizationId);
    }

    // delete department by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteDepartment(@PathVariable String id) {
        Map<String, String> response = new HashMap<>();
        try {
            departmentService.deleteDepartmentByCode(id);
            response.put("message", "Department deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("message", "Failed to delete department");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
