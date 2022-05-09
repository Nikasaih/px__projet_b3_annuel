package com.backend.securitygw.service.endpoint;

import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.common.exception.EmailAlreadyTakenExc;
import com.backend.securitygw.dataobject.request.RegistrationRequest;
import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import com.backend.securitygw.service.encryptor.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegisterUserService {
    final UserSqlRepository userSqlRepository;
    final PasswordEncoder passwordEncoder;
    final ModelMapper mapper = new ModelMapper();

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

        //-------------
        //Todo Replace the following by send email
        try {
            enableAppUserByEmail(userSqlEntity.getEmail());
        } catch (CredentialNotMatchingAccount e) {
            return;
        }
        //-------------
    }

    public void enableAppUserByEmail(String email) throws CredentialNotMatchingAccount {
        Optional<UserSqlEntity> user = userSqlRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new CredentialNotMatchingAccount();
        }

        user.get().setIsEnabled(true);
        userSqlRepository.save(user.get());
    }
}
