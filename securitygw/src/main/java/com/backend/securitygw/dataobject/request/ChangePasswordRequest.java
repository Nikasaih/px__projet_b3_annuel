package com.backend.securitygw.dataobject.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangePasswordRequest {
    @NotNull
    @NotBlank
    @Email
    private String email;
    private String currentPwd;
    private String newPwd;
}
