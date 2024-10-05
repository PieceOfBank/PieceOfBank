package com.fintech.pob.notification;

import com.fintech.pob.domain.notification.entity.Notification;
import com.fintech.pob.domain.notification.entity.NotificationStatus;
import com.fintech.pob.domain.notification.entity.NotificationType;
import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.notification.repository.NotificationRepository;
import com.fintech.pob.domain.notification.repository.NotificationTypeRepository;
import com.fintech.pob.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=none"
})
@Transactional
@Rollback(false) // 롤백 방지
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private UserRepository userRepository;

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
    public void testSaveNotification() {
        NotificationType notificationType = notificationTypeRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("NotificationType not found"));

        Notification notification = Notification.builder()
                .senderUser(sender)
                .receiverUser(receiver)
                .type(notificationType)
                .notificationStatus(NotificationStatus.UNREAD)
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        assertNotNull(savedNotification.getNotificationId());
    }
}
