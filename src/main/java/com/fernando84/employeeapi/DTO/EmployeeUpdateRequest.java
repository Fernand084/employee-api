package com.fernando84.employeeapi.DTO;

import java.time.LocalDate;
import java.util.Optional;

import com.fernando84.employeeapi.model.Gender;

import lombok.Data;

@Data
public class EmployeeUpdateRequest {
    Optional<String> firstName = Optional.empty();
    Optional<String> lastName = Optional.empty();
    Optional<LocalDate> birthDate = Optional.empty();
    Optional<Gender> gender = Optional.empty();
    Optional<LocalDate> hireDate = Optional.empty();
}
