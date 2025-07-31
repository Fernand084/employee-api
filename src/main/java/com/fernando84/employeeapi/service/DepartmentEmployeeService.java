package com.fernando84.employeeapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fernando84.employeeapi.DTO.EmployeeDTO;
import com.fernando84.employeeapi.DTO.EmployeesPerDepartmentDTO;
import com.fernando84.employeeapi.projections.GenderDistributionDTOProjection;
import com.fernando84.employeeapi.repository.DepartmentEmployeeRepository;

@Service
public class DepartmentEmployeeService {

    private DepartmentEmployeeRepository departmentEmployeeRepository;

    public DepartmentEmployeeService(DepartmentEmployeeRepository der) {
        this.departmentEmployeeRepository = der;
    }

    public Page<EmployeeDTO> getEmployeesByDepartmentId(PageRequest pageable, String id) {
        return departmentEmployeeRepository.findByDepartmentId(pageable, id)
                .map(e -> new EmployeeDTO(e.getId(), e.getFirstName(), e.getLastName(), e.getHireDate()));
    }

    public List<EmployeesPerDepartmentDTO> countActiveEmployeesPerDepartment() {
        return departmentEmployeeRepository.countActiveEmployeesPerDepartment();
    }

    public List<GenderDistributionDTOProjection> countGenderByDepartment() {
        return departmentEmployeeRepository.countGenderByDepartment();
    }

}
