package com.backend.securitygw.dataobject.request;

import com.backend.securitygw.common.enumerator.ConfirmationTokenType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ForgotPasswordRequest {
    @NotNull(message = "confirmationTokenType should not be null")
    private ConfirmationTokenType confirmationTokenType;
    @NotNull
    @NotBlank(message = "token should not be blank")
    private String token;
    @NotNull
    @NotBlank(message = "newPwd should not be blank")
    private String newPwd;
}
