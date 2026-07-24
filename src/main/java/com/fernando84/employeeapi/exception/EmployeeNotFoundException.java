package com.fernando84.employeeapi.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Employee Not Found, Employee_id: " + id);
    }
}
