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

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    // Access Token 만들기
    public String generateAccessToken(String userKey, int subscriptionType) {
        return createToken(userKey, subscriptionType, EXPIRATION_TIME);
    }

    // Refresh Token 만들기
    public String generateRefreshToken(String userKey) {
        return createToken(userKey, null, REFRESH_TOKEN_EXPIRATION_TIME);
    }


    // JWT 토큰 생성
    public String createToken(String userKey, Integer subscriptionType, long expirationTime) {
        Claims claims = Jwts.claims().setSubject(userKey);
        if (subscriptionType != null) {
            claims.put("subscriptionType", subscriptionType);

        }

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(key)
                .compact();
    }

    // JWT 토큰에서 userKey 추출
    public String extractUserKey(String token) {
        try {
            // 서명된 JWT를 파싱할 때는 parseClaimsJws()를 사용
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            // 예외 처리 및 로그 출력
            System.out.println("Error extracting user key from token: " + e.getMessage());
            throw new RuntimeException("JWT Parsing Error");
        }
    }


    // JWT 토큰에서 subscriptionType 추출
    public int extractSubscriptiontype(String token) {
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

