package com.fernando84.employeeapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fernando84.employeeapi.DTO.DepartmentSalaryAverageDTO;
import com.fernando84.employeeapi.model.Salary;
import com.fernando84.employeeapi.model.SalaryId;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
    Optional<Salary> findTopByIdEmployeeIdOrderByIdFromDateDesc(Long employee_Id);

    List<Salary> findByIdEmployeeId(Long employee_id);

    List<Salary> findTop10ByOrderByAmountDesc();

    @Query(value = """
                SELECT
                    de.department_id,
                    (select dept_name
                        from employees.department
                        where id = de.department_id) as dept_name,
                    AVG(s.amount)
                FROM (
                SELECT s.employee_id, s.amount
                FROM employees.salary s
                JOIN (
                    SELECT employee_id, MAX(from_date) AS latest_date
                    FROM employees.salary
                    GROUP BY employee_id
                ) latest ON s.employee_id = latest.employee_id AND s.from_date = latest.latest_date
                ) s
                JOIN employees.department_employee de
                ON s.employee_id = de.employee_id
                group by  de.department_id;
            """, nativeQuery = true)
    List<DepartmentSalaryAverageDTO> findAverageSalaryByDepartment();

}
