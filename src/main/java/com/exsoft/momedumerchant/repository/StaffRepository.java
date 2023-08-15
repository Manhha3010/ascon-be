package com.exsoft.momedumerchant.repository;

import com.exsoft.momedumerchant.domain.Staff;
import com.exsoft.momedumerchant.repository.base.MomEduRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StaffRepository extends MomEduRepository<Staff, Long> {

    Optional<Staff> findFirstByUsername(String username);

    Optional<Staff> findFirstByUsernameAndStatus(String username, Long status);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndStatus(String username, Long status);

    @Query(value = "select st from Staff st " +
            "left join StaffRole sr on st.id = sr.staff.id " +
            "left join Role ro on ro.id = sr.role.id " +
            "where st.username = :username and st.status = :status and ro.code = :roleCode")
    Optional<Staff> getStaffByCondition(@Param("username") String username, @Param("status") Long status, @Param("roleCode") String roleCode);

}
