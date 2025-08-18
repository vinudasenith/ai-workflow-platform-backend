package com.vinuda.workflowplatform.ai_workflow_platform.controller;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Workflow;
import com.vinuda.workflowplatform.ai_workflow_platform.service.WorkflowService;
import org.springframework.http.ResponseEntity;
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

    // register new workflow
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerWorkflow(@RequestBody Workflow workflow) {

        Workflow registeredWorkflow = workflowService.createWorkflow(workflow);
        Map<String, String> response = new HashMap<>();

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
