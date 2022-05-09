package com.backend.securitygw.dataobject.request;

import com.backend.securitygw.dataobject.aentity.LoggedUserRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChangeEmailRequest extends LoggedUserRequest {
    @NotNull
    @NotBlank
    @Email
    private String newEmail;
}
