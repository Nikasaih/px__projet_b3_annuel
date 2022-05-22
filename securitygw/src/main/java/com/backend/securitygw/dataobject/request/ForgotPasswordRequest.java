package com.backend.securitygw.dataobject.request;

import com.backend.securitygw.common.enumerator.ConfirmationTokenType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ForgotPasswordRequest {
    @NotNull
    @NotBlank(message = "token should not be blank")
    String token;
    @NotNull
    @NotBlank(message = "newPwd should not be blank")
    String newPwd;
    @NotNull(message = "confirmationTokenType should not be null")
    ConfirmationTokenType confirmationTokenType;
}
