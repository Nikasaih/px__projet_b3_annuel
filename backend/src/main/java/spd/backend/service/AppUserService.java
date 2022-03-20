package spd.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spd.backend.common.enumerator.AppUserRole;
import spd.backend.common.exception.EmailAlreadyTakenExc;
import spd.backend.common.exception.EmailNotValidExc;
import spd.backend.dataobject.accountrequest.ChangeEmailRequest;
import spd.backend.dataobject.accountrequest.ChangePasswordRequest;
import spd.backend.dataobject.sqlentity.AppUser;
import spd.backend.dataobject.sqlentity.ConfirmationToken;
import spd.backend.dataobject.sqlrepository.AppUserRepository;
import spd.backend.dataobject.sqlrepository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class AppUserService implements UserDetailsService {
    private static final String USER_NOT_FOUND = "user with %s not found";
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    EmailValidatorService emailValidatorService;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser(AppUser appUser) throws EmailAlreadyTakenExc {
        if (isUserExists(appUser.getEmail())) {
            throw new EmailAlreadyTakenExc();
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        return generateToken(appUser);
    }

    public void grantAdminRole(String email) {
        if (!isUserExists(email)) {
            return;
        }
        AppUser futureAdmin = appUserRepository.findByEmail(email).get();
        futureAdmin.setAppUserRole(AppUserRole.ROLE_ADMIN);
        appUserRepository.save(futureAdmin);
    }

    private boolean isUserExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }

    private String generateToken(AppUser appUser) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);

        confirmationTokenRepository.save(confirmationToken);


        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    public String changeEmail(ChangeEmailRequest changeEmailDto) {
        try {
            AppUser appUser = (AppUser) loadUserByUsername(changeEmailDto.getCurrentEmail());

            if (!emailValidatorService.test(changeEmailDto.getNewEmail())) {
                throw new EmailNotValidExc();
            }
            if (isUserExists(changeEmailDto.getNewEmail())) {
                throw new EmailAlreadyTakenExc();
            }

            appUser.setEmail(changeEmailDto.getNewEmail());

            appUserRepository.save(appUser);
            return "email Changes with success";
        } catch (RuntimeException | EmailAlreadyTakenExc | EmailNotValidExc e) {
            return e.toString();
        }
    }

    public String changePwd(ChangePasswordRequest changePasswordDto) {
        try {
            AppUser appUser = (AppUser) loadUserByUsername(changePasswordDto.getEmail());

            if (!bCryptPasswordEncoder.matches(changePasswordDto.getCurrentPwd(), appUser.getPassword())) {
                return "error, current password not match";
            }
            appUser.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPwd()));
            appUserRepository.save(appUser);

            return "password changes with success";
        } catch (RuntimeException e) {
            return e.toString();
        }
    }

    public String pwdForgot(String email) {
        try {
            AppUser appUser = (AppUser) loadUserByUsername(email);
            String token = generateToken(appUser);
            String link = String.format("http://localhost:8081/api/v1/registration/%s%s", "confirm?token=", token);

            emailSenderService.sendEmail(appUser.getEmail(), "confimation token", link);

            return link;
        } catch (RuntimeException e) {
            log.error(e.toString());
        }

        return "email sent";
    }
}