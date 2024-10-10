package com.fintech.pob.global.auth.service;

import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserAuthenticationService {

    private final UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String userKeyString, HttpServletRequest request) {
        UUID userKey = UUID.fromString(userKeyString);
        User user = userRepository.findByUserKey(userKey)
                .orElse(null);

        if (user != null) {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getUserId(), user.getUserPassword(), new ArrayList<>());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            return authentication;
        }

        return null;
    }
}
