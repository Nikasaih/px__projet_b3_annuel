package com.backend.securitygw.service.miniservices;

import com.backend.securitygw.dataobject.response.JwtDatagram;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtService implements Serializable {
    public static final long DAY_OF_VALIDITY = 1;
    public static final long HOUR_OF_VALIDITY = 1;
    public static final long JWT_TOKEN_VALIDITY = DAY_OF_VALIDITY * HOUR_OF_VALIDITY * 60 * 60; //Day hour minutes second
    private static final long serialVersionUID = 234234523523L;
    ModelMapper mapper = new ModelMapper();
    @Value("${secret.jwt}")
    private String jwtSecret;

    //generate token for user
    public String generateToken(JwtDatagram jwtDatagram) {
        Map<String, Object> claims = mapDatagramToClaim(jwtDatagram);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject("jwtDatagram")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    private Map<String, Object> mapDatagramToClaim(JwtDatagram jwtDatagram) {
        Map<String, Object> claims = new HashMap<>();
        for (Field field : jwtDatagram.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                claims.put(field.getName(), field.get(jwtDatagram));
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
        for (Field field : jwtDatagram.getClass().getSuperclass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                claims.put(field.getName(), field.get(jwtDatagram));
            } catch (Exception e) {
                log.error(e.toString());
            }
        }

        return claims;
    }

    public JwtDatagram parseJwt(String jwtRaw) {
        try {
            Map<String, Object> payload = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtRaw).getBody();
            return mapper.map(payload, JwtDatagram.class);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return null;
        }
    }
}
