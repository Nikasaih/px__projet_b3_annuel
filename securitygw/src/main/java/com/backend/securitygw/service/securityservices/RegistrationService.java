package com.backend.securitygw.service.securityservices;


import com.backend.securitygw.common.exception.EmailAlreadyTakenExc;
import com.backend.securitygw.common.exception.EmailNotValidExc;
import com.backend.securitygw.common.exception.TokenExpiredExc;
import com.backend.securitygw.common.exception.TokenNotFoundExc;
import com.backend.securitygw.dataobject.request.RegistrationRequest;
import com.backend.securitygw.dataobject.sqlentity.AppUser;
import com.backend.securitygw.dataobject.sqlentity.ConfirmationToken;
import com.backend.securitygw.dataobject.sqlrepository.ConfirmationTokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistrationService {
    final ModelMapper mapper = new ModelMapper();
    @Autowired
    EmailValidatorService emailValidator;
    @Autowired
    AppUserService appUserService;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public String register(RegistrationRequest request) throws EmailAlreadyTakenExc, EmailNotValidExc {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new EmailNotValidExc();
        }

        String token = appUserService.signUpUser(
                mapper.map(request, AppUser.class)
        );

        String link = String.format("http://localhost:8081/api/v1/registration/%s%s", "confirm?token=", token);

        emailSenderService.sendEmail(request.getEmail(), "confimation token", link);

        return token;
    }

    @Transactional
    public String confirmToken(String token) throws EmailAlreadyTakenExc, TokenExpiredExc, TokenNotFoundExc {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(() -> new TokenNotFoundExc());


        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyTakenExc();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredExc();
        }


        confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());

        return "confirmed";
    }
}
