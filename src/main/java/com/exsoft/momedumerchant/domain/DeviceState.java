package com.exsoft.momedumerchant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "device_state")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class DeviceState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fan")
    private Boolean fan;

    @Column(name = "light")
    private Boolean light;

    @Column(name = "sprinkler")
    private Boolean sprinkler;

    @Column(name = "pump")
    private Boolean pump;

    @Column(name = "is_fire")
    private Boolean is_fire;

    @Column(name = "created_at")
    private Instant createdAt;
}
