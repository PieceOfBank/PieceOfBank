package com.fintech.pob.domain.user.service;

import com.fintech.pob.domain.user.entity.User;

import java.util.UUID;

public interface LocalUserService {
    void saveUser(UUID userKey, String userName, String userPassword, int userSubscriptionType);

    User authenticate(String userName, String password);

    User findByUserKey(String userKey);
}
