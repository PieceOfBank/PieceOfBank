package com.fintech.pob.domain.user.controller;

import com.fintech.pob.domain.user.dto.request.CreateUserRequest;
import com.fintech.pob.domain.user.dto.request.UserRequest;
import com.fintech.pob.domain.user.service.LocalUserService;
import com.fintech.pob.domain.user.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;
    private final LocalUserService localUserService;

    @Autowired
    public UserController(UserServiceImpl userService, LocalUserService localUserService) {
        this.userService = userService;
        this.localUserService = localUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request, HttpSession session) {
        try {
            String userKey = userService.createUserAccount(request.getEmail());
            session.setAttribute("userKey", userKey);
            return ResponseEntity.ok("사용자 계정 생성 성공: " + userKey);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("계정 생성 실패: " + e.getMessage());
        }
    }

    @PostMapping("/regist")
    public ResponseEntity<String> registUser(@RequestBody UserRequest userReqeust, HttpSession session) {
//        String userKeyString = (String) session.getAttribute("userKey");
        String userKeyString = "b2f01678-938e-4358-bf71-7b0bedb48f71";

        if (userKeyString == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효한 세션이 아닙니다.");
        }
        try {
            UUID userkey = UUID.fromString(userKeyString);
            localUserService.saveUser(userkey, userReqeust.getUserName(), userReqeust.getUserPassword(), userReqeust.getUserSubscriptionType());
            return ResponseEntity.ok("사용자 정보 저장 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자 정보 저장 실패");
        }
    }

}
