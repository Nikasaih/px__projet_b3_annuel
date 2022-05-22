package com.backend.securitygw.aspect.auth;

import com.backend.securitygw.common.enumerator.AppUserRole;
import com.backend.securitygw.dataobject.response.JwtDatagram;
import com.backend.securitygw.service.miniservices.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.aspectj.lang.annotation.Aspect
@Component
@Slf4j
public class Aspect {

    @Autowired
    HttpServletRequest request;

    @Autowired
    JwtService jwtService;

    Set<AppUserRole> adminAuthority = new HashSet<>(List.of(AppUserRole.ROLE_ADMIN));
    Set<AppUserRole> basicAuthority = new HashSet<>(List.of(AppUserRole.ROLE_ADMIN, AppUserRole.ROLE_BASIC));

    @AuthNeeded
    @Around("execution(* *(..)) && @annotation(BasicRequired)")
    public Object basicRequired(ProceedingJoinPoint jp) throws Throwable {
        String authentication = request.getHeader("authentication");
        JwtDatagram jwtDatagram = jwtService.parseJwt(authentication);
        if (!basicAuthority.contains(jwtDatagram.getAppUserRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("admin needed");
        }
        Object[] args = jp.getArgs();
        return jp.proceed(args);
    }

    @AuthNeeded
    @Around("execution(* *(..)) && @annotation(AdminRequired)")
    public Object adminRequired(ProceedingJoinPoint jp) throws Throwable {
        String authentication = request.getHeader("authentication");
        JwtDatagram jwtDatagram = jwtService.parseJwt(authentication);
        if (!adminAuthority.contains(jwtDatagram.getAppUserRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("admin needed");
        }
        Object[] args = jp.getArgs();
        return jp.proceed(args);
    }

    @Around("execution(* *(..)) && @annotation(AuthNeeded)")
    public Object authNeeded(ProceedingJoinPoint jp) throws Throwable {

        String authentication = request.getHeader("authentication");
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("auth needed");
        }
        if (!jwtService.isTokenExpired(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Jwt not more available");
        }
        Object[] args = jp.getArgs();
        return jp.proceed(args);
    }
}
