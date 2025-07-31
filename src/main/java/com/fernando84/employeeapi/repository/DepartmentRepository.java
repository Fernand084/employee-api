package com.fernando84.employeeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernando84.employeeapi.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
