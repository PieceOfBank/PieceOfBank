package com.fintech.pob.domain.transactionApproval.repository;

import com.fintech.pob.domain.transactionApproval.entity.TransactionApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionApprovalRepository extends JpaRepository<TransactionApproval, Long> {
}
