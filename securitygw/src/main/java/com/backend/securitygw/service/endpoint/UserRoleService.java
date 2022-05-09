package com.backend.securitygw.service.endpoint;

import com.backend.securitygw.common.enumerator.AppUserRole;
import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    final UserSqlRepository userSqlRepository;
    Set<AppUserRole> adminAuthority = new HashSet<>(List.of(AppUserRole.ROLE_ADMIN));
    Set<AppUserRole> basicAuthority = new HashSet<>(List.of(AppUserRole.ROLE_ADMIN, AppUserRole.USER_ROLE));

    public void grantAdminRole(String email) throws CredentialNotMatchingAccount {
        Optional<UserSqlEntity> appUser = userSqlRepository.findByEmail(email);
        if (appUser.isEmpty()) {
            throw new CredentialNotMatchingAccount();
        }
        appUser.get().setAppUserRole(AppUserRole.ROLE_ADMIN);
        userSqlRepository.save(appUser.get());
    }

    public boolean hasAdminAuthority(UserSqlEntity user) {
        return adminAuthority.contains(user.getAppUserRole());
    }

    public boolean hasBasicAuthority(UserSqlEntity user) {
        return basicAuthority.contains(user.getAppUserRole());
    }
}
