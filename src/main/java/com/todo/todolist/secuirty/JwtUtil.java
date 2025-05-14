package com.todo.todolist.secuirty;

import com.todo.todolist.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    private SecretKey key;

    // Initializes the key after the class is instantiated and the jwtSecret is injected,
    // preventing the repeated creation of the key and enhancing performance
    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return !isExpired(claims);
        } catch (SecurityException e) {
            throw new InvalidTokenException("INVALID_JWT_SIGNATURE");
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException("INVALID_JWT");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("EXPIRED_JWT");
        } catch (UnsupportedJwtException e) {
            throw new InvalidTokenException("UNSUPPORTED_JWT");
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("EMPTY_CLAIMS");
        } catch (Exception ex){
            throw new InvalidTokenException("INVALID_TOKEN");
        }
    }

    public boolean isExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public Date extractIssuedAt(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getIssuedAt();
    }

    public Date extractExpiryDate(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }



}
