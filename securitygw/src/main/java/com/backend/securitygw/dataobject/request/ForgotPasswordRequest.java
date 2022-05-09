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
    @NotBlank
    String token;
    @NotNull
    @NotBlank
    String newPwd;
    @NotNull
    @NotBlank
    ConfirmationTokenType confirmationTokenType;
}
