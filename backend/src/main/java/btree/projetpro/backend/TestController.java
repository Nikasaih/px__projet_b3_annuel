package btree.projetpro.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class TestController {
    @GetMapping("/test")
    public String miguel() {
        return "test Get";
    }
}
