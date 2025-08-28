package com.vinuda.workflowplatform.ai_workflow_platform.dto;

import lombok.Data;

@Data
public class SystemHealthDTO {
    private boolean mongoConnected;
    private int totalOrganizations;
    private int totalUsers;
    private int activeSessions;

}
