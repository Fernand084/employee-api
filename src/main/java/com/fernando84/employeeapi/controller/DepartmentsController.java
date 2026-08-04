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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernando84.employeeapi.DTO.DepartmentCreateRequest;
import com.fernando84.employeeapi.DTO.DepartmentDTO;
import com.fernando84.employeeapi.DTO.EmployeeDTO;
import com.fernando84.employeeapi.DTO.ManagerDTO;
import com.fernando84.employeeapi.model.Department;
import com.fernando84.employeeapi.service.DepartmentCreateService;
import com.fernando84.employeeapi.service.DepartmentEmployeeService;
import com.fernando84.employeeapi.service.DepartmentManagerService;
import com.fernando84.employeeapi.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentsController {

    private DepartmentService departmentService;

    private DepartmentEmployeeService departmentEmployeeService;

    private DepartmentManagerService departmentManagerService;

    private DepartmentCreateService departmentCreateService;

    public DepartmentsController(DepartmentService ds, DepartmentEmployeeService des, DepartmentManagerService dms,
            DepartmentCreateService dcs) {
        this.departmentService = ds;
        this.departmentEmployeeService = des;
        this.departmentManagerService = dms;
        this.departmentCreateService = dcs;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}/employees")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<EmployeeDTO>> getDepartmentEmployees(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "250") int size,
            @PathVariable String id) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<EmployeeDTO> employees = departmentEmployeeService.getEmployeesByDepartmentId(pageable, id);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/managers")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ManagerDTO>> getManagersByDepartmentId(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @PathVariable String id) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<ManagerDTO> managers = departmentManagerService.findManagersByDepartmentId(pageable, id);
        return ResponseEntity.ok(managers);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Department> create(@RequestBody DepartmentCreateRequest request) {
        Department created = departmentCreateService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
