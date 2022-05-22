package com.backend.securitygw.service.miniservices;

import com.backend.securitygw.common.enumerator.ConfirmationTokenType;
import com.backend.securitygw.dataobject.sqlentity.ConfirmationToken;
import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.ConfirmationTokenSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenGeneratorService {
    static Integer ConfirmTokenValidity = 30;
    @Autowired
    ConfirmationTokenSqlRepository confirmationTokenSqlRepository;

    public ConfirmationToken generateConfirmationToken(UserSqlEntity user, ConfirmationTokenType confirmationTokenType) {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(ConfirmTokenValidity),
                user,
                confirmationTokenType);
        confirmationTokenSqlRepository.save(confirmationToken);

        return confirmationToken;
    }
}
