package com.fintech.pob.domain.account.service.transfer;

import com.fintech.pob.domain.account.dto.transfer.TransferCheckDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class TransferInactivityChecker implements TransferChecker {

    @Override
    public TransferCheckResult check(TransferCheckDTO transferCheckDTO) {
        String lastTransactionDate = transferCheckDTO.getAccountDeposit().getRec().getLastTransactionDate();
        String accountCreatedDate = transferCheckDTO.getAccountDeposit().getRec().getAccountCreatedDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        if (lastTransactionDate == "") {
            LocalDate createdDate = LocalDate.parse(accountCreatedDate, formatter);
            if (createdDate.isBefore(LocalDate.now().minusDays(1))) {
                return TransferCheckResult.INACTIVITY;
            }
        } else {
            LocalDate lastTransaction = LocalDate.parse(lastTransactionDate, formatter);
            if (lastTransaction.isBefore(LocalDate.now().minusDays(1))) {
                return TransferCheckResult.INACTIVITY;
            }
        }

        return TransferCheckResult.SUCCESS;
    }
}
