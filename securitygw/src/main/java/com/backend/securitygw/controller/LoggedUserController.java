package com.backend.securitygw.controller;

import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.dataobject.request.ChangeEmailRequest;
import com.backend.securitygw.dataobject.request.ChangePasswordRequest;
import com.backend.securitygw.service.endpoint.LoggedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class LoggedUserController {
    LoggedUserService loggedUserService;

    @PostMapping("/change-email")
    public ResponseEntity<String> changeEmail(@RequestBody @Valid ChangeEmailRequest changeEmailRequest, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().toString();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            loggedUserService.changeEmail(changeEmailRequest);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email changed successfully");
        } catch (CredentialNotMatchingAccount credentialNotMatchingAccount) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(credentialNotMatchingAccount.toString());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePwd(@RequestBody @Valid ChangePasswordRequest changePasswordRequest, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().toString();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        try {
            loggedUserService.changePwd(changePasswordRequest);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password changed successfully");
        } catch (CredentialNotMatchingAccount credentialNotMatchingAccount) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(credentialNotMatchingAccount.toString());
        }
    }
}
