package com.vinuda.workflowplatform.ai_workflow_platform.controller;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Department;
import com.vinuda.workflowplatform.ai_workflow_platform.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")

public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Register new department
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerDepartment(@RequestBody Department department) {

        Department registeredDepartment = departmentService.creaDepartment(department);
        Map<String, String> response = new HashMap<>();

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

    // Get departments by organization
    @GetMapping("/organization/{organizationId}")
    public List<Department> getDepartmentsByOrganization(@PathVariable String organizationId) {
        return departmentService.getDepartmentsByOrganization(organizationId);
    }

}
