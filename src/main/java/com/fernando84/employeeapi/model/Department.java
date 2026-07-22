package com.fernando84.employeeapi.model;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department", schema = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id", length = 4)
    private String id;

    @OneToMany(mappedBy = "department")
    private List<DepartmentEmployee> departmentEmployees;

    @OneToMany(mappedBy = "department")
    private List<DepartmentManager> departmentManagers;

    @Column(name = "dept_name")
    private String deptName;
}
