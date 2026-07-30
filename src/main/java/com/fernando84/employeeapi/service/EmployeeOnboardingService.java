package com.fernando84.employeeapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernando84.employeeapi.DTO.EmployeeOnboardingRequest;
import com.fernando84.employeeapi.exception.DepartmentNotFoundException;
import com.fernando84.employeeapi.model.*;
import com.fernando84.employeeapi.repository.*;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmployeeOnboardingService {
    private static final LocalDate OPEN_ENDED_DATE = LocalDate.of(9999, 1, 1);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentEmployeeRepository departmentEmployeeRepository;
    private final SalaryRepository salaryRepository;
    private final TitlesRepository titlesRepository;

    @Transactional
    public Employee onboardEmployee(EmployeeOnboardingRequest request) {

        System.out.println("DEBUG OnboardingService - received request: " + request);

        // 1. Validamos que el departamento exista ANTES de crear nada
        // (fail-fast: mejor fallar aquí que a mitad de la transacción)
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new DepartmentNotFoundException(request.departmentId()));

        // 2. Creamos el empleado base primero (necesitamos su ID generado
        // para las tablas relacionadas)
        Employee employee = new Employee();
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setBirthDate(request.birthDate());
        employee.setGender(Gender.valueOf(request.gender()));
        employee.setHireDate(request.hireDate());
        employee = employeeRepository.save(employee);

        // 3. Asignación de departamento
        DepartmentEmployee deptAssignment = new DepartmentEmployee();
        deptAssignment.setId(new DepartmentEmployeeId(employee.getId(), department.getId()));
        deptAssignment.setEmployee(employee);
        deptAssignment.setDepartment(department);
        deptAssignment.setFromDate(request.hireDate());
        deptAssignment.setToDate(OPEN_ENDED_DATE);
        departmentEmployeeRepository.save(deptAssignment);

        // 4. Salario inicial
        Salary salary = new Salary();
        salary.setId(new SalaryId(employee.getId(), request.hireDate()));
        salary.setEmployee(employee);
        salary.setAmount(request.initialSalary());
        salary.setToDate(OPEN_ENDED_DATE);
        salaryRepository.save(salary);

        // 5. Título inicial
        Title title = new Title();
        title.setId(new TitleId(employee.getId(), request.initialTitle(), request.hireDate()));
        title.setEmployee(employee);
        title.setToDate(OPEN_ENDED_DATE);
        titlesRepository.save(title);

        return employee;
    }
}
