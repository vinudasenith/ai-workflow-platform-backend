package com.vinuda.workflowplatform.ai_workflow_platform.service;

import com.vinuda.workflowplatform.ai_workflow_platform.model.Department;
import com.vinuda.workflowplatform.ai_workflow_platform.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department creaDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Department> getDepartmentsByOrganization(String organizationId) {
        return departmentRepository.findByOrganizationId(organizationId);
    }

}
