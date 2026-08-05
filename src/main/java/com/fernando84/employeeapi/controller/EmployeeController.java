package com.fernando84.employeeapi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernando84.employeeapi.DTO.EmployeeDTO;
import com.fernando84.employeeapi.DTO.EmployeeOnboardingRequest;
import com.fernando84.employeeapi.DTO.EmployeeUpdateRequest;
import com.fernando84.employeeapi.DTO.SalaryDTO;
import com.fernando84.employeeapi.DTO.TitleDTO;
import com.fernando84.employeeapi.model.Employee;
import com.fernando84.employeeapi.service.EmployeeOnboardingService;
import com.fernando84.employeeapi.service.EmployeeService;
import com.fernando84.employeeapi.service.SalaryService;
import com.fernando84.employeeapi.service.TitleService;

import jakarta.validation.Valid;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/employees")
// @CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    private EmployeeService employeeService;

    private SalaryService salaryService;

    private TitleService titleService;

    private EmployeeOnboardingService employeeOnboardingService;

    public EmployeeController(EmployeeService es, SalaryService ss, TitleService ts, EmployeeOnboardingService eos) {
        this.employeeService = es;
        this.salaryService = ss;
        this.titleService = ts;
        this.employeeOnboardingService = eos;
    }

    // get all active employees
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Page<EmployeeDTO> getAllEmployees(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "250") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return employeeService.getAllEmployees(pageable);
    }

    // get all not active employees
    @GetMapping("/not-active")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<EmployeeDTO> getNotActiveEmployees(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "250") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return employeeService.getNotActiveEmployees(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/salary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalaryDTO> getEmployeeCurrentSalary(@PathVariable Long id) {
        return salaryService.getCurrentSalary(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}/salaries")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SalaryDTO>> getEmployeeSalaries(@PathVariable Long id) {
        List<SalaryDTO> salaries = salaryService.getSalaryHistory(id);
        if (salaries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(salaries);
    }

    @GetMapping("/{id}/titles")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TitleDTO>> getEmployeeTitles(@PathVariable Long id) {
        return titleService.getTitlesHistory(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeOnboardingRequest request) {
        EmployeeDTO created = employeeOnboardingService.onboardEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request) {
        EmployeeDTO updated = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(updated);
    }

    /*
     * private static final Logger log =
     * LoggerFactory.getLogger(GlobalExceptionHandler.class);
     * 
     * @PostMapping("/debug-raw")
     * public String debugRaw(@RequestBody String rawBody) {
     * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     * System.out.println(auth);
     * System.out.println("RAW BODY RECEIVED: [" + rawBody + "]");
     * return rawBody;
     * }
     */

}
