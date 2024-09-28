package com.fintech.pob.domain.notification.repository;

import com.fintech.pob.domain.notification.entity.TransactionApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionApprovalRepository extends JpaRepository<TransactionApproval, Long> {
}
