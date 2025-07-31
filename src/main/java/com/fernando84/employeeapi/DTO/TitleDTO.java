package com.fernando84.employeeapi.DTO;

import java.time.LocalDate;

public record TitleDTO(String title, LocalDate fromDate, LocalDate toDate) {
}
