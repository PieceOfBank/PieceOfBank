package com.fintech.pob.domain.user.application;

import com.fintech.pob.domain.user.dao.UserRepository;
import com.fintech.pob.domain.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class LocalUserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(String userKey, String userName, String userPassword, int userSubscriptionType) {
        User user = new User();

        user.setUserKey(userKey);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setSubscriptionType(userSubscriptionType);

        userRepository.save(user);
    }
}
