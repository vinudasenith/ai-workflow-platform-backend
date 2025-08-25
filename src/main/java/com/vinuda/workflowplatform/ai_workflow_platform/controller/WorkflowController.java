package com.vinuda.workflowplatform.ai_workflow_platform.controller;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Workflow;
import com.vinuda.workflowplatform.ai_workflow_platform.service.WorkflowService;

import com.vinuda.workflowplatform.ai_workflow_platform.model.User;
import com.vinuda.workflowplatform.ai_workflow_platform.service.Userservice;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/workflow")

public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private Userservice userService;

    // register new workflow
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerWorkflow(@RequestBody Workflow workflow) {

        Map<String, String> response = new HashMap<>();

        // get the authenticated user

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized: no authenticated user"));
        }

        String email;
        Object principle = authentication.getPrincipal();
        if (principle instanceof UserDetails) {
            email = ((UserDetails) principle).getUsername();
        } else {
            email = principle.toString();
        }

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid user"));
        }

        workflow.setTenantId(user.getTenantId());

        // Save workflow
        Workflow registeredWorkflow = workflowService.createWorkflow(workflow);

        if (registeredWorkflow != null) {
            response.put("message", "Workflow registered successfully and is now active");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Workflow registration failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // get all workflows
    @GetMapping("/all")
    public ResponseEntity<List<Workflow>> getAllWorkflows() {
        List<Workflow> workflows = workflowService.getAllWorkflows();
        return ResponseEntity.ok(workflows);
    }

    // get all workflows by tenantId
    @GetMapping("/tenant/all")
    public ResponseEntity<List<Workflow>> getAllWorkflowsByTenant() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email;
        Object principle = authentication.getPrincipal();
        if (principle instanceof UserDetails) {
            email = ((UserDetails) principle).getUsername();
        } else {
            email = principle.toString();
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        // Fetch workflows by tenantId
        List<Workflow> workflows = workflowService.getWorkflowsByTenantId(user.getTenantId());
        return ResponseEntity.ok(workflows);
    }

    // Delete workflow
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteWorkflow(@PathVariable String id) {
        Map<String, String> response = new HashMap<>();
        boolean deleted = workflowService.deleteWorkflow(id);
        if (deleted) {
            response.put("message", "Workflow deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Workflow not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
