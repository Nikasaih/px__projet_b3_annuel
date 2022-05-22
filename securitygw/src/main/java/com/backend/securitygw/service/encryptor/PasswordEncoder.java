package com.backend.securitygw.service.encryptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Objects;

@Service
public class PasswordEncoder {
    @Value("${secret.pepper}")
    private String pepper;

    public String hashPwd(String rawUserPwd, String userSalt) {
        try {
            byte[] salt = userSalt.getBytes(StandardCharsets.UTF_8);
            KeySpec spec = new PBEKeySpec(rawUserPwd.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Arrays.toString(hash);
        } catch (Exception e) {
            return e.toString();
        }
    }

    public boolean validatePwd(String rawUserPwd, String userSalt, String savedHashedPwd) {
        final String requestPasswordHashed = hashPwd(rawUserPwd, userSalt);
        return Objects.equals(requestPasswordHashed, savedHashedPwd);
    }
}