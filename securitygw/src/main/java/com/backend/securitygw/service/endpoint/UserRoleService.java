package com.backend.securitygw.service.endpoint;

import com.backend.securitygw.common.enumerator.AppUserRole;
import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService {
    @Autowired
    UserSqlRepository userSqlRepository;

    public void grantStoreManagerRole(String email) throws CredentialNotMatchingAccount {
        changeRoleByEmail(email, AppUserRole.ROLE_STORE_MANAGER);
    }

    public void grantAdminRole(String email) throws CredentialNotMatchingAccount {
        changeRoleByEmail(email, AppUserRole.ROLE_ADMIN);
    }

    public void grantSuperadminRole(String email) throws CredentialNotMatchingAccount {
        changeRoleByEmail(email, AppUserRole.ROLE_SUPERADMIN);
    }

    private void changeRoleByEmail(String email, AppUserRole newRole) throws CredentialNotMatchingAccount {
        Optional<UserSqlEntity> appUser = userSqlRepository.findByEmail(email);
        if (appUser.isEmpty()) {
            throw new CredentialNotMatchingAccount();
        }
        appUser.get().setAppUserRole(newRole);
        userSqlRepository.save(appUser.get());
    }
}
