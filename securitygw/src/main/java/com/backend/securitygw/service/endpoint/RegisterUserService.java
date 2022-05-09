package com.backend.securitygw.service.endpoint;

import com.backend.securitygw.common.enumerator.ConfirmationTokenType;
import com.backend.securitygw.common.exception.EmailAlreadyTakenExc;
import com.backend.securitygw.dataobject.request.RegistrationRequest;
import com.backend.securitygw.dataobject.sqlentity.ConfirmationToken;
import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.ConfirmationTokenSqlRepository;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import com.backend.securitygw.service.encryptor.PasswordEncoder;
import com.backend.securitygw.service.miniservices.ConfirmationTokenGeneratorService;
import com.backend.securitygw.service.miniservices.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterUserService {
    final UserSqlRepository userSqlRepository;
    final PasswordEncoder passwordEncoder;
    final ConfirmationTokenSqlRepository confirmationTokenSqlRepository;
    final EmailSenderService emailSenderService;
    final ConfirmationTokenGeneratorService confirmationTokenGeneratorService;
    ModelMapper mapper = new ModelMapper();

    public void registerUser(RegistrationRequest registrationRequest) throws EmailAlreadyTakenExc {
        if (userSqlRepository.findByEmail(registrationRequest.getEmail()).isEmpty()) {
            throw new EmailAlreadyTakenExc();
        }
        UserSqlEntity userSqlEntity = mapper.map(registrationRequest, UserSqlEntity.class);

        final String userSalt = UUID.randomUUID().toString();
        userSqlEntity.setHashedPasswordSalt(userSalt);

        final String hashedPassword = passwordEncoder.hashPwd(registrationRequest.getPassword(), userSqlEntity.getHashedPasswordSalt());
        userSqlEntity.setHashedPassword(hashedPassword);

        userSqlRepository.save(userSqlEntity);

        ConfirmationToken confirmationToken = confirmationTokenGeneratorService
                .generateConfirmationToken(userSqlEntity, ConfirmationTokenType.REGISTER);

        String registrationConfirmationUrl = String.format("/confirm-registration?token=%s", confirmationToken.getToken());
        emailSenderService.sendRegistrationEmail(userSqlEntity.getEmail(), registrationConfirmationUrl);
    }


    public boolean enableAppUserByToken(String token, ConfirmationTokenType tokenType) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenSqlRepository.findByToken(token);
        if (confirmationToken.isEmpty()) {
            return false;
        }

        if (confirmationToken.get().getConfirmationTokenType() != tokenType) {
            return false;
        }

        confirmationToken.get().setConfirmedAt(LocalDateTime.now());
        confirmationTokenSqlRepository.save(confirmationToken.get());
        enableAppUserByEmail(confirmationToken.get().getAppUser().getEmail());
        return true;
    }

    public void enableAppUserByEmail(String email) {
        Optional<UserSqlEntity> user = userSqlRepository.findByEmail(email);
        if (user.isEmpty()) {
            return;
        }

        user.get().setIsEnabled(true);
        userSqlRepository.save(user.get());
    }
}
