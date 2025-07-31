package com.fernando84.employeeapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fernando84.employeeapi.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = """
            SELECT e.*
            FROM employees.employee e
            JOIN employees.department_employee de ON e.id = de.employee_id
            WHERE de.to_date = '9999-01-01'
            ORDER BY e.id
            """, countQuery = """
            SELECT COUNT(*)
            FROM employees.employee e
            JOIN employees.department_employee de ON e.id = de.employee_id
            WHERE de.to_date = '9999-01-01'
            """, nativeQuery = true)
    Page<Employee> findActiveEmployees(Pageable pageable);

    @Query(value = """
            SELECT e.*
            FROM employees.employee e
            JOIN employees.department_employee de ON e.id = de.employee_id
            WHERE de.to_date != '9999-01-01'
            ORDER BY e.id
            """, countQuery = """
            SELECT COUNT(*)
            FROM employees.employee e
            JOIN employees.department_employee de ON e.id = de.employee_id
            WHERE de.to_date !='9999-01-01'
            """, nativeQuery = true)
    Page<Employee> findNotActiveEmployees(Pageable pageable);

}
