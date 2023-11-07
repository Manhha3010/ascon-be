package com.exsoft.momedumerchant.repository;

import com.exsoft.momedumerchant.domain.SensorState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorStateRepository extends JpaRepository<SensorState, Long> {
    SensorState findFirstByOrderByIdDesc();



    @Query(value = "SELECT * FROM sensor_state ORDER BY id DESC LIMIT 25", nativeQuery = true)
    List<SensorState> findAllByOrderByIdDescLimit25();

}
