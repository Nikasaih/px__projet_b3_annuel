package btree.projetpro.backend.lib.controller.security;

import btree.projetpro.backend.lib.dataobject.entity.security.UserEntity;
import btree.projetpro.backend.lib.dataobject.request.ChangeEmailRequest;
import btree.projetpro.backend.lib.dataobject.request.ChangePasswordRequest;
import btree.projetpro.backend.lib.security.jwt.JwtRequest;
import btree.projetpro.backend.lib.security.jwt.JwtResponse;
import btree.projetpro.backend.lib.service.security.CustomUserDetailsService;
import btree.projetpro.backend.lib.service.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/logout-successful")
    public String logoutSuccess() {
        return "disconnect success";
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws IllegalStateException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtRequest.getUsername(), jwtRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new IllegalStateException("Invalid credential :", e);
        }

        final UserEntity userDetails = (UserEntity) customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        final String token = jwtService.generateToken(userDetails);

        return new JwtResponse(token);
    }

    @GetMapping("/pwd-forgot/{email}")
    public String pwdForgot(@PathVariable("email") String email) {
        return customUserDetailsService.pwdForgot(email);
    }

    @PostMapping("/change-pwd")
    public String changePwd(@RequestBody ChangePasswordRequest changePasswordDto) {
        return customUserDetailsService.changePwd(changePasswordDto);
    }

    @PostMapping("/change-mail")
    public String changeMail(@RequestBody ChangeEmailRequest changeEmailDto) {
        return customUserDetailsService.changeEmail(changeEmailDto);
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
