package com.fernando84.employeeapi.DTO;

import java.time.LocalDate;

public record SalaryDTO(Long id, Long amount, LocalDate fromDate, LocalDate toDate) {
}
