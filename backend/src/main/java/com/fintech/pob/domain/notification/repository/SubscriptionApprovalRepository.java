package com.fintech.pob.domain.notification.repository;

import com.fintech.pob.domain.notification.entity.SubscriptionApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionApprovalRepository extends JpaRepository<SubscriptionApproval, Long> {
}

