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

    Set<AppUserRole> basicAuthority = new HashSet<>(List.of(AppUserRole.ROLE_ADMIN, AppUserRole.ROLE_CUSTOMER, AppUserRole.ROLE_STORE_MANAGER));
    Set<AppUserRole> storeManagerAuthority = new HashSet<>(List.of(AppUserRole.ROLE_ADMIN, AppUserRole.ROLE_STORE_MANAGER));
    Set<AppUserRole> adminAuthority = new HashSet<>(List.of(AppUserRole.ROLE_ADMIN));
    Set<AppUserRole> superadminAuthority = new HashSet<>(List.of(AppUserRole.ROLE_SUPERADMIN));

    @Around("execution(* *(..)) && @annotation(CustomerRequired)")
    public Object basicRequired(ProceedingJoinPoint jp) throws Throwable {
        return authorizationService.filter(jp, request.getHeader("authentication"), basicAuthority, "basic required");
    }

    @Around("execution(* *(..)) && @annotation(StoreManagerRequired)")
    public Object storeManagerRequired(ProceedingJoinPoint jp) throws Throwable {
        return authorizationService.filter(jp, request.getHeader("authentication"), storeManagerAuthority, "basic required");
    }

    @Around("execution(* *(..)) && @annotation(AdminRequired)")
    public Object adminRequired(ProceedingJoinPoint jp) throws Throwable {
        return authorizationService.filter(jp, request.getHeader("authentication"), adminAuthority, "basic required");
    }

    @Around("execution(* *(..)) && @annotation(SuperadminRequired)")
    public Object superadminRequired(ProceedingJoinPoint jp) throws Throwable {
        return authorizationService.filter(jp, request.getHeader("authentication"), superadminAuthority, "basic required");
    }

}
