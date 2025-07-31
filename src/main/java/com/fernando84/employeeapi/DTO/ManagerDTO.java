package com.fernando84.employeeapi.DTO;

import java.time.LocalDate;

public record ManagerDTO(Long id, String firstName, String lastName, LocalDate hireDate) {

}
