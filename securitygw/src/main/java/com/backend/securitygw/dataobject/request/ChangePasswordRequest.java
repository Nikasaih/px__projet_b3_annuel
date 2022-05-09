package com.backend.securitygw.dataobject.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ChangePasswordRequest extends UserCurrentCredential {
    @NotNull
    @NotBlank
    private String newPwd;
}
