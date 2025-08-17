package com.vinuda.workflowplatform.ai_workflow_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")

public class User {

    @Id
    private String id;

    private String email;
    private String password;

    private String firstName;
    private String lastName;

    private String tenantId; // for multi-tenant support
    private String organizationId; // organization within a tenant

    private Role role;

    private boolean approved = false;

    public enum Role {
        ADMIN, APPROVER, USER
    }

}
