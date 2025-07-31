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

    @Query("""
                SELECT new com.fernando84.employeeapi.DTO.DepartmentSalaryAverageDTO(d.id, AVG(s.amount))
                FROM DepartmentEmployee de
                JOIN de.department d
                JOIN de.employee e
                JOIN Salary s ON s.id.employeeId = e.id
                GROUP BY d.id
            """)
    List<DepartmentSalaryAverageDTO> findAverageSalaryByDepartment();

}
