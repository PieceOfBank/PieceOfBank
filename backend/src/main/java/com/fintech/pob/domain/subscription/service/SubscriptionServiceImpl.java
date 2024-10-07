package com.fintech.pob.domain.subscription.service;

import com.fintech.pob.domain.subscription.dto.SubscriptionRequestDto;
import com.fintech.pob.domain.subscription.entity.Subscription;
import com.fintech.pob.domain.subscription.repository.SubscriptionRepository;
import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.repository.UserRepository;
import com.fintech.pob.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor

public class SubscriptionServiceImpl implements SubscriptionService{

    private final UserService userService;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    @Override
    public Subscription create(SubscriptionRequestDto dto) {

        User protectUser = userRepository.findByUserKey(dto.getUserKey()).orElse(null);
        User targetUser = userRepository.findByUserKey(dto.getTargetKey()).orElse(null);

        Subscription subscription = new Subscription();
        subscription.setProtectUser(protectUser);
        subscription.setTargetUser(targetUser);
        subscriptionRepository.save(subscription);
        return subscription;
    }



    @Override
    public Subscription findByTargetUserKey(UUID userKey) {
        return subscriptionRepository.findByTargetUser_UserKey(userKey);
    }

    @Override
    public Subscription update(UUID userKey) {
        return null;
    }

    @Override
    public void delete(UUID userKey) {

//        Subscription subscription = subscriptionRepository.findOne(userKey);
//        subscriptionRepository.delete(subscription);
    }
}
