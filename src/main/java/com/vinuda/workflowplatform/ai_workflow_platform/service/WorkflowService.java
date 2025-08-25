package com.vinuda.workflowplatform.ai_workflow_platform.service;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Workflow;
import com.vinuda.workflowplatform.ai_workflow_platform.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class WorkflowService {

    @Autowired
    private WorkflowRepository workflowRepository;

    // create new work flow
    public Workflow createWorkflow(Workflow workflow) {
        workflow.setCreatedAt(LocalDateTime.now());
        workflow.setStatus(Workflow.WorkflowStatus.ACTIVE); // directly active for everyone
        return workflowRepository.save(workflow);
    }

    // get all workflows
    public List<Workflow> getAllWorkflows() {
        return workflowRepository.findAll();
    }

    // Delete workflow
    public boolean deleteWorkflow(String id) {
        if (workflowRepository.existsById(id)) {
            workflowRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // get all workflows by tenantId
    public List<Workflow> getWorkflowsByTenantId(String tenantId) {
        return workflowRepository.findByTenantId(tenantId);
    }

}
