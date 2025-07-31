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
import com.fernando84.employeeapi.DTO.DepartmentDTO;
import com.fernando84.employeeapi.DTO.EmployeeDTO;
import com.fernando84.employeeapi.DTO.ManagerDTO;
import com.fernando84.employeeapi.service.DepartmentEmployeeService;
import com.fernando84.employeeapi.service.DepartmentManagerService;
import com.fernando84.employeeapi.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentsController {

    private DepartmentService departmentService;

    private DepartmentEmployeeService departmentEmployeeService;

    private DepartmentManagerService departmentManagerService;

    public DepartmentsController(DepartmentService ds, DepartmentEmployeeService des, DepartmentManagerService dms) {
        this.departmentService = ds;
        this.departmentEmployeeService = des;
        this.departmentManagerService = dms;
    }

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<Page<EmployeeDTO>> getDepartmentEmployees(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "250") int size,
            @PathVariable String id) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<EmployeeDTO> employees = departmentEmployeeService.getEmployeesByDepartmentId(pageable, id);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/manager")
    public ResponseEntity<Page<ManagerDTO>> getManagersByDepartmentId(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @PathVariable String id) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<ManagerDTO> managers = departmentManagerService.findManagersByDepartmentId(pageable, id);
        return ResponseEntity.ok(managers);
    }
}
