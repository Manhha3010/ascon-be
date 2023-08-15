package com.exsoft.momedumerchant.dto.view;

import com.exsoft.momedumerchant.domain.Role;
import lombok.*;
import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RoleView implements Serializable {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Boolean enable;

    public static RoleView from(Role domain) {
        return RoleView.builder()
                .id(domain.getId())
                .code(domain.getCode())
                .name(domain.getName())
                .description(domain.getDescription())
                .enable(domain.getEnable())
                .build();
    }

}
