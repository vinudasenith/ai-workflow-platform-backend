package com.vinuda.workflowplatform.ai_workflow_platform.service;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Organization;
import com.vinuda.workflowplatform.ai_workflow_platform.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    // register organization
    public Organization registerOrganization(Organization organization) {
        List<Organization> allOrgs = organizationRepository.findAll();
        for (Organization org : allOrgs) {
            if (org.getName().equalsIgnoreCase(organization.getName())) {
                return null;
            }
        }
        organization.setTenantId(UUID.randomUUID().toString());
        organization.setApproved(false);
        return organizationRepository.save(organization);

    }

    // login organization
    public Organization loginOrganization(String email, String password) {
        Organization org = organizationRepository.findByOwnerEmail(email)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        if (!org.isApproved()) {
            throw new RuntimeException("Organization not approved by admin yet");
        }

        if (!org.getOwnerPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        return org;
    }

    // approve organization
    public Organization approveOrganization(String organizationId) {
        Organization org = organizationRepository.findById(organizationId).orElse(null);
        if (org == null) {
            throw new RuntimeException("Organization not found");
        }
        org.setApproved(true);
        return organizationRepository.save(org);
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
