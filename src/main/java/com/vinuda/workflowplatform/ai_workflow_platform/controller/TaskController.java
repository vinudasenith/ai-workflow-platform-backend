package com.vinuda.workflowplatform.ai_workflow_platform.controller;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Task;
import com.vinuda.workflowplatform.ai_workflow_platform.service.TaskService;

import com.vinuda.workflowplatform.ai_workflow_platform.service.Userservice;
import com.vinuda.workflowplatform.ai_workflow_platform.model.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")

public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private Userservice userService;

    // create task
    @PostMapping("/register")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Authentication Authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Authentication == null || !Authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email;
        Object principle = Authentication.getPrincipal();
        if (principle instanceof UserDetails) {
            email = ((UserDetails) principle).getUsername();
        } else {
            email = principle.toString();
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        task.setTenantId(user.getTenantId());
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // Get All Tasks
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // Get all tasks by tenantId
    @GetMapping("/tenant/all")
    public ResponseEntity<List<Task>> getAllTasksByTenant() {
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
        List<Task> tasks = taskService.getTasksByTenantId(tenantId);

        return ResponseEntity.ok(tasks);
    }

    // Update Task Status
    // @PutMapping("/{id}/status")
    // public ResponseEntity<?> updateTaskStatus(
    // @PathVariable String id,
    // @RequestParam Task.TaskStatus status) {
    // try {
    // Task updatedTask = taskService.updateTaskStatus(id, status);
    // return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    // } catch (RuntimeException e) {
    // return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    // }
    // }

    // Delete Task
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id);
            return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
    }

}
