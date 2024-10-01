package com.fintech.pob.domain.account.service.transfer;

import com.fintech.pob.domain.account.dto.client.ClientAccountDetailResponseDTO;
import com.fintech.pob.domain.account.dto.request.AccountDetailRequestDTO;
import com.fintech.pob.domain.account.dto.request.AccountTransferRequestDTO;
import com.fintech.pob.domain.account.service.AccountService;
import com.fintech.pob.global.header.dto.HeaderRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TransferBalanceChecker implements TransferChecker {

    private final AccountService accountService;

    @Override
    public TransferCheckResult check(AccountTransferRequestDTO requestPayload, HeaderRequestDTO header) {
        Mono<ClientAccountDetailResponseDTO> accountDetailMono = accountService.getAccountDetail(
                new AccountDetailRequestDTO(requestPayload.getWithdrawalAccountNo())
        );

        return accountDetailMono
                .map(accountDetails -> {
                    Long currentBalance = accountDetails.getRec().getAccountBalance();
                    Long transferAmount = requestPayload.getTransactionBalance();

                    if (transferAmount > currentBalance) {
                        return TransferCheckResult.BALANCE;
                    } else {
                        return TransferCheckResult.SUCCESS;
                    }
                })
                .block();
    }
}
