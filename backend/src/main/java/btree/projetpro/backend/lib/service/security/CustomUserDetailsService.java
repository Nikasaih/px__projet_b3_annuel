package btree.projetpro.backend.lib.service.security;

import btree.projetpro.backend.lib.dataobject.entity.security.ConfirmationToken;
import btree.projetpro.backend.lib.dataobject.entity.security.UserEntity;
import btree.projetpro.backend.lib.dataobject.repository.security.ConfirmationTokenRepository;
import btree.projetpro.backend.lib.dataobject.repository.security.CustomUserRepository;
import btree.projetpro.backend.lib.dataobject.request.ChangeEmailRequest;
import btree.projetpro.backend.lib.dataobject.request.ChangePasswordRequest;
import btree.projetpro.backend.lib.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final String USER_NOT_FOUND = "user with %s not found";
    Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    EmailValidatorService emailValidatorService;
    @Autowired
    private CustomUserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signUpUser(UserEntity user) {
        if (isUserExists(user.getEmail())) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return generateToken(user);
    }

    private boolean isUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private String generateToken(UserEntity user) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);

        confirmationTokenRepository.save(confirmationToken);

        return token;
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public String changeEmail(ChangeEmailRequest changeEmailDto) {
        try {
            UserEntity appUser = (UserEntity) loadUserByUsername(changeEmailDto.getCurrentEmail());

            if (!emailValidatorService.test(changeEmailDto.getNewEmail())) {
                throw new IllegalStateException("new email not valid");
            }
            if (isUserExists(changeEmailDto.getNewEmail())) {
                throw new IllegalStateException("email already taken");
            }

            appUser.setEmail(changeEmailDto.getNewEmail());

            userRepository.save(appUser);
            return "email Changes with success";
        } catch (RuntimeException e) {
            return e.toString();
        }
    }

    public String changePwd(ChangePasswordRequest changePasswordDto) {
        try {
            UserEntity appUser = (UserEntity) loadUserByUsername(changePasswordDto.getEmail());

            if (!bCryptPasswordEncoder.matches(changePasswordDto.getCurrentPwd(), appUser.getPassword())) {
                return "error, current password not match";
            }
            appUser.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPwd()));
            userRepository.save(appUser);

            return "password changes with success";
        } catch (RuntimeException e) {
            return e.toString();
        }
    }

    public String pwdForgot(String email) {
        try {
            UserEntity appUser = (UserEntity) loadUserByUsername(email);
            String token = generateToken(appUser);
            String link = String.format("http://localhost:8081/api/v1/registration/%s%s", "confirm?token=", token);

            emailSenderService.sendEmail(appUser.getEmail(), "confimation token", link);

            return link;
        } catch (RuntimeException e) {
            logger.info(e.toString());
        }

        return "email sent";
    }
}