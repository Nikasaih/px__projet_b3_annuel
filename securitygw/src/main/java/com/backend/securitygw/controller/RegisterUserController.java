package com.backend.securitygw.controller;

import com.backend.securitygw.common.enumerator.ConfirmationTokenType;
import com.backend.securitygw.dataobject.request.RegistrationRequest;
import com.backend.securitygw.service.endpoint.RegisterUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterUserController {
    RegisterUserService registerUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            registerUserService.registerUser(registrationRequest);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/confirm-registration")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token) {

        boolean jobDone = registerUserService.enableAppUserByToken(token, ConfirmationTokenType.REGISTER);
        if (!jobDone) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("some problem happened, maybe your request isn't appropriate");
        }
        return ResponseEntity.status(HttpStatus.OK).body("account enabled");
    }
}
