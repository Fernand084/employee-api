package com.fernando84.employeeapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando84.employeeapi.DTO.DepartmentSalaryAverageDTO;
import com.fernando84.employeeapi.DTO.SalaryDTO;
import com.fernando84.employeeapi.service.SalaryService;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    private SalaryService salaryService;

    public SalaryController(SalaryService ss) {
        this.salaryService = ss;
    }

    @GetMapping("/highest")
    public ResponseEntity<List<SalaryDTO>> getHighestSalaries() {
        return salaryService.getTopSalaries();
    }

    @GetMapping("/average-by-department")
    public ResponseEntity<List<DepartmentSalaryAverageDTO>> getAverageSalariesByDepartment() {
        return ResponseEntity.ok(salaryService.getAverageSalariesByDepartment());
    }

}
