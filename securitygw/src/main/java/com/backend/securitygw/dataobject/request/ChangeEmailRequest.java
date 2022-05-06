package com.backend.securitygw.dataobject.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeEmailRequest {
    private String currentEmail;
    private String password;
    private String newEmail;
}
