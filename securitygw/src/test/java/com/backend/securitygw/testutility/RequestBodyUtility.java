package com.backend.securitygw.testutility;

import com.backend.securitygw.dataobject.request.ChangeEmailRequest;
import com.backend.securitygw.dataobject.request.ChangePasswordRequest;
import com.backend.securitygw.dataobject.request.LoginRequest;
import com.backend.securitygw.dataobject.request.RegistrationRequest;

public class RequestBodyUtility {
    private static final String UTIL_FIRST_NAME = "firstName";
    private static final String UTIL_LAST_NAME = "lastName";
    private static final String UTIL_EMAIL = "email@email.com";
    private static final String UTIL_PASSWORD = "somePassword";

    public static RegistrationRequest defaultRegistrationRequest() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName(UTIL_FIRST_NAME);
        registrationRequest.setLastName(UTIL_LAST_NAME);
        registrationRequest.setEmail(UTIL_EMAIL);
        registrationRequest.setPassword(UTIL_PASSWORD);
        return registrationRequest;
    }

    public static RegistrationRequest faultRegistrationRequest() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName(UTIL_FIRST_NAME);
        registrationRequest.setLastName(UTIL_LAST_NAME);
        registrationRequest.setPassword(UTIL_PASSWORD);
        return registrationRequest;
    }

    public static LoginRequest defaultLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(UTIL_PASSWORD);
        loginRequest.setEmailLogin(UTIL_EMAIL);
        return loginRequest;
    }

    public static LoginRequest faultLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmailLogin(UTIL_EMAIL);
        return loginRequest;
    }

    public static ChangePasswordRequest defaultChangePasswordRequest() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setEmail(UTIL_EMAIL);
        changePasswordRequest.setCurrentPwd(UTIL_PASSWORD);
        changePasswordRequest.setNewPwd(UTIL_PASSWORD + "new");
        return changePasswordRequest;
    }

    public static ChangePasswordRequest faultChangePasswordRequest() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setEmail(UTIL_EMAIL);
        changePasswordRequest.setCurrentPwd(UTIL_PASSWORD);
        return changePasswordRequest;
    }

    public static ChangeEmailRequest defaultChangeEmailRequest() {
        ChangeEmailRequest changeEmailRequest = new ChangeEmailRequest();
        changeEmailRequest.setCurrentEmail(UTIL_EMAIL);
        changeEmailRequest.setNewEmail("new" + UTIL_EMAIL);
        changeEmailRequest.setPassword(UTIL_PASSWORD);
        return changeEmailRequest;
    }

    public static ChangeEmailRequest faultChangeEmailRequest() {
        ChangeEmailRequest changeEmailRequest = new ChangeEmailRequest();
        changeEmailRequest.setCurrentEmail(UTIL_EMAIL);
        changeEmailRequest.setPassword(UTIL_PASSWORD);
        return changeEmailRequest;
    }
}
