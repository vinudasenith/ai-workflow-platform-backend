package com.vinuda.workflowplatform.ai_workflow_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "organization")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Organization {
    @Id
    private String id;

    private String name;
    private String tenantId;
    private String description;
    private String industry;

    private String ownerName;
    private String ownerEmail;
    private String ownerPassword;
    private String phoneNumber;

    private boolean approved = false;

}
