package com.vinuda.workflowplatform.ai_workflow_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "departments")

public class Department {

    @Id
    private String id;

    private String name;
    private String organizationId;
    private String description;
    private String tenantId;

}
