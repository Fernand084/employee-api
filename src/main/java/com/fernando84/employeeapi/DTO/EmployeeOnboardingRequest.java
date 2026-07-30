package com.fernando84.employeeapi.DTO;

import java.time.LocalDate;

public record EmployeeOnboardingRequest(
        String firstName,
        String lastName,
        LocalDate birthDate,
        String gender,
        LocalDate hireDate,
        String departmentId,
        Long initialSalary,
        String initialTitle) {

}
