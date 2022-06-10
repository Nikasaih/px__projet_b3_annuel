package com.backend.securitygw.dataobject.response;

import com.backend.securitygw.common.enumerator.AppUserRole;
import com.backend.securitygw.dataobject.aentity.PublicUserDataAbs;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDatagram extends PublicUserDataAbs {
    private Long id;
    private AppUserRole appUserRole;
}
