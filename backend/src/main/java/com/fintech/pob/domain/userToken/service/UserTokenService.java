package com.fintech.pob.domain.userToken.service;

import com.fintech.pob.domain.userToken.entity.UserToken;

import java.util.Optional;
import java.util.UUID;

public interface UserTokenService {
    void saveUserToken(UUID userKey, String token);
    String getUserTokenByUserKey(UUID userKey);
    void deleteUserToken(UUID userKey);
}
