package com.vinuda.workflowplatform.ai_workflow_platform.service;

import com.vinuda.workflowplatform.ai_workflow_platform.dto.SystemHealthDTO;
import com.vinuda.workflowplatform.ai_workflow_platform.repository.OrganizationRepository;
import com.vinuda.workflowplatform.ai_workflow_platform.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SystemHealthService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    @Autowired
    public SystemHealthService(OrganizationRepository organizationRepository, UserRepository userRepository) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
    }

    public SystemHealthDTO getSystemHealth() {
        SystemHealthDTO dto = new SystemHealthDTO();

        try {
            organizationRepository.count();
            dto.setMongoConnected(true);
        } catch (Exception e) {
            dto.setMongoConnected(false);
        }

        dto.setTotalOrganizations((int) organizationRepository.count());
        dto.setTotalUsers((int) userRepository.count());

        dto.setActiveSessions(0);

        return dto;
    }
}
