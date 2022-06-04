package com.backend.securitygw.common.exception;

import com.backend.securitygw.common.enumerator.AppUserRole;

public class JwtDurationForRoleNotAvailableExc extends Exception {
    public JwtDurationForRoleNotAvailableExc(AppUserRole appUserRole) {
        super(String.format("JwtDurationForRoleNotAvailableExc %s", appUserRole.toString()));
    }
}
