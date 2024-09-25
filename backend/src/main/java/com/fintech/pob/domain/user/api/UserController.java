package com.fintech.pob.domain.user.api;

import com.fintech.pob.domain.user.application.LocalUserService;
import com.fintech.pob.domain.user.application.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final LocalUserService localUserService;

    @Autowired
    public UserController(UserService userService, LocalUserService localUserService) {
        this.userService = userService;
        this.localUserService = localUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestParam String email, HttpSession session) {
        try {
            String userKey = userService.createUserAccount(email);
            session.setAttribute("userKey", userKey);
            return ResponseEntity.ok("사용자 계정 생성 성공: " + userKey);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("계정 생성 실패: " + e.getMessage());
        }
    }

    @PostMapping("/regist")
    public ResponseEntity<String> registUser(@RequestParam String userName, @RequestParam String userPassword,
                                             @RequestParam int userSubscriptionType, HttpSession session) {
//        String userKey = (String) session.getAttribute("1cf012ec-c2bc-4d96-96c8-f311c883a3e7");
        UUID userKey = UUID.fromString("58898a6b-0535-48df-a47f-437e61b92c59");

        if (userKey == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효한 세션이 아닙니다.");
        }
        try {
            localUserService.saveUser(userKey, userName, userPassword, userSubscriptionType);
            return ResponseEntity.ok("사용자 정보 저장 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자 정보 저장 실패");
        }
    }
}
