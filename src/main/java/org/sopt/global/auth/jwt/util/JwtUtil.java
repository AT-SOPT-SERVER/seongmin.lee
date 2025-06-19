package org.sopt.global.auth.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    private final Long accessTokenExpiredMs;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret, @Value("${spring.jwt.access_token_expired_ms}")Long accessTokenExpiredMs){
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenExpiredMs = accessTokenExpiredMs;
    }

    public Long getId(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    public String getUsername(String token){
        Claims claims = getClaims(token);
        return claims.get("username", String.class);
    }

    public Optional<String> extractToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer ")){
            return Optional.of(authorization.substring(("Bearer ".length())));
        }

        return Optional.empty();
    }

    // JWT 토큰의 유효성을 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            // JWT 토큰 파싱 및 서명 검증
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (JwtException e) {
            // 서명이 유효하지 않은 경우
            return false;
        }
    }

    public boolean isExpired(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public Claims getClaims(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    public String createAccessToken(Long id, String username){
        return Jwts.builder()
                .claim("id", id)
                .claim("username", username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiredMs))
                .signWith(secretKey)
                .compact();
    }
}
