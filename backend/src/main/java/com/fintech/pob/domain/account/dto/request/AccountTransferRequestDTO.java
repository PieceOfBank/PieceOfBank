package com.fintech.pob.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransferRequestDTO {
    private String depositAccountNo;
    private Long transactionBalance;
    private String withdrawalAccountNo;
    private String depositTransactionSummary;
    private String withdrawalTransactionSummary;
}
