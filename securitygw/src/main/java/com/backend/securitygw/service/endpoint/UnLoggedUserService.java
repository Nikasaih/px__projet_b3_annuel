package com.backend.securitygw.service.endpoint;

import com.backend.securitygw.common.enumerator.ConfirmationTokenType;
import com.backend.securitygw.common.exception.AccountNotEnableExc;
import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.dataobject.request.ForgotPasswordRequest;
import com.backend.securitygw.dataobject.request.UserCurrentCredential;
import com.backend.securitygw.dataobject.response.JwtDatagram;
import com.backend.securitygw.dataobject.sqlentity.ConfirmationToken;
import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.ConfirmationTokenSqlRepository;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import com.backend.securitygw.service.miniservices.ConfirmationTokenGeneratorService;
import com.backend.securitygw.service.miniservices.EmailSenderService;
import com.backend.securitygw.service.miniservices.JwtService;
import com.backend.securitygw.service.miniservices.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UnLoggedUserService {

    final ModelMapper mapper = new ModelMapper();
    @Autowired
    UserSqlRepository userSqlRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    ConfirmationTokenGeneratorService confirmationTokenGeneratorService;
    @Autowired
    ConfirmationTokenSqlRepository confirmationTokenSqlRepository;
    @Autowired
    UserService userService;

    public String signIn(UserCurrentCredential userCurrentCredential) throws CredentialNotMatchingAccount, AccountNotEnableExc {
        Optional<UserSqlEntity> user = userSqlRepository.findByEmail(userCurrentCredential.getCurrentEmail());
        if (user.isEmpty()) {
            throw new CredentialNotMatchingAccount();
        }
        if (!userService.validateCredential(user.get(), userCurrentCredential.getCurrentPwd())) {
            throw new CredentialNotMatchingAccount();
        }
        if (!user.get().getIsEnabled()) {
            throw new AccountNotEnableExc();
        }
        JwtDatagram jwtDatagram = mapper.map(user.get(), JwtDatagram.class);
        return jwtService.generateToken(jwtDatagram);
    }

    public void sendPwdForgot(String email) {
        Optional<UserSqlEntity> userSqlEntity = userSqlRepository.findByEmail(email);
        if (userSqlEntity.isEmpty()) {
            return;
        }
        ConfirmationToken confirmationToken = confirmationTokenGeneratorService
                .generateConfirmationToken(userSqlEntity.get(), ConfirmationTokenType.PWD_FORGOT);

        String registrationConfirmationUrl = String.format("/password-forgot?email=%s", confirmationToken.getToken());
        emailSenderService.sendRegistrationEmail(userSqlEntity.get().getEmail(), registrationConfirmationUrl);
    }

    public void updatePwdForgot(ForgotPasswordRequest forgotPasswordRequest) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenSqlRepository.findByToken(forgotPasswordRequest.getToken());
        if (confirmationToken.isEmpty()) {
            return;
        }
        if (confirmationToken.get().getConfirmationTokenType() != forgotPasswordRequest.getConfirmationTokenType()) {
            return;
        }

        confirmationToken.get().setConfirmedAt(LocalDateTime.now());
        confirmationTokenSqlRepository.save(confirmationToken.get());

        UserSqlEntity user = confirmationToken.get().getAppUser();

        userService.changePwd(user, forgotPasswordRequest.getNewPwd());
    }
}
