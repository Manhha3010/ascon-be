package com.exsoft.momedumerchant.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "type")
    private String type;


}
