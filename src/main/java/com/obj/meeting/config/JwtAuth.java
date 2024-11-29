package com.obj.meeting.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuth {

    private final SecretKey secretKey;


    public JwtAuth(@Value("${meeting.jwt.secret-key}") String secretKeyStr) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes(StandardCharsets.UTF_8));
    }

public String generateToken(String username,String role) {
    return Jwts.builder()
            .claim("username",username)
            .claim("role",role)
            .signWith(secretKey)
            .compact();
}

public String getUserName(String token) {
    return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload().get("username",String.class);
}

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload().get("role",String.class);
    }


}
