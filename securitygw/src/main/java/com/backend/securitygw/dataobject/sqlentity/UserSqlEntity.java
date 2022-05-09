package com.backend.securitygw.dataobject.sqlentity;

import com.backend.securitygw.common.enumerator.AppUserRole;
import com.backend.securitygw.dataobject.aentity.PrivateUserDataAbs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserSqlEntity extends PrivateUserDataAbs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hashedPassword;
    private String hashedPasswordSalt;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole = AppUserRole.USER_ROLE;
    private Boolean isLocked = false;
    private Boolean isEnabled = false;
}
