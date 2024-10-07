package com.fintech.pob.domain.user.service;

import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Transactional
public class LocalUserServiceImpl implements LocalUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(UUID userKey, String userId, String userName, String userPassword, int userSubscriptionType) {
        User user = new User();
        if (userKey == null) {
            throw new IllegalArgumentException("userKey는 null이거나 비어 있을 수 없습니다.");
        }
        user.setUserKey(userKey);
        user.setUserName(userName);
        user.setUserId(userId);
        user.setUserPassword(userPassword);
        user.setSubscriptionType(userSubscriptionType);

        System.out.println(user.getUserKey());

        userRepository.save(user);
    }

    @Override
    public User authenticate(String userId, String password) {
        return userRepository.findByUserIdAndUserPassword(userId, password);
    }

    @Override
    public User findByUserKey(String userKey) {
        return userRepository.findByUserKey(UUID.fromString(userKey))
                .orElseThrow(() -> new IllegalArgumentException("해당 userKey 찾을 수 없음"));
    }



}
