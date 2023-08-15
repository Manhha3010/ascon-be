package com.exsoft.momedumerchant.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "Username is not empty")
    private String username;

    @NotEmpty(message = "Password is not empty")
    private String password;

}
