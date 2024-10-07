package com.fintech.pob.domain.account.service.transfer;

import com.fintech.pob.domain.account.dto.client.ClientAccountHistoryListResponseDTO;
import com.fintech.pob.domain.account.dto.request.AccountHistoryListRequestDTO;
import com.fintech.pob.domain.account.dto.request.AccountTransferRequestDTO;
import com.fintech.pob.domain.account.dto.transfer.TransferCheckDTO;
import com.fintech.pob.domain.account.service.account.AccountHistoryListService;
import com.fintech.pob.domain.subscription.entity.Subscription;
import com.fintech.pob.domain.subscription.service.SubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferLimitChecker implements TransferChecker {

    private final SubscriptionService subscriptionService;
    private final AccountHistoryListService accountHistoryListService;
    private final HttpServletRequest request;

    @Override
    public TransferCheckResult check(TransferCheckDTO transferCheckDTO) {
        String userKey = (String) request.getAttribute("userKey");
        Optional<Subscription> subscriptionOptional = subscriptionService.findByTargetUserKey(UUID.fromString(userKey));

        AccountTransferRequestDTO requestPayload = transferCheckDTO.getRequestPayload();

        if (subscriptionOptional.isPresent()) {
            Subscription subscription = subscriptionOptional.get();
            Long oneTimeTransferLimit = subscription.getOneTimeTransferLimit();
            Long dailyTransferLimit = subscription.getDailyTransferLimit();

            // 1회 이체 한도 체크
            if (transferCheckDTO.getRequestPayload().getTransactionBalance() > oneTimeTransferLimit) {
                return TransferCheckResult.LIMIT;
            }

            // 일일 이체 한도 체크
            AccountHistoryListRequestDTO historyRequest = new AccountHistoryListRequestDTO(
                    requestPayload.getWithdrawalAccountNo(),
                    LocalDate.now().toString(),
                    LocalDate.now().toString(),
                    "A",
                    "DESC"
            );

            return accountHistoryListService.getAccountHistoryList(historyRequest)
                    .map(response -> {
                        Long totalAmountToday = response.getRec().getHistory().stream()
                                .mapToLong(ClientAccountHistoryListResponseDTO.Record.HistoryInfo::getTransactionBalance)
                                .sum();

                        if (totalAmountToday + requestPayload.getTransactionBalance() > dailyTransferLimit) {
                            return TransferCheckResult.LIMIT;
                        }
                        return TransferCheckResult.SUCCESS;
                    })
                    .block();
        }

        return TransferCheckResult.SUCCESS;
    }
}