package com.backend.securitygw.service.miniservices;

import com.backend.securitygw.dataobject.response.JwtDatagram;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements Serializable {
    public static final long DAY_OF_VALIDITY = 1;
    public static final long HOUR_OF_VALIDITY = 1;
    public static final long JWT_TOKEN_VALIDITY = DAY_OF_VALIDITY * HOUR_OF_VALIDITY * 60 * 60; //Day hour minutes second
    private static final long serialVersionUID = 234234523523L;
    ModelMapper mapper = new ModelMapper();
    @Value("${jwt.secret}")
    private String jwtSecret;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }


    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    //generate token for user
    public String generateToken(JwtDatagram jwtDatagram) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(jwtDatagram.getFirstName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public JwtDatagram parseJwt(String jwtRaw) {
        Map<String, Object> payload = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwtRaw).getBody();
        return mapper.map(payload, JwtDatagram.class);
    }

    //validate token
    /*
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }*/
}
