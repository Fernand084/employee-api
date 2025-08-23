package com.fernando84.employeeapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fernando84.employeeapi.DTO.DepartmentSalaryAverageDTO;
import com.fernando84.employeeapi.DTO.SalaryDTO;
import com.fernando84.employeeapi.DTO.TopSalariesDTO;
import com.fernando84.employeeapi.model.Salary;
import com.fernando84.employeeapi.repository.SalaryRepository;

@Service
public class SalaryService {

        private SalaryRepository salaryRepository;

        public SalaryService(SalaryRepository sr) {
                this.salaryRepository = sr;
        }

        public Optional<SalaryDTO> getCurrentSalary(Long id) {
                return salaryRepository.findTopByIdEmployeeIdOrderByIdFromDateDesc(id)
                                .map(s -> new SalaryDTO(
                                                s.getId().getEmployeeId(),
                                                s.getAmount(),
                                                s.getId().getFromDate(),
                                                s.getToDate()));
        }

        public List<SalaryDTO> getSalaryHistory(Long id) {
                return salaryRepository.findByIdEmployeeId(id)
                                .stream()
                                .map(s -> new SalaryDTO(
                                                s.getId().getEmployeeId(),
                                                s.getAmount(),
                                                s.getId().getFromDate(),
                                                s.getToDate()))
                                .toList();
        }

        public List<TopSalariesDTO> getTopSalaries(String id) {
                return salaryRepository.findTopSalariesByDepartment(id);
        }

        public List<DepartmentSalaryAverageDTO> getAverageSalariesByDepartment() {
                return salaryRepository.findAverageSalaryByDepartment();
        }

}
