package spd.backend.service;


import spd.backend.common.exception.EmailAlreadyTaken;
import spd.backend.common.exception.EmailNotValid;
import spd.backend.common.exception.TokenExpired;
import spd.backend.common.exception.TokenNotFound;
import spd.backend.dataobject.accountrequest.RegistrationRequest;
import spd.backend.dataobject.sqlentity.AppUser;
import spd.backend.dataobject.sqlentity.ConfirmationToken;
import spd.backend.dataobject.sqlrepository.ConfirmationTokenRepository;
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

    public String register(RegistrationRequest request) throws EmailAlreadyTaken, EmailNotValid {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new EmailNotValid();
        }

        String token = appUserService.signUpUser(
                mapper.map(request, AppUser.class)
        );

        String link = String.format("http://localhost:8081/api/v1/registration/%s%s", "confirm?token=", token);

        emailSenderService.sendEmail(request.getEmail(), "confimation token", link);

        return token;
    }

    @Transactional
    public String confirmToken(String token) throws EmailAlreadyTaken, TokenExpired, TokenNotFound {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(() -> new TokenNotFound());


        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyTaken();
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpired();
        }


        confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());

        return "confirmed";
    }
}
