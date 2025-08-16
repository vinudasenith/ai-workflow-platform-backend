package com.vinuda.workflowplatform.ai_workflow_platform.controller;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Organization;
import com.vinuda.workflowplatform.ai_workflow_platform.service.OrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/organizations")

public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/register")

    public Organization registerOrganization(@RequestBody Organization organization) {
        Organization registerOrganization = organizationService.registerOrganization(organization);
        if (registerOrganization == null) {
            return null;
        }
        return registerOrganization;
    }

    @GetMapping("/tenant/{tenantId}")
    public List<Organization> getOrganizationsByTenantId(@PathVariable String tenantId) {
        return organizationService.getOrganizationsByTenantId(tenantId);
    }

    @GetMapping("/{id}")
    public Organization getOrganizationById(@PathVariable String id) {
        Organization organization = organizationService.getOrganizationById(id);
        if (organization == null) {
            return null;
        }
        return organization;
    }

    @DeleteMapping("/{id}")
    public boolean deleteOrganization(@PathVariable String id) {
        boolean deleted = organizationService.deleteOrganization(id);
        if (deleted) {
            return true;
        }
        return false;
    }
}
