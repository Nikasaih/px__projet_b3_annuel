package com.backend.securitygw.dataobject.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GrantRoleRequest {
    @Email
    @NotNull
    private String email;
}
