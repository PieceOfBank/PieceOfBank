package com.fintech.pob.global.header.interceptor;

import com.fintech.pob.global.header.dto.HeaderRequestDTO;
import com.fintech.pob.global.header.service.HeaderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class HeaderInterceptor implements HandlerInterceptor {

    private final HeaderService headerService;

    public HeaderInterceptor(HeaderService headerService) {
        this.headerService = headerService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getAttribute("accessInterceptor") != null) {
            return true; // 재실행 방지
        }
        request.setAttribute("accessInterceptor", true);

        String userKey = request.getHeader("userKey");
        String apiName = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);

        HeaderRequestDTO header = headerService.createCommonHeader(apiName, userKey);
        request.setAttribute("header", header);

        return true;
    }
}