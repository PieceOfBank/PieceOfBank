package com.fintech.pob.global.filter;

import com.fintech.pob.global.auth.jwt.JwtUtil;
import com.fintech.pob.global.auth.service.UserAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserAuthenticationService userAuthenticationService; // UserAuthenticationService 사용

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserAuthenticationService userAuthenticationService) {
        this.jwtUtil = jwtUtil;
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        String userKey = null;
        String jwtToken = null;

        if (authorizationHeader != null) {
            jwtToken = authorizationHeader;
            try {
                userKey = jwtUtil.extractUserKey(jwtToken);
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired");
            } catch (Exception e) {
                logger.error("Error extracting JWT Token", e);
            }
        }
        logger.info("JWT Token: " + jwtToken);
        logger.info("Extracted userKey: " + userKey);


        if (userKey != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // UserAuthenticationService를 통해 유저 인증 정보 설정
            UsernamePasswordAuthenticationToken authentication = userAuthenticationService.getAuthentication(userKey, request);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
