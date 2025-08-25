package com.fernando84.employeeapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando84.employeeapi.DTO.DepartmentSalaryAverageDTO;
import com.fernando84.employeeapi.DTO.TopSalariesDTO;
import com.fernando84.employeeapi.service.SalaryService;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    private SalaryService salaryService;

    public SalaryController(SalaryService ss) {
        this.salaryService = ss;
    }

    @GetMapping("/{id}/highest")
    public ResponseEntity<List<TopSalariesDTO>> getTopSalaries(@PathVariable String id) {
        return ResponseEntity.ok(salaryService.getTopSalaries(id));
    }

    @GetMapping("/average-by-department")
    public ResponseEntity<List<DepartmentSalaryAverageDTO>> getAverageSalariesByDepartment() {
        return ResponseEntity.ok(salaryService.getAverageSalariesByDepartment());
    }

}
