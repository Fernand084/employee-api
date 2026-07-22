package com.fernando84.employeeapi.model;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentManagerId implements Serializable {
    @Column(name = "employee_id")
    private Long employeeId;
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id", length = 4)
    private String departmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DepartmentManagerId))
            return false;
        DepartmentManagerId that = (DepartmentManagerId) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, departmentId);
    }

}
