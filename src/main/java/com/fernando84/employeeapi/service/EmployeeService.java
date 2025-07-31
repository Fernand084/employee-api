package com.fernando84.employeeapi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.fernando84.employeeapi.DTO.EmployeeDTO;
import com.fernando84.employeeapi.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository repo) {
        this.employeeRepository = repo;
    }

    public Page<EmployeeDTO> getAllEmployees(PageRequest pageable) {
        return employeeRepository.findActiveEmployees(pageable)
                .map(e -> new EmployeeDTO(e.getId(), e.getFirstName(), e.getLastName(), e.getHireDate()));
    }

    public Page<EmployeeDTO> getNotActiveEmployees(PageRequest pageable) {
        return employeeRepository.findNotActiveEmployees(pageable)
                .map(e -> new EmployeeDTO(e.getId(), e.getFirstName(), e.getLastName(), e.getHireDate()));
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(e -> new EmployeeDTO(e.getId(), e.getFirstName(), e.getLastName(), e.getHireDate()));

    }
}
