package btree.projetpro.backend.lib.service.security;

import btree.projetpro.backend.lib.dataobject.entity.security.ConfirmationToken;
import btree.projetpro.backend.lib.dataobject.entity.security.User;
import btree.projetpro.backend.lib.dataobject.repository.security.ConfirmationTokenRepository;
import btree.projetpro.backend.lib.dataobject.request.RegistrationRequest;
import btree.projetpro.backend.lib.service.EmailSenderService;
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
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String token = customUserDetailsService.signUpUser(
                mapper.map(request, User.class)
        );

        String link = String.format("http://localhost:8081/api/v1/registration/%s%s", "confirm?token=", token);

        emailSenderService.sendEmail(request.getEmail(), "confimation token", link);

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(() -> new IllegalStateException("token not found"));


        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email Already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }


        confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
        customUserDetailsService.enableAppUser(confirmationToken.getUser().getEmail());

        return "confirmed";
    }
}
