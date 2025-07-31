package com.fernando84.employeeapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fernando84.employeeapi.model.DepartmentManager;
import com.fernando84.employeeapi.model.DepartmentManagerId;
import com.fernando84.employeeapi.model.Employee;

public interface DepartmentManagerRepository extends JpaRepository<DepartmentManager, DepartmentManagerId> {
    @Query(value = """
            SELECT e.*, dm.from_date
            FROM employees.employee e
            JOIN employees.department_manager dm
            ON e.id = dm.employee_id
            WHERE dm.to_date = '9999-01-01' and department_id = ?
            ORDER BY dm.from_date desc
            """, countQuery = """
            SELECT e.*, dm.from_date
            FROM employees.employee e
            JOIN employees.department_manager dm
            ON e.id = dm.employee_id
            WHERE dm.to_date = '9999-01-01' and department_id = ?
            ORDER BY dm.from_date desc
            """, nativeQuery = true)
    Page<Employee> findManagersByDepartmentId(Pageable pageable, String id);
}
