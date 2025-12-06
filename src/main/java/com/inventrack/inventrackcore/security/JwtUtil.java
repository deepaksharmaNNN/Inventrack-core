package com.inventrack.inventrackcore.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key = Keys.hmacShaKeyFor("replace-this-with-a-very-secure-secret-of-64-chars-minimum!".getBytes());
    private final long expirationMs = 1000L * 60 * 60 * 8; // 8 hours

    public String generateToken(String username, Long userId) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("uid", userId)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public boolean validate(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String getUsername(String token) {
        return parse(token).getBody().getSubject();
    }

    public Long getUserId(String token) {
        Object v = parse(token).getBody().get("uid");
        if (v instanceof Integer) return ((Integer) v).longValue();
        if (v instanceof Long) return (Long) v;
        return null;
    }
}