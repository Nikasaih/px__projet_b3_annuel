package com.backend.securitygw.common;

import com.backend.securitygw.dataobject.request.RegistrationRequest;
import com.backend.securitygw.service.endpoint.RegisterUserService;
import com.backend.securitygw.service.endpoint.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupCaller {
    @Autowired
    RegisterUserService registerUserService;
    @Autowired
    UserRoleService userRoleService;

    @Value("${admin.email}")
    String adminEmail;
    @Value("${admin.pwd}")
    String adminPwd;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        RegistrationRequest adminRegistrationRequest = new RegistrationRequest();
        adminRegistrationRequest.setLastName("admin");
        adminRegistrationRequest.setEmail(adminEmail);
        adminRegistrationRequest.setPassword(adminPwd);
        adminRegistrationRequest.setFirstName("admin");
        try {
            registerUserService.registerUser(adminRegistrationRequest);
            registerUserService.enableAppUserByEmail(adminRegistrationRequest.getEmail());
            userRoleService.grantAdminRole(adminRegistrationRequest.getEmail());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
