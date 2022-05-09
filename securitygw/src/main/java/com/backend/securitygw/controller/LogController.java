package com.backend.securitygw.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {/*
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
    public LoginResponse authenticate(@RequestBody LoginRequest jwtRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtRequest.getEmailLogin(), jwtRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credential :", e);
        }

        final UserDetails userDetails = appUserService.loadUserByUsername(jwtRequest.getEmailLogin());

        final String token = jwtService.generateToken(userDetails);

        return new LoginResponse(token);
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
    }*/
}