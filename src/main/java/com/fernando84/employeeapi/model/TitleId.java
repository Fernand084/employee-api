package com.fernando84.employeeapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleId implements Serializable {

    @Column(name = "employee_id")
    private Long employeeId;
    @Column(name = "title")
    private String title;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TitleId))
            return false;
        TitleId that = (TitleId) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(fromDate, that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, title, fromDate);
    }
}
