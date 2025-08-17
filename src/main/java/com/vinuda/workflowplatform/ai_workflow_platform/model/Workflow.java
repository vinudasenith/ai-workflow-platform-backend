package com.vinuda.workflowplatform.ai_workflow_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "workflow")

public class Workflow {

    @Id
    private String id;

    private String name;
    private String description;

    private String departmentId;
    private String tenantId;

    private String createdByUserId;
    private LocalDateTime createdAt;

    private WorkflowStatus status;

    private List<String> steps;

    public enum WorkflowStatus {
        PENDING, ACTIVE, COMPLETED, REJECTED
    }

}
