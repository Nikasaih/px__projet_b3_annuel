package com.backend.securitygw.service.miniservices;

import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import com.backend.securitygw.service.encryptor.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserSqlRepository userSqlRepository;

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
