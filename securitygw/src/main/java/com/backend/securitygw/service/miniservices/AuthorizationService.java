package com.backend.securitygw.service.miniservices;

import com.backend.securitygw.common.enumerator.AppUserRole;
import com.backend.securitygw.dataobject.response.JwtDatagram;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

@Service
@Slf4j
public class AuthorizationService {
    @Autowired
    JwtService jwtService;

    public Object filter(ProceedingJoinPoint jp, String authentication, Set<AppUserRole> roleAuthorized, String forbiddenMsg) throws Throwable {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Jwt not more available");
        }

        JwtDatagram jwtDatagram = jwtService.parseJwt(authentication);
        if (jwtDatagram == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Jwt expired");
        }
        if (!roleAuthorized.contains(jwtDatagram.getAppUserRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(forbiddenMsg);
        }
        Object[] args = jp.getArgs();

        Arrays.stream(args).forEach(e -> {
            if (e instanceof JwtDatagram) {
                e = jwtDatagram;
            }
        });
        return jp.proceed(args);
    }
}
