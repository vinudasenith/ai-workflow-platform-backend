package com.vinuda.workflowplatform.ai_workflow_platform.controller;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Organization;
import com.vinuda.workflowplatform.ai_workflow_platform.service.OrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organizations")

public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    // register new organization
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerOrganization(@RequestBody Organization organization) {
        Organization registeredOrg = organizationService.registerOrganization(organization);
        Map<String, String> response = new HashMap<>();

        if (registeredOrg != null) {
            response.put("message", "Organization registered successfully, waiting for admin approval");
            response.put("tenantId", registeredOrg.getTenantId()); // show tenantId if needed
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Organization registration failed: Name already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // login registared organization
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginOrganization(@RequestBody Map<String, String> loginRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            String email = loginRequest.get("ownerEmail");
            String password = loginRequest.get("ownerPassword");

            Organization org = organizationService.loginOrganization(email, password);

            response.put("message", "Login successful");
            response.put("tenantId", org.getTenantId());
            response.put("organizationName", org.getName());
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // approve organization
    @PutMapping("/approve/{organizationId}")
    public ResponseEntity<Map<String, String>> approveOrganization(@PathVariable String organizationId) {
        Map<String, String> response = new HashMap<>();
        try {
            Organization approvedOrg = organizationService.approveOrganization(organizationId);
            response.put("message", "Organization approved successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Get all organization
    @GetMapping("/all")
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    // delete organization
    @DeleteMapping("/{id}")
    public boolean deleteOrganization(@PathVariable String id) {
        boolean deleted = organizationService.deleteOrganization(id);
        if (deleted) {
            return true;
        }
        return false;
    }
}
