package com.fernando84.employeeapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fernando84.employeeapi.DTO.ManagerDTO;
import com.fernando84.employeeapi.repository.DepartmentManagerRepository;

@Service
public class DepartmentManagerService {

    private DepartmentManagerRepository departmentManagerRepository;

    public DepartmentManagerService(DepartmentManagerRepository dmr) {
        this.departmentManagerRepository = dmr;
    }

    public Page<ManagerDTO> findManagersByDepartmentId(PageRequest pagable, String id) {
        return departmentManagerRepository.findManagersByDepartmentId(pagable, id)
                .map(e -> new ManagerDTO(e.getId(), e.getFirstName(), e.getLastName(), e.getHireDate()));
    }
}
