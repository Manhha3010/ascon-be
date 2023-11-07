package com.exsoft.momedumerchant.repository;

import com.exsoft.momedumerchant.domain.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceStateRepository extends JpaRepository<DeviceState, Long> {


    // find the latest device state
    DeviceState findFirstByOrderByIdDesc();
}
