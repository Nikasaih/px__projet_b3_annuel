package btree.projetpro.backend.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class log {
    @GetMapping("/logout")
    public String logout() {
        return "success logout";
    }
}
