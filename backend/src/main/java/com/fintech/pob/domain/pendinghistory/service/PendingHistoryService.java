package com.fintech.pob.domain.pendinghistory.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.pob.domain.account.dto.request.AccountTransferRequestDTO;
import com.fintech.pob.domain.account.service.AccountService;
import com.fintech.pob.domain.pendinghistory.dto.PendingHistoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class PendingHistoryService {

    private final RedisTemplate<String, String> redisTemplate;
    private final AccountService accountService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void savePendingHistory(int notificationId, PendingHistoryDTO pendingHistoryDTO) {
        String redisKey = "PendingHistory:" + notificationId;

        Map<String, String> pendingHistoryData = new HashMap<>();
        pendingHistoryData.put("depositAccountNo", pendingHistoryDTO.getDepositAccountNo());
        pendingHistoryData.put("transactionBalance", String.valueOf(pendingHistoryDTO.getTransactionBalance()));
        pendingHistoryData.put("withdrawalAccountNo", pendingHistoryDTO.getWithdrawalAccountNo());
        pendingHistoryData.put("depositTransactionSummary", pendingHistoryDTO.getDepositTransactionSummary());
        pendingHistoryData.put("withdrawalTransactionSummary", pendingHistoryDTO.getWithdrawalTransactionSummary());

        try {
            String redisValue = objectMapper.writeValueAsString(pendingHistoryData);
            redisTemplate.opsForList().rightPush(redisKey, redisValue);

            redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error("[PendingHistory 저장] 직렬화 오류", e);
        }
    }

    public void acceptPendingHistory(int notificationId) {
        String redisKey = "PendingHistory:" + notificationId;

        try {
            String redisValue = redisTemplate.opsForList().leftPop(redisKey);

            if (redisValue != null) {
                Map<String, String> pendingHistoryData = objectMapper.readValue(redisValue, Map.class);
                callAccountTransfer(pendingHistoryData);

                deletePendingHistory(redisKey);
            } else {
                log.info("[PendingHistory 승인] Notfound notificationId: " + notificationId);
            }
        } catch (Exception e) {
            log.error("[PendingHistory 승인] 데이터 처리 오류: " + notificationId, e);
        }
    }

    public void denyPendingHistory(int notificationId) {
        String redisKey = "PendingHistory:" + notificationId;
        deletePendingHistory(redisKey);
    }

    public void deletePendingHistory(String redisKey) {
        try {
            redisTemplate.delete(redisKey);
            log.info("[PendingHistory 삭제] 데이터 삭제 완료: " + redisKey);
        } catch (Exception e) {
            log.error("[PendingHistory 삭제] Redis 데이터 삭제 오류: " + redisKey, e);
        }
    }

    public void callAccountTransfer(Map<String, String> pendingHistoryData) {
        AccountTransferRequestDTO requestPayload = AccountTransferRequestDTO.builder()
                .depositAccountNo(pendingHistoryData.get("depositAccountNo"))
                .transactionBalance(Long.valueOf(pendingHistoryData.get("transactionBalance")))
                .withdrawalAccountNo(pendingHistoryData.get("withdrawalAccountNo"))
                .depositTransactionSummary(pendingHistoryData.get("depositTransactionSummary"))
                .withdrawalTransactionSummary(pendingHistoryData.get("withdrawalTransactionSummary"))
                .build();

        accountService.updateAccountTransfer(requestPayload)
                .doOnSuccess(response -> log.info("[PendingHistory 승인] 계좌 이체 성공: " + response))
                .doOnError(error -> log.error("[PendingHistory 승인] 계좌 이체 중 오류 발생", error))
                .subscribe();
    }
}
