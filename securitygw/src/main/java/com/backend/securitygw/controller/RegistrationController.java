package com.backend.securitygw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
   /* @Autowired
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
            //return ResponseEntity.status(400).body(e.toString());
        }

        return ResponseEntity.status(500).body("something went wrong");
    }

    //Todo confirmationtoken service on gw*/
}
