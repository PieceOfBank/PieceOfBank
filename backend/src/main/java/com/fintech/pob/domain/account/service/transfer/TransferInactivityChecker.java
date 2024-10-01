package com.fintech.pob.domain.account.service.transfer;

import com.fintech.pob.domain.account.dto.client.ClientAccountDetailResponseDTO;
import com.fintech.pob.domain.account.dto.request.AccountDetailRequestDTO;
import com.fintech.pob.domain.account.dto.request.AccountTransferRequestDTO;
import com.fintech.pob.domain.account.service.AccountService;
import com.fintech.pob.global.header.dto.HeaderRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TransferInactivityChecker implements TransferChecker {

    private final AccountService accountService;

    @Override
    public TransferCheckResult check(AccountTransferRequestDTO requestPayload, HeaderRequestDTO header) {
        Mono<ClientAccountDetailResponseDTO> accountDetailMono = accountService.getAccountDetail(
                new AccountDetailRequestDTO(requestPayload.getDepositAccountNo())
        );

        return accountDetailMono
                .map(accountDetails -> {
                    String lastTransactionDate = accountDetails.getRec().getLastTransactionDate();
                    String accountCreatedDate = accountDetails.getRec().getAccountCreatedDate();

                    if (lastTransactionDate == null) {
                        LocalDate createdDate = LocalDate.parse(accountCreatedDate);
                        if (createdDate.isBefore(LocalDate.now().minusYears(1))) {
                            return TransferCheckResult.INACTIVITY;
                        }
                    }

                    LocalDate lastTransaction = LocalDate.parse(lastTransactionDate);
                    if (lastTransaction.isBefore(LocalDate.now().minusYears(1))) {
                        return TransferCheckResult.INACTIVITY;
                    }

                    return TransferCheckResult.SUCCESS;
                })
                .block();
    }
}
