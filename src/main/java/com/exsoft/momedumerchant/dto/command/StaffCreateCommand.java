package com.exsoft.momedumerchant.dto.command;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StaffCreateCommand implements Serializable {

    @NotEmpty(message = "Username cannot be empty")
    @Email(message = "Email is wrong format")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password in min 8 character")
    private String password;

    @NotEmpty
    private String fullname;

    @NotEmpty
    private String role;

    private String avatar;

    private Long managerId;

    @NotNull
    private Long status;

    private String phoneNumber;


}
