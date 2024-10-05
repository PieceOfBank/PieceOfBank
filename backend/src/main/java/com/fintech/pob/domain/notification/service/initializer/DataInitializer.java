package com.fintech.pob.domain.notification.service.initializer;

import com.fintech.pob.domain.notification.entity.notification.NotificationType;
import com.fintech.pob.domain.notification.repository.notification.NotificationTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initNotificationTypes(NotificationTypeRepository notificationTypeRepository) {
        return args -> {
            if (notificationTypeRepository.count() == 0) {  // 데이터가 없을 때만 추가
                notificationTypeRepository.save(new NotificationType(null, "거래 수락 요청 알림"));
                notificationTypeRepository.save(new NotificationType(null, "한도 변경 알림"));
                notificationTypeRepository.save(new NotificationType(null, "거래 기한 알림"));
                notificationTypeRepository.save(new NotificationType(null, "구독 신청 알림"));
                System.out.println("Notification types initialized.");
            }
        };
    }
}

