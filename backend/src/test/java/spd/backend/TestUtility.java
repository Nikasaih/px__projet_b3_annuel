package spd.backend;

import org.springframework.boot.test.web.client.TestRestTemplate;
import spd.backend.common.exception.EmailAlreadyTaken;
import spd.backend.dataobject.sqlentity.AppUser;
import spd.backend.service.AppUserService;

public final class TestUtility {
    final static String ADMIN_EMAIL = "admin@test.com";
    final static String ADMIN_PASSWORD = "adminPassword";
    final static String USER_EMAIL = "user@test.com";
    final static String USER_PASSWORD = "userPassword";

    public static void registerAdmin(AppUserService appUserService) throws EmailAlreadyTaken {
        //admin registration
        AppUser admin = new AppUser();
        admin.setEmail(ADMIN_EMAIL);
        admin.setPassword(ADMIN_PASSWORD);
        appUserService.signUpUser(admin);
        appUserService.enableAppUser(ADMIN_EMAIL);
        appUserService.grantAdminRole(ADMIN_EMAIL);
    }

    public static void registerUser(AppUserService appUserService) throws EmailAlreadyTaken {
        //admin registration
        AppUser admin = new AppUser();
        admin.setEmail(USER_EMAIL);
        admin.setPassword(USER_PASSWORD);
        appUserService.signUpUser(admin);
        appUserService.enableAppUser(USER_EMAIL);
    }

    public static TestRestTemplate connectAsAdmin() {
        TestRestTemplate template = new TestRestTemplate();
        return template.withBasicAuth(ADMIN_EMAIL, ADMIN_PASSWORD);
    }

    public static TestRestTemplate connectAsUser() {
        TestRestTemplate template = new TestRestTemplate();
        return template.withBasicAuth(USER_EMAIL, USER_PASSWORD);
    }
}
