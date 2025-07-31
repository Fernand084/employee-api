package com.fernando84.employeeapi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fernando84.employeeapi.DTO.EmployeesPerDepartmentDTO;
import com.fernando84.employeeapi.model.DepartmentEmployee;
import com.fernando84.employeeapi.model.DepartmentEmployeeId;
import com.fernando84.employeeapi.model.Employee;
import com.fernando84.employeeapi.projections.GenderDistributionDTOProjection;

public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, DepartmentEmployeeId> {
    @Query(value = """
            SELECT e.*
            FROM employees.employee e
            JOIN employees.department_employee de ON e.id = de.employee_id
            WHERE de.to_date = '9999-01-01' and department_id = ?
            ORDER BY e.id
            """, countQuery = """
            SELECT COUNT(*)
            FROM employees.employee e
            JOIN employees.department_employee de ON e.id = de.employee_id
            WHERE de.to_date = '9999-01-01' and department_id = ?
            """, nativeQuery = true)
    Page<Employee> findByDepartmentId(Pageable pageable, String id);

    @Query(value = """
            SELECT
                    d.id,
                    d.dept_name,
                    COUNT(de.employee_id)
            FROM employees.department_employee de
            JOIN employees.department d
            ON de.department_id = d.id
            WHERE de.to_date = '9999-01-01'
            GROUP BY d.id, d.dept_name
            """, nativeQuery = true)
    List<EmployeesPerDepartmentDTO> countActiveEmployeesPerDepartment();

    @Query(value = """
            SELECT
                dept_info.department_id AS dept_id,
                dept_info.dept_name AS departmentName,
                e.gender AS gender,
                count(dept_info.employee_id) AS count
            FROM employees.employee e
            JOIN (
                SELECT
                    de.department_id,
                    d.dept_name,
                    de.employee_id
                FROM employees.department_employee de
                JOIN employees.department d
                ON d.id = de.department_id
                WHERE de.to_date = '9999-01-01'
            ) dept_info ON dept_info.employee_id = e.id
            GROUP BY dept_info.department_id, dept_info.dept_name, e.gender
            """, nativeQuery = true)
    List<GenderDistributionDTOProjection> countGenderByDepartment();

}
