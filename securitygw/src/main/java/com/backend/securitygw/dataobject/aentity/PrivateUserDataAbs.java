package com.backend.securitygw.dataobject.aentity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Getter
@Setter
public abstract class PrivateUserDataAbs extends PublicUserDataAbs {
    @NotNull
    @NotBlank
    @Email
    private String email;
}
