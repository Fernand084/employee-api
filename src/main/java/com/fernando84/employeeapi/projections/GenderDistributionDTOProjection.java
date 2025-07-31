package com.fernando84.employeeapi.projections;

import com.fernando84.employeeapi.model.Gender;

public interface GenderDistributionDTOProjection {
    String getDeptId();

    String getDepartmentName();

    Gender getGender();

    Long getCount();
}
