package com.mnhmilu.poc.localstack.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.mnhmilu.poc.localstack.common.DynamoDBTables;
import com.mnhmilu.poc.localstack.entity.SubscriptionEntity;

import java.util.List;
import java.util.Optional;

import static com.mnhmilu.poc.localstack.common.DynamoDBIndexes.INDEX_EXTERNAL_REQUEST_ID;
import static com.mnhmilu.poc.localstack.common.DynamoDBIndexes.INDEX_SUBSCRIPTION_REQUEST_ID;


public class SubscriptionRepositoryImpl extends AbstractDynamoRepository<SubscriptionEntity> implements SubscriptionRepository {

    public SubscriptionRepositoryImpl(String profile, String appName, String endpointUrl, String endpointRegion) {
        super(SubscriptionEntity.class, profile, appName, endpointUrl, endpointRegion);
    }

    @Override
    protected String getTableName() {
        return DynamoDBTables.TABLE_SUBSCRIPTION;
    }

    @Override
    public SubscriptionEntity save(SubscriptionEntity subscription) {
        return saveData(subscription);
    }

    @Override
    public List<SubscriptionEntity> findByWalletNo(String walletNo) {
        return getData(getQueryExpression(walletNo));
    }

    @Override
    public Optional<SubscriptionEntity> findByRppSubscriptionRequestId(String externalRequestId) {
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setExternalRequestId(externalRequestId);
        DynamoDBQueryExpression<SubscriptionEntity> queryExpression = new DynamoDBQueryExpression<>();
        queryExpression.setIndexName(INDEX_EXTERNAL_REQUEST_ID);
        queryExpression.setHashKeyValues(subscriptionEntity);
        queryExpression.setConsistentRead(false);
        return queryData(queryExpression).stream().findFirst();
    }

    @Override
    public Optional<SubscriptionEntity> findBySubscriptionRequestIdAndWalletNo(String subscriptionRequestId, String walletNo) {
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setSubscriptionRequestId(subscriptionRequestId);

        DynamoDBQueryExpression<SubscriptionEntity> queryExpression = new DynamoDBQueryExpression<>();

        queryExpression.setIndexName(INDEX_SUBSCRIPTION_REQUEST_ID);
        queryExpression.setHashKeyValues(subscriptionEntity);
        queryExpression.setConsistentRead(false);

        return queryData(queryExpression).stream()
                .filter(s -> s.getWalletNo().equals(walletNo))
                .findFirst();
    }

    @Override
    public boolean saveAll(List<SubscriptionEntity> entities) {
        return saveMultipleData(entities);
    }

    private DynamoDBQueryExpression<SubscriptionEntity> getQueryExpression(String walletNo) {

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setWalletNo(walletNo);

        return new DynamoDBQueryExpression<SubscriptionEntity>().withHashKeyValues(subscriptionEntity);
    }
}
