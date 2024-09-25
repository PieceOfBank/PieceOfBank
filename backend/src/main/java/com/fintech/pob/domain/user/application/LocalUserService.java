package com.fintech.pob.domain.user.application;

import com.fintech.pob.domain.user.dao.UserRepository;
import com.fintech.pob.domain.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Transactional
public class LocalUserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UUID userKey, String userName, String userPassword, int userSubscriptionType) {
        User user = new User();
        if (userKey == null ) {
            throw new IllegalArgumentException("userKey는 null이거나 비어 있을 수 없습니다.");
        }
        user.setUserKey(userKey);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setSubscriptionType(userSubscriptionType);

        System.out.println(user.getUserKey());

        userRepository.save(user);
    }

    public User authenticate(String userName, String password) {
        return userRepository.findByUserNameAndUserPassword(userName, password);
    }
}
