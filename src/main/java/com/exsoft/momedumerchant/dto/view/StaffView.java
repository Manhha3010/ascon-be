package com.exsoft.momedumerchant.dto.view;

import com.exsoft.momedumerchant.domain.Staff;
import lombok.*;
import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class StaffView implements Serializable {

    private Long id;
    private String username;
    private String fullname;
    private String avatar;
    private RoleView role;
    private Long status;

    public static StaffView from(Staff domain) {
        return StaffView.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .fullname(domain.getFullname())
                .avatar(domain.getAvatar())
                .status(domain.getStatus())
                .build();
    }
}
