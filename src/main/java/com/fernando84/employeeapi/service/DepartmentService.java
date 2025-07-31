package com.fernando84.employeeapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fernando84.employeeapi.DTO.DepartmentDTO;
import com.fernando84.employeeapi.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository dr) {
        this.departmentRepository = dr;
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(d -> new DepartmentDTO(d.getDeptName()))
                .toList();
    }

}
