package com.fintech.pob.domain.pendinghistory.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingHistoryDTO {
    private String depositAccountNo;
    private Long transactionBalance;
    private String withdrawalAccountNo;
    private String depositTransactionSummary;
    private String withdrawalTransactionSummary;
}
