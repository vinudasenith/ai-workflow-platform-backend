package com.vinuda.workflowplatform.ai_workflow_platform.repository;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Task;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {

    // Find all tasks for a given workflow
    List<Task> findByWorkflowId(String workflowId);

    // Find tasks by status
    // List<Task> findByStatus(Task.TaskStatus status);

    List<Task> findByTenantId(String tenantId);

}
