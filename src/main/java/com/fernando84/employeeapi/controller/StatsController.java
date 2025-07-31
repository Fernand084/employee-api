package com.fernando84.employeeapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando84.employeeapi.DTO.EmployeesPerDepartmentDTO;
import com.fernando84.employeeapi.projections.GenderDistributionDTOProjection;
import com.fernando84.employeeapi.projections.TitleHistoryDTOProjection;
import com.fernando84.employeeapi.service.DepartmentEmployeeService;
import com.fernando84.employeeapi.service.TitleService;

@RestController
@RequestMapping("/api/stats")
public class StatsController {
    private final DepartmentEmployeeService departmentEmployeeService;
    private final TitleService titleService;

    public StatsController(DepartmentEmployeeService de, TitleService ts) {
        this.departmentEmployeeService = de;
        this.titleService = ts;
    }

    @GetMapping("/employees-per-department")
    public ResponseEntity<List<EmployeesPerDepartmentDTO>> getEmployeeCounts() {
        return ResponseEntity.ok(departmentEmployeeService.countActiveEmployeesPerDepartment());
    }

    @GetMapping("/gender-distribution")
    public ResponseEntity<List<GenderDistributionDTOProjection>> getGenderCount() {
        return ResponseEntity.ok(departmentEmployeeService.countGenderByDepartment());
    }

    @GetMapping("/titles-history")
    public ResponseEntity<List<TitleHistoryDTOProjection>> getTitleHistory() {
        return ResponseEntity.ok(titleService.getTitleHistory());
    }
}
