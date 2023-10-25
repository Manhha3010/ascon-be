package com.exsoft.momedumerchant.repository;

import com.exsoft.momedumerchant.domain.SensorState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorStateRepository extends JpaRepository<SensorState, Long> {
    SensorState findFirstByOrderByIdDesc();
}
