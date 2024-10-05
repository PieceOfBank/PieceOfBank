package com.fintech.pob.domain.notification.repository.transaction;

import com.fintech.pob.domain.notification.entity.transaction.TransactionApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionApprovalRepository extends JpaRepository<TransactionApproval, Long> {
}
