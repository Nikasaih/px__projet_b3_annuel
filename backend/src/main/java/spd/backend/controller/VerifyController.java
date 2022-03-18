package spd.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verify")
@Slf4j
public class VerifyController {
    @GetMapping
    ResponseEntity<String> VerifyRoutes() {
        return ResponseEntity.status(200).body("verify routes");
    }
}
