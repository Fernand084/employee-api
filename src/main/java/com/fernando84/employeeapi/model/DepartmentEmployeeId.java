package com.fernando84.employeeapi.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEmployeeId implements Serializable {

    @Column(name = "employee_id")
    private Long employeeId;
    @Column(name = "department_id")
    private String departmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DepartmentEmployeeId))
            return false;
        DepartmentEmployeeId that = (DepartmentEmployeeId) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, departmentId);
    }

}
