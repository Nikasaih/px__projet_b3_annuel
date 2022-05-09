package com.backend.securitygw.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public abstract class LoggedUserRequest {
    @NotNull
    @NotBlank
    @Email
    private String currentEmail;
    @NotNull
    @NotBlank
    private String currentPwd;
}
