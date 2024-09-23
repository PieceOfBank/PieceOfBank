package com.fintech.pob.domain.user.entity;

import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class LocalUserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(String userName, String password){
        User user = new User();

        user.setUserName(userName);
        user.setUserPassword(password);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        user.setSubscriptionType();
    }
}
