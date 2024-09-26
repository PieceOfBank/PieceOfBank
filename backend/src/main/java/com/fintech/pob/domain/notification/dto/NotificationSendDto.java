package com.fintech.pob.domain.notification.dto;

import lombok.*;

/**
 * 모바일에서 전달받은 객체
 *
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationSendDto {
    private String token;

    private String title;

    private String body;

    @Builder(toBuilder = true)
    public NotificationSendDto(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }
}