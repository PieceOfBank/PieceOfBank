package com.fintech.pob.domain.subscription.controller;


import com.fintech.pob.domain.subscription.dto.SubscriptionRequestDto;
import com.fintech.pob.domain.subscription.entity.Subscription;
import com.fintech.pob.domain.subscription.service.SubscriptionService;
import com.fintech.pob.global.auth.jwt.JwtUtil;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/subscriptions")
@AllArgsConstructor
public class SubScriptionController {
    private final SubscriptionService subscriptionService;
    private final JwtUtil jwtUtil;


    @PostMapping("/create")
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionRequestDto dto) {

        Subscription newSubscription = subscriptionService.create(dto);
        return ResponseEntity.ok(newSubscription);
    }

    @GetMapping("/find")
    public ResponseEntity<Subscription> getSubscription(@RequestHeader("Authorization") String token) {

        String key = (String) session.getAttribute("userKey");
        UUID userKey = UUID.fromString(key);
        Subscription subscriptions = subscriptionService.findByTargetUserKey(userKey);
        return ResponseEntity.ok(subscriptions);
    }



}
