package btree.projetpro.backend;

import btree.projetpro.backend.security.dao.UserRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/public/test")
    public String publicTest() {
        return "test Get public" + UserRole.ADMIN_ROLE.toString();
    }

    @GetMapping("/auth/test")
    public String authTest() {
        return "test Get auth";
    }

    @GetMapping("/admin/test")
    public String authAdmin() {
        return "test Get admin";
    }

}
