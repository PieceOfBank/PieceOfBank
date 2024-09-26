package com.fintech.pob.domain.user.api;

import com.fintech.pob.domain.user.application.LocalUserService;
import com.fintech.pob.domain.user.application.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
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

    private final UserService  userService;
    private final LocalUserService localUserService;

    @Autowired
    public UserController(UserService userService, LocalUserService localUserService) {
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

    @Getter
    @Setter
    public static class CreateUserRequest {
        private String email;
    }


    @PostMapping("/regist")
    public ResponseEntity<String> registUser(@RequestBody UserRequest userReqeust, HttpSession session) {
        String userKeyString = (String) session.getAttribute("userKey");

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

    @Getter
    @Setter
    public static class UserRequest {
        private String userName;
        private String userPassword;
        private int userSubscriptionType;
    }
}
