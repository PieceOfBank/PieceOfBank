package com.fintech.pob.domain.subscription.service;


import com.fintech.pob.domain.subscription.dto.SubscriptionRequestDto;
import com.fintech.pob.domain.subscription.entity.Subscription;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionService {

    Subscription create(SubscriptionRequestDto subscriptionRequestDto);


    Subscription findByTargetUserKey(UUID userKey);

    Subscription update(UUID userKey);

    void delete(UUID userKey);
}
