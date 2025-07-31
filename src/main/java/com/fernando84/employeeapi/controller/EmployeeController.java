package com.fernando84.employeeapi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernando84.employeeapi.DTO.EmployeeDTO;
import com.fernando84.employeeapi.DTO.SalaryDTO;
import com.fernando84.employeeapi.DTO.TitleDTO;
import com.fernando84.employeeapi.service.EmployeeService;
import com.fernando84.employeeapi.service.SalaryService;
import com.fernando84.employeeapi.service.TitleService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    private SalaryService salaryService;

    private TitleService titleService;

    public EmployeeController(EmployeeService es, SalaryService ss, TitleService ts) {
        this.employeeService = es;
        this.salaryService = ss;
        this.titleService = ts;
    }

    // get all active employees
    @GetMapping
    public Page<EmployeeDTO> getAllEmployees(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "250") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return employeeService.getAllEmployees(pageable);
    }

    // get all not active employees
    @GetMapping("/not-active")
    public Page<EmployeeDTO> getNotActiveEmployees(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "250") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return employeeService.getNotActiveEmployees(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/salary")
    public ResponseEntity<SalaryDTO> getEmployeeCurrentSalary(@PathVariable Long id) {
        return salaryService.getCurrentSalary(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}/salaries")
    public ResponseEntity<List<SalaryDTO>> getEmployeeSalaries(@PathVariable Long id) {
        List<SalaryDTO> salaries = salaryService.getSalaryHistory(id);
        if (salaries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(salaries);
    }

    @GetMapping("/{id}/titles")
    public ResponseEntity<List<TitleDTO>> getEmployeeTitles(@PathVariable Long id) {
        return titleService.getTitlesHistory(id);
    }

}
