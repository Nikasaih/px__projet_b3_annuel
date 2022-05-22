package com.backend.securitygw.dataobject.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AskEmailPasswordForgotRequest {
    @NotNull
    @NotBlank(message = "emailToSend should not be empty")
    @Email
    private String emailToSend;
}
