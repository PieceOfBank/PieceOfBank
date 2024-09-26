package com.fintech.pob.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.access-token-validity-in-seconds}")
    private long EXPIRATION_TIME;

    // JWT 토큰 생성
    public String generateToken(String userKey, int subscriptionType) {
        Claims claims = Jwts.claims().setSubject(userKey);
        claims.put("subscriptionType", subscriptionType);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // JWT 토큰에서 userKey 추출
    public String extractUserKey(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJwt(token).getBody().getSubject();
    }

    // JWT 토큰에서 subscriptionType 추출
    public int extractSubscriptiontyoe(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return (int) claims.get("subscriptionType");
    }

    // 토큰 유효성 검사
    public boolean isTokenValid(String token, String userKey) {
        final String extractedUserKey = extractUserKey(token);
        return (extractedUserKey.equals(userKey) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }


}
