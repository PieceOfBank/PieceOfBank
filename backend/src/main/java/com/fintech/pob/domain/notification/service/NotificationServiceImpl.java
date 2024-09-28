package com.fintech.pob.domain.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.pob.domain.notification.dto.NotificationMessageDto;
import com.fintech.pob.domain.notification.dto.NotificationRequestDto;
import com.fintech.pob.domain.notification.dto.TransactionApprovalRequestDto;
import com.fintech.pob.domain.notification.entity.NotificationStatus;
import com.fintech.pob.domain.notification.entity.NotificationType;
import com.fintech.pob.domain.notification.entity.TransactionApproval;
import com.fintech.pob.domain.notification.repository.NotificationRepository;
import com.fintech.pob.domain.notification.entity.Notification;
import com.fintech.pob.domain.notification.repository.NotificationTypeRepository;
import com.fintech.pob.domain.notification.repository.TransactionApprovalRepository;
import com.fintech.pob.domain.user.entity.User;
import com.fintech.pob.domain.user.repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Value("${fcm.key.path}")
    private String SERVICE_ACCOUNT_JSON;
    @Value("${fcm.api.url}")
    private String FCM_API_URL;

    private final WebClient webClient;

    private final NotificationRepository notificationRepository;
    private final TransactionApprovalRepository transactionApprovalRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final UserRepository userRepository;


    @Transactional
    public void requestExceedTransfer(TransactionApprovalRequestDto transactionApprovalRequestDto) {
        NotificationType notificationType = notificationTypeRepository.findByTypeName("거래 수락 요청 알림")
                .orElseThrow(() -> new IllegalArgumentException("Notification Type not found"));
        User sender = userRepository.findByUserKey(transactionApprovalRequestDto.getSenderKey())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        // receiver 값 못 받으면 Subscription에서 조회하여 사용
        User receiver = userRepository.findByUserKey(transactionApprovalRequestDto.getReceiverKey())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        // Notification 생성
        Notification notification = Notification.builder()
                .senderUser(sender)
                .receiverUser(receiver)
                .type(notificationType)
                .created(LocalDateTime.now())
                .notificationStatus(NotificationStatus.UNREAD)
                .build();
        notificationRepository.save(notification);

        // TransactionApproval 생성
        TransactionApproval transactionApproval = TransactionApproval.builder()
                .notification(notification)
                .receiverName(transactionApprovalRequestDto.getReceiverName())
                .amount(1000000L)
                .build();
        transactionApprovalRepository.save(transactionApproval);

        // 푸시 알림 전송 처리 예정
    }

    @Override
    public void acceptTransferRequest(TransactionApprovalRequestDto transactionApprovalRequestDto) {

    }

    /**
     * 푸시 메시지 처리를 수행하는 비즈니스 로직
     *
     * @param notificationRequestDto 모바일에서 전달받은 Object
     * @return 성공(1), 실패(0)
     */
    public Mono<Integer> sendMessageTo(NotificationRequestDto notificationRequestDto) throws IOException {
        String message = makeMessage(notificationRequestDto);
        System.out.println("+++++++++" + message);
        String accessToken = getAccessToken();

        return webClient.post()
                .uri(FCM_API_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .bodyValue(message)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful() ? 1 : 0)
                .doOnError(e -> {

                    log.error("[-] FCM 전송 오류 :: " + e.getMessage());
                    log.error("[" + notificationRequestDto.getToken() + "]이 유효하지 않습니다");
                })
                .onErrorReturn(0);
    }

    /**
     * Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰을 발급 받습니다.
     *
     * @return Bearer token
     */
    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(SERVICE_ACCOUNT_JSON).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        log.info("getAccessToken() - googleCredentials: {} ", googleCredentials.getAccessToken().getTokenValue());
        return googleCredentials.getAccessToken().getTokenValue();
    }

    /**
     * FCM 전송 정보를 기반으로 메시지를 구성합니다. (Object -> String)
     *
     * @param notificationRequestDto notificationRequestDto
     * @return String
     */
    private String makeMessage(NotificationRequestDto notificationRequestDto) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        NotificationMessageDto fcmMessageDto = NotificationMessageDto
                .builder()
                .message(NotificationMessageDto.Message.builder()
                        .token(notificationRequestDto.getToken()) // 1:1 전송 시 대상 토큰
                        .notification(NotificationMessageDto.Notification.builder()
                                .title(notificationRequestDto.getTitle())
                                .body(notificationRequestDto.getBody())
                                .image(null)
                                .build()
                        ).build())
                .validateOnly(false)
                .build();

        return om.writeValueAsString(fcmMessageDto);
    }
}

