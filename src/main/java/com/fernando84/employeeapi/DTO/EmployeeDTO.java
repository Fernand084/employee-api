package com.fernando84.employeeapi.DTO;

import java.time.LocalDate;

public record EmployeeDTO(Long id, String firstName, String lastName, LocalDate hireDate) {
}