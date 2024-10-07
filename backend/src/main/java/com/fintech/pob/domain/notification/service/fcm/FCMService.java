package com.fintech.pob.domain.notification.service.fcm;

import com.fintech.pob.domain.notification.dto.notification.NotificationRequestDto;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface FCMService {
    Mono<Integer> sendMessageTo(NotificationRequestDto notificationSendDto) throws IOException;
}
