package com.fintech.pob.domain.notification.dto.expo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExpoNotificationMessageDto {
    private String token;
    private String title;
    private String body;

    public ExpoNotificationMessageDto(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }
}
