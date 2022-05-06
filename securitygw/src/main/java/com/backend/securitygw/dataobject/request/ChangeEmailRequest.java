package com.backend.securitygw.dataobject.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangeEmailRequest {
    @NotNull
    @NotBlank
    @Email
    private String currentEmail;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    @Email
    private String newEmail;
}
