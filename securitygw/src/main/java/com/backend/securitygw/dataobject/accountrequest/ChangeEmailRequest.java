package com.backend.securitygw.dataobject.accountrequest;

import lombok.Getter;

@Getter
public class ChangeEmailRequest {
    private String currentEmail;
    private String newEmail;
}
