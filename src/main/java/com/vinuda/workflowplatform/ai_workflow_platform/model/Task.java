package com.vinuda.workflowplatform.ai_workflow_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")

public class Task {

    @Id
    private String id;

    private String workflowId;
    private String name;
    private String description;
    private String tenantId;

    private LocalDateTime createdAt;
    // private TaskStatus status;

    // public enum TaskStatus {
    // PENDING, IN_PROGRESS, COMPLETED, REJECTED
    // }

    private List<String> fileUrls = new ArrayList<>();
}