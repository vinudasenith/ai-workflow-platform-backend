package com.vinuda.workflowplatform.ai_workflow_platform.repository;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
    List<Organization> findByTenantId(String tenantId);

}
