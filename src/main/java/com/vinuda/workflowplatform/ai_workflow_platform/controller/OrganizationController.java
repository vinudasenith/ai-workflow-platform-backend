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

    // register new organization
    @PostMapping("/register")
    public Organization registerOrganization(@RequestBody Organization organization) {
        Organization registerOrganization = organizationService.registerOrganization(organization);
        if (registerOrganization == null) {
            return null;
        }
        return registerOrganization;
    }

    // Get all organization
    @GetMapping("/all")
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    // delete organization
    @DeleteMapping("/{id}")
    public boolean deleteOrganization(@PathVariable String id) {
        boolean deleted = organizationService.deleteOrganization(id);
        if (deleted) {
            return true;
        }
        return false;
    }
}
