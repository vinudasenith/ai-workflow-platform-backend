package com.vinuda.workflowplatform.ai_workflow_platform.repository;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
    List<Organization> findByTenantId(String tenantId);

    Optional<Organization> findByOwnerEmail(String ownerEmail);

    Optional<Organization> findByName(String name);

}
