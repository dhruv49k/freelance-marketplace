package com.freelance.backend.service;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import com.freelance.backend.entity.User;
import io.jsonwebtoken.Jwts;
import java.util.Date;

@Service
public class JWTService {
    private final SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

    public String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(secretKey)
                .compact();

    }

}