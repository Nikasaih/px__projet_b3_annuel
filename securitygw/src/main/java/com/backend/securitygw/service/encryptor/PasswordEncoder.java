package com.backend.securitygw.service.encryptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${pepper.secret}")
    private String pepper;

    public String hashPwd(String rawUserPwd, String userSalt) {
        String toHash = rawUserPwd + userSalt + pepper;
        return bCryptPasswordEncoder.encode(toHash);
    }

    public boolean validatePwd(String rawUserPwd, String userSalt, String savedHashedPwd) {
        return hashPwd(rawUserPwd, userSalt).equals(savedHashedPwd);
    }
}