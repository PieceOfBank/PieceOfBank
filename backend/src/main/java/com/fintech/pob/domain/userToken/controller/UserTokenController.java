package com.fintech.pob.domain.userToken.controller;

import com.fintech.pob.domain.userToken.service.UserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/token")
public class UserTokenController {

    private final UserTokenService userTokenService;

    public UserTokenController(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @PostMapping()
    public ResponseEntity<String> saveToken(@RequestParam("userKey") UUID userKey, @RequestParam("token") String token) {
        userTokenService.saveUserToken(userKey, token);
        return ResponseEntity.ok("토큰이 저장되었습니다.");
    }

    @GetMapping()
    public ResponseEntity<?> getToken(@RequestParam("userKey") UUID userKey) {
        return userTokenService.getUserTokenByUserKey(userKey)
                .map(userToken -> ResponseEntity.ok(userToken.getToken()))
                .orElse(ResponseEntity.badRequest().body("해당 유저에게 토큰이 없습니다." + userKey));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteToken(@RequestParam("userKey") UUID userKey) {
        userTokenService.deleteUserToken(userKey);
        return ResponseEntity.ok("토큰이 삭제되었습니다.");
    }
}
