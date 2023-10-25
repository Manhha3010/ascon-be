package com.exsoft.momedumerchant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sensor_state")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class SensorState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "temperature")
    private Float temperature;

    @Column(name = "humidity")
    private Float humidity;

    @Column(name = "humidity_ground")
    private Integer humidityGround;

    @Column(name = "is_dark")
    private Boolean isDark;

    @Column(name = "created_at")
    private Instant createdAt;
}
