package com.backend.securitygw.dataobject.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    private String email;
    private String currentPwd;
    private String newPwd;
}
