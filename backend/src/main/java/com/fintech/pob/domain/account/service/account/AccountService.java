package com.fintech.pob.domain.account.service.account;

import com.fintech.pob.domain.account.dto.client.*;
import com.fintech.pob.domain.account.dto.request.*;
import com.fintech.pob.domain.account.dto.transfer.TransferCheckDTO;
import com.fintech.pob.domain.account.service.transfer.TransferCheckResult;
import com.fintech.pob.domain.account.service.transfer.TransferCheckService;
import com.fintech.pob.domain.account.service.transfer.TransferEnumMapper;
import com.fintech.pob.domain.notification.service.notification.NotificationService;
import com.fintech.pob.domain.pendinghistory.service.PendingHistoryService;
import com.fintech.pob.domain.subscription.entity.Subscription;
import com.fintech.pob.domain.subscription.service.SubscriptionService;
import com.fintech.pob.global.header.dto.HeaderRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final WebClient webClient;
    private final HttpServletRequest request;
    private final TransferCheckService transferCheckService;
    private final SubscriptionService subscriptionService;
    private final PendingHistoryService pendingHistoryService;
    private final NotificationService notificationService;

    @EventListener
    public void handleAccountTransferEvent(AccountTransferEvent event) {
        AccountTransferRequestDTO requestPayload = event.getRequestPayload();
        updateAccountTransfer(requestPayload)
                .doOnSuccess(response -> log.info("[Account Transfer Event] 계좌 이체 성공: " + response))
                .doOnError(error -> log.error("[Account Transfer Event] 계좌 이체 중 오류 발생", error))
                .subscribe();
    }

    public Mono<ClientAccountCreationResponseDTO> createAccount(AccountCreationRequestDTO requestPayload) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountCreationRequestDTO requestDTO = new ClientAccountCreationRequestDTO();
        requestDTO.setHeader(header);
        requestDTO.setAccountTypeUniqueNo(requestPayload.getAccountTypeUniqueNo());

        return webClient.post()
                .uri("demandDeposit/createDemandDepositAccount")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountCreationResponseDTO.class);
    }

    public Mono<ClientAccountListResponseDTO> getAccountList() {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountListRequestDTO requestDTO = new ClientAccountListRequestDTO();
        requestDTO.setHeader(header);

        return webClient.post()
                .uri("demandDeposit/inquireDemandDepositAccountList")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountListResponseDTO.class);
    }

    public Mono<ClientAccountListResponseDTO> getAccountList(HeaderRequestDTO header) {
        ClientAccountListRequestDTO requestDTO = new ClientAccountListRequestDTO();
        requestDTO.setHeader(header);

        return webClient.post()
                .uri("demandDeposit/inquireDemandDepositAccountList")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountListResponseDTO.class);
    }

    public Mono<ClientAccountHistoryListResponseDTO> getAccountHistoryList(AccountHistoryListRequestDTO requestPayload) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");
        System.out.println(header.toString());
        header.setApiName("inquireTransactionHistoryList");

        ClientAccountHistoryListRequestDTO requestDTO = ClientAccountHistoryListRequestDTO.of(header, requestPayload);

        return webClient.post()
                .uri("demandDeposit/inquireTransactionHistoryList")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountHistoryListResponseDTO.class);
    }

    public Mono<ClientAccountDetailResponseDTO> getAccountDetail(AccountDetailRequestDTO requestPayload) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountDetailRequestDTO requestDTO = new ClientAccountDetailRequestDTO();
        requestDTO.setHeader(header);
        requestDTO.setAccountNo(requestPayload.getAccountNo());

        System.out.println(header);

        return webClient.post()
                .uri("demandDeposit/inquireDemandDepositAccount")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountDetailResponseDTO.class);
    }

    public Mono<ClientAccountTransferResponseDTO> updateAccountTransfer(AccountTransferRequestDTO requestPayload) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        Optional<Subscription> subscriptionOptional = subscriptionService.findByTargetUserKey(UUID.fromString(header.getUserKey()));
        if (subscriptionOptional.isPresent()) {
            header.setApiName("inquireDemandDepositAccount");

            Mono<ClientAccountDetailResponseDTO> accountDepositMono = getAccountDetail(new AccountDetailRequestDTO(requestPayload.getDepositAccountNo()));
            Mono<ClientAccountDetailResponseDTO> accountWithdrawMono = getAccountDetail(new AccountDetailRequestDTO(requestPayload.getWithdrawalAccountNo()));

            return Mono.zip(accountDepositMono, accountWithdrawMono)
                    .flatMap(tuple -> {
                        ClientAccountDetailResponseDTO accountDeposit = tuple.getT1();
                        ClientAccountDetailResponseDTO accountWithdraw = tuple.getT2();

                        TransferCheckDTO transferCheckDTO = TransferCheckDTO.of(header, requestPayload, accountDeposit, accountWithdraw);

                        // 수정된 부분: checkTransfer 메서드를 리액티브하게 호출
                        return transferCheckService.checkTransfer(transferCheckDTO)
                                .flatMap(checkResult -> {
                                    System.out.println("--------------------------------------");
                                    System.out.println(checkResult);
                                    System.out.println("--------------------------------------");

                                    if (checkResult != TransferCheckResult.SUCCESS) {
                                        // 알림 전송
                                        String typeName = TransferEnumMapper.getNotificationMessage(checkResult);
                                        long notiId = notificationService.sendNotification(UUID.fromString(header.getUserKey()), subscriptionOptional.get().getProtectUser().getUserKey(), typeName);

                                        if (checkResult == TransferCheckResult.LIMIT || checkResult == TransferCheckResult.INACTIVITY) {
                                            // pendingHistory 추가
                                            pendingHistoryService.savePendingHistory(notiId, requestPayload);
                                        }
                                        return Mono.error(new RuntimeException("Transfer failed: " + checkResult));
                                    }

                                    header.setApiName("updateDemandDepositAccountTransfer");

                                    ClientAccountTransferRequestDTO requestDTO = ClientAccountTransferRequestDTO.of(header, requestPayload);

                                    return webClient.post()
                                            .uri("demandDeposit/updateDemandDepositAccountTransfer")
                                            .accept(MediaType.APPLICATION_JSON)
                                            .bodyValue(requestDTO)
                                            .retrieve()
                                            .bodyToMono(ClientAccountTransferResponseDTO.class);
                                });
                    });
        }

        ClientAccountTransferRequestDTO requestDTO = ClientAccountTransferRequestDTO.of(header, requestPayload);

        return webClient.post()
                .uri("demandDeposit/updateDemandDepositAccountTransfer")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountTransferResponseDTO.class);
    }

    public Mono<ClientAccountHistoryDetailResponseDTO> getAccountHistoryDetail(AccountHistoryDetailRequestDTO requestPayload) {
        HeaderRequestDTO header = (HeaderRequestDTO) request.getAttribute("header");

        ClientAccountHistoryDetailRequestDTO requestDTO = ClientAccountHistoryDetailRequestDTO.of(header, requestPayload);

        return webClient.post()
                .uri("demandDeposit/inquireTransactionHistory")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(ClientAccountHistoryDetailResponseDTO.class);
    }
}

