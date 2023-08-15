package com.exsoft.momedumerchant.repository;

import com.exsoft.momedumerchant.domain.Staff;
import com.exsoft.momedumerchant.domain.StaffRole;
import com.exsoft.momedumerchant.repository.base.MomEduRepository;

import java.util.Optional;

public interface StaffRoleRepository extends MomEduRepository<StaffRole, Long> {

    Optional<StaffRole> findFirstByStaff(Staff staff);
}
