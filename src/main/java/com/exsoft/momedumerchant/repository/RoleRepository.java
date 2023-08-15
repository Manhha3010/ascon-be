package com.exsoft.momedumerchant.repository;

import com.exsoft.momedumerchant.domain.Role;
import com.exsoft.momedumerchant.repository.base.MomEduRepository;

import java.util.Optional;

public interface RoleRepository extends MomEduRepository<Role, Long> {

    Optional<Role> findFirstByCode(String code);
}
