package com.backend.securitygw.service.endpoint;

import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.dataobject.request.ChangeEmailRequest;
import com.backend.securitygw.dataobject.request.ChangePasswordRequest;
import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import com.backend.securitygw.service.encryptor.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoggedUserService {
    final UserSqlRepository userSqlRepository;
    final PasswordEncoder passwordEncoder;

    public void ChangeEmail(ChangeEmailRequest request) throws CredentialNotMatchingAccount {
        Optional<UserSqlEntity> appUser = userSqlRepository.findByEmail(request.getCurrentEmail());
        if (appUser.isEmpty()) {
            throw new CredentialNotMatchingAccount();
        }
        final boolean validateCredential = passwordEncoder.validatePwd(
                request.getCurrentPwd(),
                appUser.get().getHashedPasswordSalt(),
                appUser.get().getHashedPassword());
        if (!validateCredential) {
            return;
        }

        appUser.get().setEmail(request.getNewEmail());
        userSqlRepository.save(appUser.get());
    }

    public void ChangePwd(ChangePasswordRequest request) throws CredentialNotMatchingAccount {
        Optional<UserSqlEntity> appUser = userSqlRepository.findByEmail(request.getCurrentEmail());
        if (appUser.isEmpty()) {
            throw new CredentialNotMatchingAccount();
        }
        final boolean validateCredential = passwordEncoder.validatePwd(
                request.getCurrentPwd(),
                appUser.get().getHashedPasswordSalt(),
                appUser.get().getHashedPassword());
        if (!validateCredential) {
            return;
        }

        final String newHashedPwd = passwordEncoder.hashPwd(request.getNewPwd(), appUser.get().getHashedPasswordSalt());
        appUser.get().setHashedPassword(newHashedPwd);
        userSqlRepository.save(appUser.get());
    }


}
