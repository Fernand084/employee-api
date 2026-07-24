package com.fernando84.employeeapi.exception;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(String id) {
        super("Department Not Found, Department_id: " + id);
    }
}
