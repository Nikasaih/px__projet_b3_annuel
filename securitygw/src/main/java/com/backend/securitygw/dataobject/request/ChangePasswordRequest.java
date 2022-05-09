package com.backend.securitygw.dataobject.request;


import com.backend.securitygw.dataobject.aentity.LoggedUserRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ChangePasswordRequest extends LoggedUserRequest {
    @NotNull
    @NotBlank
    private String newPwd;
}
