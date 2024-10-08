package com.fintech.pob.domain.notification.repository.subscription;

import com.fintech.pob.domain.subscriptionApproval.entity.SubscriptionApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionApprovalRepository extends JpaRepository<SubscriptionApproval, Long> {
}

