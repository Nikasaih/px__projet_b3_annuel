package com.backend.securitygw.testutility;

import com.backend.securitygw.common.exception.EmailAlreadyTakenExc;
import com.backend.securitygw.dataobject.sqlentity.AppUser;
import com.backend.securitygw.service.securityservices.AppUserService;
import org.springframework.boot.test.web.client.TestRestTemplate;

public final class AuthUtility {
    final static String ADMIN_EMAIL = "admin@test.com";
    final static String ADMIN_PASSWORD = "adminPassword";
    final static String USER_EMAIL = "user@test.com";
    final static String USER_PASSWORD = "userPassword";

    public static void registerAdmin(AppUserService appUserService) throws EmailAlreadyTakenExc {
        //admin registration
        AppUser admin = new AppUser();
        admin.setEmail(ADMIN_EMAIL);
        admin.setPassword(ADMIN_PASSWORD);
        appUserService.signUpUser(admin);
        appUserService.enableAppUser(ADMIN_EMAIL);
        appUserService.grantAdminRole(ADMIN_EMAIL);
    }

    public static void registerUser(AppUserService appUserService) throws EmailAlreadyTakenExc {
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
