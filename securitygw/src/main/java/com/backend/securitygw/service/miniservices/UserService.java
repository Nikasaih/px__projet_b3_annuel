package com.backend.securitygw.service.miniservices;

import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import com.backend.securitygw.service.encryptor.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    final PasswordEncoder passwordEncoder;
    final UserSqlRepository userSqlRepository;

    public void changePwd(UserSqlEntity user, String newPwd) {
        final String newHashedPwd = passwordEncoder.hashPwd(newPwd, user.getHashedPasswordSalt());
        user.setHashedPassword(newHashedPwd);
        userSqlRepository.save(user);
    }

    public boolean validateCredential(UserSqlEntity user, String currentPwdRequested) {
        return passwordEncoder.validatePwd(
                currentPwdRequested,
                user.getHashedPasswordSalt(),
                user.getHashedPassword());
    }
}
