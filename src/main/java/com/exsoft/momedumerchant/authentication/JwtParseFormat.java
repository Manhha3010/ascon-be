package com.exsoft.momedumerchant.authentication;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtParseFormat {

    private String username;
    private String iss;
    private Long userId;

}
