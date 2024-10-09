package com.fintech.pob.domain.subscription.controller;


import com.fintech.pob.domain.subscription.dto.SubscriptionRequestDto;
import com.fintech.pob.domain.subscription.entity.Subscription;
import com.fintech.pob.domain.subscription.service.SubscriptionService;
import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.service.LocalUserService;
import com.fintech.pob.domain.user.service.UserService;
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
    private final UserService userService;
    private final LocalUserService userLocalService;
    @PostMapping("/create")
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionRequestDto dto) {

        Subscription newSubscription = subscriptionService.create(dto);

        return ResponseEntity.ok(newSubscription);
    }

    @GetMapping("/findbytarget")
    public ResponseEntity<?> getSubscription(@RequestHeader("Authorization") String token) {

       String key = (String) jwtUtil.extractUserKey(token);
        UUID userKey = UUID.fromString(key);
        Optional<Subscription> subscriptions = Optional.ofNullable(subscriptionService.findByTargetUserKey(userKey).orElse(null));

       // String k= String.valueOf(subscriptions.get().getProtectUser().getUserKey());

       // User user = userLocalService.findByUserKey(k);

        //System.out.println(user);
        //System.out.println(user.getAccountNo());

        
        //String accountNo= user.getAccountNo();



        return ResponseEntity.ok(Optional.of(subscriptions.get()));
    }

    @GetMapping("/findbyprotect")
    public ResponseEntity<?> getSubscriptionByProtectUserKey(@RequestHeader("Authorization") String token) {
        String key = (String) jwtUtil.extractUserKey(token);
        UUID userKey = UUID.fromString(key);
        Subscription subscription = subscriptionService.getSubscriptionByProtectUserKey(userKey);
      //  UUID k= subscription.getTargetUser().getUserKey();

      //  User user = userLocalService.findByUserKey(k.toString());
     //   String accountNo= user.getAccountNo();

      //  System.out.println(user);
   //     System.out.println(user.getAccountNo());


        if (subscription != null) {
            return ResponseEntity.ok(subscription);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/setOneTime")
    public ResponseEntity<Subscription> setOneTimeTransferLimit(
            @RequestHeader("Authorization") String token, @RequestParam Long newLimit) {

        String key = (String) jwtUtil.extractUserKey(token);
        UUID userKey = UUID.fromString(key);

        System.out.println(newLimit);
         subscriptionService.setOneTimeTransferLimit(userKey, newLimit);
        return ResponseEntity.ok(null);

    }


    @PutMapping("/setDaily")
    public ResponseEntity<Subscription> setDailyTransferLimit(
            @RequestHeader("Authorization") String token, @RequestParam Long newLimit) {

        String key = (String) jwtUtil.extractUserKey(token);
        UUID userKey = UUID.fromString(key);

        subscriptionService.setDailyTransferLimit(userKey, newLimit);
        return ResponseEntity.ok(null);

    }


}
