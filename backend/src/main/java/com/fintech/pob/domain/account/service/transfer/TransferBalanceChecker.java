package com.fintech.pob.domain.account.service.transfer;

import com.fintech.pob.domain.account.dto.transfer.TransferCheckDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferBalanceChecker implements TransferChecker {

    @Override
    public TransferCheckResult check(TransferCheckDTO transferCheckDTO) {
        Long currentBalance = transferCheckDTO.getAccountWithdraw().getRec().getAccountBalance();
        Long transferAmount = transferCheckDTO.getRequestPayload().getTransactionBalance();

        if (transferAmount > currentBalance) {
            return TransferCheckResult.BALANCE;
        } else {
            return TransferCheckResult.SUCCESS;
        }
    }
}
