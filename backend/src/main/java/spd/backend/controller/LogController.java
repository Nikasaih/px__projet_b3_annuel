package spd.backend.controller;

import spd.backend.dataobject.accountrequest.ChangeEmailRequest;
import spd.backend.dataobject.accountrequest.ChangePasswordRequest;
import spd.backend.dataobject.jwt.JwtRequest;
import spd.backend.dataobject.jwt.JwtResponse;
import spd.backend.service.AppUserService;
import spd.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/logout-successful")
    public String logoutSuccess() {
        return "disconnect success";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtRequest.getUsername(), jwtRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credential :", e);
        }

        final UserDetails userDetails = appUserService.loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtService.generateToken(userDetails);

        return new JwtResponse(token);
    }

    @GetMapping("/pwd-forgot/{email}")
    public String pwdForgot(@PathVariable("email") String email) {
        return appUserService.pwdForgot(email);
    }

    @PostMapping("/change-pwd")
    public String changePwd(@RequestBody ChangePasswordRequest changePasswordDto) {
        return appUserService.changePwd(changePasswordDto);
    }

    @PostMapping("/change-mail")
    public String changeMail(@RequestBody ChangeEmailRequest changeEmailDto) {
        return appUserService.changeEmail(changeEmailDto);
    }

    //Useless
    @GetMapping("/")
    public String welcome() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "hello" + authentication.getName();
        }

        return "hello";
    }
}