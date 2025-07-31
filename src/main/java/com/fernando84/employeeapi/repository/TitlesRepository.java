package com.fernando84.employeeapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fernando84.employeeapi.model.Title;
import com.fernando84.employeeapi.model.TitleId;
import com.fernando84.employeeapi.projections.TitleHistoryDTOProjection;

public interface TitlesRepository extends JpaRepository<Title, TitleId> {

    List<Title> findByIdEmployeeId(Long employee_id);

    @Query(value = """
            select
                EXTRACT(YEAR FROM from_date) AS year,
                title,
                count(employee_id) as total
            from employees.title
            group by year, title
            order by year;

            """, nativeQuery = true)
    List<TitleHistoryDTOProjection> getTitleHistory();

}
