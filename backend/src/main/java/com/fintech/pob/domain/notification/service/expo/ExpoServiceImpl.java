package com.fintech.pob.domain.notification.service.expo;

import com.fintech.pob.domain.notification.dto.expo.ExpoNotificationMessageDto;
import com.fintech.pob.domain.notification.dto.expo.ExpoNotificationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class ExpoServiceImpl implements ExpoService {

    @Value("${expo.api.url}")
    private String EXPO_API_URL;

    @Override
    public void sendPushNotification(ExpoNotificationRequestDto expoNotificationRequestDto) {
        // 메시지 객체 생성
        ExpoNotificationMessageDto message = new ExpoNotificationMessageDto(expoNotificationRequestDto.getToken(), expoNotificationRequestDto.getTitle(), expoNotificationRequestDto.getContent());

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 본문 설정
        HttpEntity<ExpoNotificationMessageDto> entity = new HttpEntity<>(message, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(EXPO_API_URL, entity, String.class);
    }
}
