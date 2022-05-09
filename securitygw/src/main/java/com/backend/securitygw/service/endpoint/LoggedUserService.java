package com.backend.securitygw.service.endpoint;

import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.dataobject.request.ChangeEmailRequest;
import com.backend.securitygw.dataobject.request.ChangePasswordRequest;
import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import com.backend.securitygw.service.encryptor.PasswordEncoder;
import com.backend.securitygw.service.miniservices.JwtService;
import com.backend.securitygw.service.miniservices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoggedUserService {
    final UserSqlRepository userSqlRepository;
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;
    final UserService userService;

    public void changeEmail(ChangeEmailRequest request) throws CredentialNotMatchingAccount {
        Optional<UserSqlEntity> appUser = userSqlRepository.findByEmail(request.getCurrentEmail());
        if (appUser.isEmpty()) {
            throw new CredentialNotMatchingAccount();
        }
        if (!userService.validateCredential(appUser.get(), request.getCurrentPwd())) {
            return;
        }

        appUser.get().setEmail(request.getNewEmail());
        userSqlRepository.save(appUser.get());
    }

    public void changePwd(ChangePasswordRequest request) throws CredentialNotMatchingAccount {
        Optional<UserSqlEntity> appUser = userSqlRepository.findByEmail(request.getCurrentEmail());
        if (appUser.isEmpty()) {
            throw new CredentialNotMatchingAccount();
        }
        if (!userService.validateCredential(appUser.get(), request.getCurrentPwd())) {
            return;
        }

        userService.changePwd(appUser.get(), request.getNewPwd());
    }
}
