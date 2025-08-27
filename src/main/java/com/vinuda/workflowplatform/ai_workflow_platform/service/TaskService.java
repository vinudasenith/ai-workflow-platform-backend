package com.vinuda.workflowplatform.ai_workflow_platform.service;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Task;
import com.vinuda.workflowplatform.ai_workflow_platform.repository.TaskRepository;

import com.vinuda.workflowplatform.ai_workflow_platform.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Create a new task
    public Task createTask(Task task) {
        // task.setStatus(Task.TaskStatus.PENDING); // default status
        return taskRepository.save(task);
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // // Update task status
    // public Task updateTaskStatus(String taskId, Task.TaskStatus newStatus) {
    // Task task = taskRepository.findById(taskId).orElse(null);
    // if (task == null) {
    // throw new RuntimeException("Task not found with id: " + taskId);
    // }
    // task.setStatus(newStatus);
    // Task updatedTask = taskRepository.save(task);
    // return updatedTask;
    // }

    // Delete task
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    // Get tasks by tenantId
    public List<Task> getTasksByTenantId(String tenantId) {
        return taskRepository.findByTenantId(tenantId);
    }

    // get task by id
    public Task getTaskById(String id) {
        return taskRepository.findById(id).orElse(null);
    }

    // update task
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

}
