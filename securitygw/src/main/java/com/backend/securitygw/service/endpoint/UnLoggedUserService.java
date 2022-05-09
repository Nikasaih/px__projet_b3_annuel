package com.backend.securitygw.service.endpoint;

import com.backend.securitygw.common.enumerator.ConfirmationTokenType;
import com.backend.securitygw.common.exception.AccountNotEnableExc;
import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.dataobject.request.UserCurrentCredential;
import com.backend.securitygw.dataobject.response.JwtDatagram;
import com.backend.securitygw.dataobject.sqlentity.ConfirmationToken;
import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import com.backend.securitygw.service.encryptor.PasswordEncoder;
import com.backend.securitygw.service.miniservices.ConfirmationTokenGeneratorService;
import com.backend.securitygw.service.miniservices.EmailSenderService;
import com.backend.securitygw.service.miniservices.JwtService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UnLoggedUserService {
    final PasswordEncoder passwordEncoder;
    final ModelMapper mapper = new ModelMapper();
    final UserSqlRepository userSqlRepository;
    final JwtService jwtService;
    final EmailSenderService emailSenderService;
    final ConfirmationTokenGeneratorService confirmationTokenGeneratorService;

    public String signIn(UserCurrentCredential userCurrentCredential) throws CredentialNotMatchingAccount, AccountNotEnableExc {
        Optional<UserSqlEntity> user = userSqlRepository.findByEmail(userCurrentCredential.getCurrentEmail());
        if (user.isEmpty()) {
            throw new CredentialNotMatchingAccount();
        }
        if (!passwordEncoder.validatePwd(userCurrentCredential.getCurrentPwd(), user.get().getHashedPasswordSalt(), user.get().getHashedPassword())) {
            throw new CredentialNotMatchingAccount();
        }
        if (!user.get().getIsEnabled()) {
            throw new AccountNotEnableExc();
        }
        JwtDatagram jwtDatagram = mapper.map(user.get(), JwtDatagram.class);
        return jwtService.generateToken(jwtDatagram);
    }

    public void pwdForgot(String email) {
        Optional<UserSqlEntity> userSqlEntity = userSqlRepository.findByEmail(email);
        if (userSqlEntity.isEmpty()) {
            return;
        }
        ConfirmationToken confirmationToken = confirmationTokenGeneratorService
                .generateConfirmationToken(userSqlEntity.get(), ConfirmationTokenType.PWD_FORGOT);

        String registrationConfirmationUrl = String.format("/password-forgot?email=%s", confirmationToken.getToken());
        emailSenderService.sendRegistrationEmail(userSqlEntity.get().getEmail(), registrationConfirmationUrl);
    }
}
