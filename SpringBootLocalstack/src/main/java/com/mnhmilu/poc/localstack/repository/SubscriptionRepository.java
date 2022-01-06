package com.mnhmilu.poc.localstack.repository;


import com.mnhmilu.poc.localstack.entity.SubscriptionEntity;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {

    SubscriptionEntity save(SubscriptionEntity subscription);

    List<SubscriptionEntity> findByWalletNo(String walletNo);

    Optional<SubscriptionEntity> findByRppSubscriptionRequestId(String rppSubscriptionRequestId);

    Optional<SubscriptionEntity> findBySubscriptionRequestIdAndWalletNo(String subscriptionRequestId, String walletNo);

    boolean saveAll(List<SubscriptionEntity> entities);
}
