package com.fintech.pob.global.auth;

import com.fintech.pob.domain.user.service.LocalUserService;
import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.global.auth.jwt.JwtUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final LocalUserService localUserService;

    public AuthController(JwtUtil jwtUtil, LocalUserService localUserService) {
        this.jwtUtil = jwtUtil;
        this.localUserService = localUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authResquest) {
        User user = localUserService.authenticate(authResquest.getUserName(), authResquest.getPassword());

        if (user != null) {
            // Jwt 토큰 생성
            String token = jwtUtil.generateToken(String.valueOf(user.getUserKey()), user.getSubscriptionType());
            // 권한과 함께 생성
            return ResponseEntity.ok(new AuthResponse(token, user.getSubscriptionType()));
        } else {
            return ResponseEntity.status(401).body("로그인 실패");
        }
    }

    @Getter
    public static class AuthResponse {
        private String token;
        private int subscriptionType;

        public AuthResponse(String token, int subscriptionType) {
            this.token = token;
            this.subscriptionType = subscriptionType;
        }
    }

    @Getter
    @Setter
    public static class AuthRequest {
        private String userName;
        private String password;
    }

}
