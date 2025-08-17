package com.vinuda.workflowplatform.ai_workflow_platform.repository;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Workflow;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface WorkflowRepository extends MongoRepository<Workflow, String> {

    List<Workflow> findByTenantId(String tenantId);

    List<Workflow> findBydepartmentId(String departmentId);

}
