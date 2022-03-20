package spd.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.backend.common.exception.EmailAlreadyTakenExc;
import spd.backend.common.exception.EmailNotValidExc;
import spd.backend.dataobject.accountrequest.RegistrationRequest;
import spd.backend.service.RegistrationService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;
    String responseMessage = "Check your email for validate registration, if you don't receive a mail, it's possible that an account already exists";

    @PostMapping
    public ResponseEntity<?> register(@Valid RegistrationRequest registrationRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.status(400).body(errors);
        }
        try {
            registrationService.register(registrationRequest);
            ResponseEntity.status(200).body(responseMessage);
        } catch (EmailAlreadyTakenExc e) {
            return ResponseEntity.status(200).body(responseMessage);
        } catch (EmailNotValidExc e) {
            return ResponseEntity.status(400).body(e.toString());
        }

        return ResponseEntity.status(500).body("something went wrong");
    }
}
