package com.fernando84.employeeapi.DTO;

import jakarta.persistence.SqlResultSetMapping;

@SqlResultSetMapping(name = "DepartmentSalaryAverageDTO")
public record DepartmentSalaryAverageDTO(String departmentId, Double averageSalary) {
}
