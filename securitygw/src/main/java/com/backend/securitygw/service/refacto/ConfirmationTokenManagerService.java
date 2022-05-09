package com.backend.securitygw.service.refacto;

import com.backend.securitygw.common.enumerator.ConfirmationTokenType;
import com.backend.securitygw.common.exception.TokenAlreadyConfirmedExc;
import com.backend.securitygw.common.exception.TokenExpiredExc;
import com.backend.securitygw.common.exception.TokenNotFoundExc;
import com.backend.securitygw.dataobject.sqlentity.AppUser;
import com.backend.securitygw.dataobject.sqlentity.ConfirmationToken;
import com.backend.securitygw.dataobject.sqlrepository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ConfirmationTokenManagerService {
    Map<ConfirmationTokenType, CrudRepository> repositoryMap;
    ConfirmationTokenRepository confirmationTokenRepository;

    public String generateConfirmationToken(AppUser appUser, ConfirmationTokenType confirmationTokenType) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);

        repositoryMap.get(confirmationTokenType).save(confirmationToken);

        return token;
    }

    public RegisterConfirmationToken validateConfirmationToken(AppUser appUser, ConfirmationTokenType confirmationTokenType, String tokenToValidate) throws TokenNotFoundExc, TokenExpiredExc, TokenAlreadyConfirmedExc {
        if (repositoryMap == null) {
            initMap();
        }
        RegisterConfirmationToken confirmationToken =
                (RegisterConfirmationToken) repositoryMap.get(confirmationTokenType)
                        .findByToken(tokenToValidate)
                        .orElseThrow(() -> new TokenNotFoundExc());

        if (confirmationToken.getConfirmedAt() != null) {
            throw new TokenAlreadyConfirmedExc();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredExc();
        }


        confirmationToken.setConfirmedAt(LocalDateTime.now());
        repositoryMap.get(confirmationTokenType).save(confirmationToken);

        return confirmationToken;
    }
}
