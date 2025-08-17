package com.vinuda.workflowplatform.ai_workflow_platform.service;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Organization;
import com.vinuda.workflowplatform.ai_workflow_platform.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    // register organization
    public Organization registerOrganization(Organization organization) {
        List<Organization> existingOrgs = organizationRepository.findByTenantId(organization.getTenantId());

        boolean exists = false;
        for (Organization org : existingOrgs) {
            if (org.getName().equals(organization.getName())) {
                exists = true;
                break;
            }
        }
        if (exists) {
            return null;
        }
        return organizationRepository.save(organization);

    }

    // get all organizations
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    // delete organization
    public boolean deleteOrganization(String id) {
        if (organizationRepository.existsById(id)) {
            organizationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
