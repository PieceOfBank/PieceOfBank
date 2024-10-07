package com.fintech.pob.domain.account.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InactivityCheckScheduler {

//    private final InactivityCheckService inactivityCheckService;

    // 매일 오전 9시에 비활성 계좌를 체크
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkInactiveAccounts() {
//        inactivityCheckService.checkAndSendInactivityNotifications();
    }
}

