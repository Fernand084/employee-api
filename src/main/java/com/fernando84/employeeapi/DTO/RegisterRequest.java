package com.fernando84.employeeapi.DTO;

import com.fernando84.employeeapi.model.Role;

public record RegisterRequest(String username, String password, Role role) {

}
