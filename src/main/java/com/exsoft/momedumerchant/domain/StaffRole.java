package com.exsoft.momedumerchant.domain;

import com.exsoft.momedumerchant.domain.base.Auditable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "me_staff_role")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class StaffRole extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

}
