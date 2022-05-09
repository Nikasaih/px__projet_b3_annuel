package com.backend.securitygw.dataobject.request;

import com.backend.securitygw.dataobject.aentity.PrivateUserDataAbs;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegistrationRequest extends PrivateUserDataAbs {
    @NotNull
    @NotBlank
    private String password;
}
