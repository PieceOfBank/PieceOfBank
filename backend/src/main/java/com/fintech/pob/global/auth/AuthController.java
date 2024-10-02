package com.fintech.pob.global.auth;

import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.service.LocalUserService;
import com.fintech.pob.global.auth.dto.AccessTokenResponse;
import com.fintech.pob.global.auth.dto.AuthRequest;
import com.fintech.pob.global.auth.dto.AuthResponse;
import com.fintech.pob.global.auth.dto.RefreshRequest;
import com.fintech.pob.global.auth.jwt.JwtUtil;
import com.fintech.pob.global.auth.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final LocalUserService localUserService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(JwtUtil jwtUtil, LocalUserService localUserService, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.localUserService = localUserService;
        this.refreshTokenService = refreshTokenService;
    }

    // 로그인 할 때 access 랑 refresh 둘다 발급
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authResquest) {
        User user = localUserService.authenticate(authResquest.getUserName(), authResquest.getPassword());

        if (user != null) {
            // access 토큰 생성
            String accessToken = jwtUtil.generateAccessToken(String.valueOf(user.getUserKey()), user.getSubscriptionType());

            // refresh 토큰 생성
            String refreshToken = jwtUtil.generateRefreshToken(String.valueOf(user.getUserKey()));
            refreshTokenService.saveRefreshToken(String.valueOf(user.getUserKey()), refreshToken);

            // 권한과 함께 생성
            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, user.getSubscriptionType()));
        } else {
            return ResponseEntity.status(401).body("로그인 실패");
        }
    }

    // refresh 로 access재발급
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();
        String userKey = jwtUtil.extractUserKey(refreshToken);

        // redis로 유효성 검사
        String savedToken = refreshTokenService.getRefreshToken(userKey);
        if (savedToken != null && savedToken.equals(refreshToken) && jwtUtil.isTokenValid(refreshToken, userKey)) {
            User user = localUserService.findByUserKey(userKey);
            int subscriptionType = user.getSubscriptionType();


            // 새 토큰 발급
            String newAccessToken = jwtUtil.generateAccessToken(userKey, subscriptionType);
            return ResponseEntity.ok(new AccessTokenResponse(newAccessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한 검증 불가 refresh token");
        }
    }

}
