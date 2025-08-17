package com.vinuda.workflowplatform.ai_workflow_platform.repository;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DepartmentRepository extends MongoRepository<Department, String> {
    List<Department> findByOrganizationId(String organizationId);
}
