package com.vinuda.workflowplatform.ai_workflow_platform.repository;

import com.vinuda.workflowplatform.ai_workflow_platform.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

    List<User> findByTenantId(String tenantId);

}
