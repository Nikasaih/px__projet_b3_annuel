package com.backend.securitygw.dataobject.accountrequest;


import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String email;
    private String currentPwd;
    private String newPwd;
}
