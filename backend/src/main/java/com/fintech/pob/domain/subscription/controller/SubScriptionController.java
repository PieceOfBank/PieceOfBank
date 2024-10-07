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
    public ResponseEntity<Optional<Subscription>> getSubscription(HttpSession session) {
        //  UUID userKey = UUID.fromString("58898a6b-0535-48df-a47f-437e61b92c59");

        String key = (String) jwtUtil.extractUserKey(token);
        UUID userKey = UUID.fromString(key);


        Optional<Subscription> subscriptions = subscriptionService.findByTargetUserKey(userKey);


        return ResponseEntity.ok(subscriptions);
    }



}
