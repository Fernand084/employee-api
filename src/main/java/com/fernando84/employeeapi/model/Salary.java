package com.fernando84.employeeapi.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salary", schema = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    @EmbeddedId
    private SalaryId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "amount")
    private Long amount;
    @Column(name = "to_date")
    private LocalDate toDate;

}
