package com.fernando84.employeeapi.DTO;

import java.math.BigDecimal;

import jakarta.persistence.SqlResultSetMapping;

@SqlResultSetMapping(name = "DepartmentSalaryAverageDTO")
public record DepartmentSalaryAverageDTO(String departmentId, String deptName, BigDecimal averageSalary) {
}
