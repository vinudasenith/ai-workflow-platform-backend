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

    // Create a new department
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Get departments by organization
    public List<Department> getDepartmentsByOrganization(String organizationId) {
        return departmentRepository.findByOrganizationId(organizationId);
    }

    // delete department by id
    public boolean deleteDepartmentByCode(String departmentCode) {
        Department dept = departmentRepository.findByDepartmentCode(departmentCode);
        if (dept != null) {
            departmentRepository.delete(dept);
            return true;
        }
        return false;
    }

    // get all departments by tenantId
    public List<Department> getDepartmentsByTenantId(String tenantId) {
        return departmentRepository.findByTenantId(tenantId);
    }

}
