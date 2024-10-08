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
    public Optional<Subscription> findByTargetUserKey(UUID userKey) {
        return subscriptionRepository.findByTargetUser_UserKey(userKey);
    }

    @Override
    public Subscription update(UUID userKey) {
        return null;
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public void delete(UUID userKey) {

//        Subscription subscription = subscriptionRepository.findOne(userKey);
//        subscriptionRepository.delete(subscription);
    }

    @Override
    public void setOneTimeTransferLimit(UUID userKey,Long limit) {

        Subscription subscription  = subscriptionRepository.findByTargetUser_UserKey(userKey).orElse(null);
        subscription.setOneTimeTransferLimit(limit);

        subscriptionRepository.save(subscription);

    }

    @Override
    public void setDailyTransferLimit(UUID userKey,Long limit) {

        Subscription subscription  = subscriptionRepository.findByTargetUser_UserKey(userKey).orElse(null);
        subscription.setDailyTransferLimit(limit);
        subscriptionRepository.save(subscription);

    }

@Override
    public Long getOneTimeTransferLimit(UUID userKey) {
        Subscription subscription = subscriptionRepository.findByTargetUser_UserKey(userKey)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found for userKey: " + userKey));
        return subscription.getOneTimeTransferLimit();
    }

    @Override
    public Long getDailyTransferLimit(UUID userKey) {
        Subscription subscription = subscriptionRepository.findByTargetUser_UserKey(userKey)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found for userKey: " + userKey));
        return subscription.getDailyTransferLimit();
    }

    @Override
    public Subscription getSubscriptionByProtectUserKey(UUID userKey) {
        return subscriptionRepository.findByProtectUserUserKey(userKey);
    }

}
