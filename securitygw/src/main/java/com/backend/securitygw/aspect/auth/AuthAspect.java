package com.backend.securitygw.aspect.auth;

import com.backend.securitygw.common.enumerator.AppUserRole;
import com.backend.securitygw.service.miniservices.AuthorizationService;
import com.backend.securitygw.service.miniservices.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.aspectj.lang.annotation.Aspect
@Component
@Slf4j
public class AuthAspect {

    @Autowired
    HttpServletRequest request;

    @Autowired
    JwtService jwtService;
    @Autowired
    AuthorizationService authorizationService;

    Set<AppUserRole> adminAuthority = new HashSet<>(List.of(AppUserRole.ROLE_ADMIN));
    Set<AppUserRole> basicAuthority = new HashSet<>(List.of(AppUserRole.ROLE_ADMIN, AppUserRole.ROLE_BASIC));

    @Around("execution(* *(..)) && @annotation(BasicRequired)")
    public Object basicRequired(ProceedingJoinPoint jp) throws Throwable {
        return authorizationService.filter(jp, request.getHeader("authentication"), basicAuthority, "basic required");
    }

    @Around("execution(* *(..)) && @annotation(AdminRequired)")
    public Object adminRequired(ProceedingJoinPoint jp) throws Throwable {
        log.info("admin check method start");
        return authorizationService.filter(jp, request.getHeader("authentication"), adminAuthority, "basic required");
    }


}
