package com.fintech.pob.domain.pendinghistory.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pending_history")
@Data
@NoArgsConstructor
public class Pending_history {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pendingHistoryId")
    private Integer pendingHistoryId;

    // 계좌 번호 참조
    @Column(name = "accountNo", nullable = false)
    private String accountNo;

    // 대기 중인 금액
    @Column(name = "transactionBalance", nullable = false)
    private Long transactionBalance;

    // 대기 중인 송금 대상 계좌 번호
    @Column(name = "transactionAccountNo", nullable = false)
    private String transactionAccountNo;

    // 송금 메모
    @Column(name = "transactionMemo")
    private String transactionMemo;

    // 수락여부
    @Column(name = "isAccepted")
    private boolean isAccepted;


}
