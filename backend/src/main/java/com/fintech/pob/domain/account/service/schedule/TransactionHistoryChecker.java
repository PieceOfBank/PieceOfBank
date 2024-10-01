package com.fintech.pob.domain.account.service.schedule;

import com.fintech.pob.domain.account.dto.client.ClientAccountListRequestDTO;
import com.fintech.pob.domain.account.dto.client.ClientAccountListResponseDTO;
import com.fintech.pob.domain.account.service.AccountService;
import com.fintech.pob.domain.subscription.entity.Subscription;
import com.fintech.pob.global.header.dto.HeaderRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionHistoryChecker implements ScheduledChecker {

    private final AccountService accountService;
    private final SubscriptionService subscriptionService;

    @Override
    public void check() {
        List<Subscription> subscriptions = subscriptionService.findAll();

        for (Subscription subscription : subscriptions) {
            Mono<ClientAccountListResponseDTO> accountListMono = accountService.getAccountList();

            accountListMono.subscribe(accountListResponse -> {
                List<ClientAccountListResponseDTO.Record> accounts = accountListResponse.getRec();

                for (ClientAccountListResponseDTO.Record account : accounts) {
                    String lastTransactionDate = account.getLastTransactionDate();
                    String accountNo = account.getAccountNo();

                    if (lastTransactionDate == null || LocalDate.parse(lastTransactionDate).isBefore(LocalDate.now().minusDays(3))) {
                        System.out.println("[ScheduledChecker] : " + accountNo + ", " + subscription.getTargetUser());
                        // 알림 전송
                        break;
                    }
                }
            });
        }
    }
}
