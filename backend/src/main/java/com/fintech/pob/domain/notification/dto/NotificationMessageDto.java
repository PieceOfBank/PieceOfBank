package com.fintech.pob.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * FCM 전송 Format DTO
 *
 */
@Getter
@Builder
public class NotificationMessageDto {
    private boolean validateOnly;
    private NotificationMessageDto.Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private NotificationMessageDto.Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}