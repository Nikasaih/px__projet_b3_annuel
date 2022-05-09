package com.backend.securitygw.controller;

import com.backend.securitygw.common.exception.AccountNotEnableExc;
import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.dataobject.request.UserCurrentCredential;
import com.backend.securitygw.service.endpoint.UnLoggedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UnLoggedController {
    UnLoggedUserService unLoggedUserService;

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody @Valid UserCurrentCredential userCurrentCredential, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().toString();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            String jwt = unLoggedUserService.signIn(userCurrentCredential);
            return ResponseEntity.status(HttpStatus.OK).body(jwt);
        } catch (CredentialNotMatchingAccount | AccountNotEnableExc e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }

    @PostMapping("/password-forgot")
    public ResponseEntity<String> pwdForgot(@RequestParam("email") String email) {
        unLoggedUserService.pwdForgot(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body("An email has been sent to change your password account. If you don't receive it, maybe it's because no account is link with your mail");
    }
}