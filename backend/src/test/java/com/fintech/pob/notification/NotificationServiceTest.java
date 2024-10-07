package com.fintech.pob.notification;

import com.fintech.pob.domain.notification.entity.notification.Notification;
import com.fintech.pob.domain.notification.entity.notification.NotificationType;
import com.fintech.pob.domain.notification.repository.notification.NotificationRepository;
import com.fintech.pob.domain.notification.repository.notification.NotificationTypeRepository;
import com.fintech.pob.domain.notification.service.notification.NotificationServiceImpl;
import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class NotificationServiceTest {

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    private User sender;
    private User receiver;

    @BeforeEach
    public void setUp() {
        // 테스트 유저 데이터 삽입: UserRepository와 같음
        UUID senderKey = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID receiverKey = UUID.fromString("987e6543-e21b-12d3-a456-426614174000");

        sender = new User();
        sender.setUserId("sender");
        sender.setUserKey(senderKey);
        sender.setUserName("Sender User");
        sender.setUserPassword("password123");
        sender.setCreated(LocalDateTime.now());
        sender.setUpdated(LocalDateTime.now());
        sender.setSubscriptionType(1);

        receiver = new User();
        receiver.setUserId("receiver");
        receiver.setUserKey(receiverKey);
        receiver.setUserName("Receiver User");
        receiver.setUserPassword("password456");
        receiver.setCreated(LocalDateTime.now());
        receiver.setUpdated(LocalDateTime.now());
        receiver.setSubscriptionType(2);

        userRepository.save(sender);
        userRepository.save(receiver);
    }

    @Test
    public void testSendNotification() {
        NotificationType notificationType = notificationTypeRepository.findByTypeName("한도 초과 알림")
                .orElseThrow(() -> new IllegalArgumentException("NotificationType not found"));

        notificationService.sendNotification(sender.getUserKey(), receiver.getUserKey(), "한도 초과 알림");

        Optional<Notification> savedNotification = notificationRepository.findById(1L);
        assertNotNull(savedNotification);
        assertNotNull(savedNotification.get().getNotificationId());
        assertNotNull(savedNotification.get().getSenderUser());
        assertNotNull(savedNotification.get().getReceiverUser());
        assertNotNull(savedNotification.get().getType());
        assertNotNull(savedNotification.get().getNotificationStatus());
    }
}


