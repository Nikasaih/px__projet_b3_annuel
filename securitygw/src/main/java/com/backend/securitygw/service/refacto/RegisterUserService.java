package com.backend.securitygw.service.refacto;

import com.backend.securitygw.common.exception.EmailAlreadyTakenExc;
import com.backend.securitygw.config.PasswordEncoder;
import com.backend.securitygw.dataobject.sqlentity.AppUser;
import com.backend.securitygw.dataobject.sqlrepository.AppUserRepository;
import com.backend.securitygw.service.miniservices.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RegisterUserService {
    final AppUserServiceUtils appUserServiceUtils;
    final PasswordEncoder passwordEncoder;
    final AppUserRepository appUserRepository;
    final EmailSenderService emailSenderService;
    //Todo change following
    final String REGISTRATION_ROOT_ROUTE = "http://localhost:8081/api/v1/registration/%s%s";
    ConfirmationTokenManagerService confirmationTokenManager;
    @Value("${pepper.secret}")
    private String pepper;

    public void registerUser(AppUser appUser) throws EmailAlreadyTakenExc {
        if (appUserServiceUtils.isUserExists(appUser.getEmail())) {
            throw new EmailAlreadyTakenExc();
        }

        final String userSalt = UUID.randomUUID().toString();
        appUser.setSalt(userSalt);

        final String encodedPassword = passwordEncoder.hashPwd(appUser.getPassword(), appUser.getSalt());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        //Todo Replace the following by send email
        enableAppUserByEmail(appUser.getEmail());
    }

    public void enableAppUserByEmail(String email) {
        AppUser user = (AppUser) appUserServiceUtils.loadUserByUsername(email);
        user.setEnabled(true);
        appUserRepository.save(user);
    }
}
