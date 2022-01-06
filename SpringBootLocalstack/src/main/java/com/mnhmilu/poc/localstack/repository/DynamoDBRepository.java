package com.mnhmilu.poc.localstack.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DynamoDBRepository {

    public <T> Optional<T> getData(Object key, DynamoDBMapper mapper, Class<T> tClass) {
        try {

            return Optional.ofNullable(mapper.load(tClass, key));
        } catch (Exception ex) {
            log.error("Data retrieval failed from DynamoDB using key: {}", key, ex);
        }

        return Optional.empty();
    }

    public <T> Optional<T> getData(Object hashKey, Object rangeKey, DynamoDBMapper mapper, Class<T> tClass) {
        try {
            return Optional.ofNullable(mapper.load(tClass, hashKey, rangeKey));
        } catch (Exception ex) {
            log.error("Data retrieval failed from DynamoDB using hashKey : {} , rangeKey : {}", hashKey, rangeKey, ex);
        }

        return Optional.empty();
    }

    public <T> List<T> getData(DynamoDBQueryExpression<T> queryExpression, DynamoDBMapper mapper, Class<T> tClass) {
        try {
            return mapper.query(tClass, queryExpression);
        } catch (Exception ex) {
            log.error("Data retrieval failed from DynamoDB using queryExpression: {}", queryExpression.toString(), ex);
        }

        return new ArrayList<>();
    }

    public List<Object> batchLoadDataByHashKey(String tableName, List<Object> itemsToGet, DynamoDBMapper mapper) {
        try {

            return mapper.batchLoad(itemsToGet).get(tableName);
        } catch (Exception ex) {
            log.error("Data retrieval failed from DynamoDB using queryExpression: {}", itemsToGet.toString(), ex);
        }

        return new ArrayList<>();
    }

    public <T> List<T> queryPage(DynamoDBQueryExpression<T> queryExpression, DynamoDBMapper mapper, Class<T> tClass) {
        try {
            return mapper.queryPage(tClass, queryExpression).getResults();
        } catch (Exception ex) {
            log.error("Data retrieval failed from DynamoDB using queryExpression: {}", queryExpression.toString(), ex);
        }

        return new ArrayList<>();
    }

    protected  <T> List<T> queryData(DynamoDBQueryExpression<T> queryExpression, DynamoDBMapper mapper, Class<T> tClass) {
        try {
            return mapper.query(tClass, queryExpression);
        } catch (Exception ex) {
            log.error("Data retrieval failed from DynamoDB using queryExpression: {}", queryExpression.toString(), ex);
        }

        return Collections.emptyList();
    }

    public <T> T saveData(T model, DynamoDBMapper mapper) throws RuntimeException {
        try {
            mapper.save(model);
            return model;

        } catch (Exception ex) {
            log.error("Couldn't save {} to DynamoDB", model, ex);
            throw new RuntimeException("Couldn't save {} to DynamoDB", ex);
        }
    }

    public <T> List<DynamoDBMapper.FailedBatch> saveMultipleData(List<T> models, DynamoDBMapper mapper) {
        try {
            return mapper.batchSave(models);
        } catch (Exception ex) {
            log.error("Couldn't save {} to DynamoDB", models, ex);
            throw new RuntimeException("Couldn't save {} to DynamoDB", ex);
        }
    }

    public <T> boolean deleteData(T model, DynamoDBMapper mapper) {
        try {
            mapper.delete(model);

            return true;
        } catch (Exception ex) {
            log.error("Couldn't delete {} to DynamoDB", model, ex);
            throw new RuntimeException("Couldn't delete {} to DynamoDB", ex);
        }
    }

    public <T> List<T> scanData(Class<T> clazz, DynamoDBMapper mapper, DynamoDBScanExpression expression) throws RuntimeException {
    	try {
    		return mapper.scan(clazz, expression);
    		
    	} catch (Exception ex) {
            log.error("Couldn't read data from DynamoDB", ex);
            throw new RuntimeException("Couldn't read from DynamoDB");
    	}
    }

    public <T> ScanResultPage<T> scanPage(Class<T> clazz, DynamoDBMapper mapper, DynamoDBScanExpression expression) throws RuntimeException {
        try {
            return mapper.scanPage(clazz, expression);
        } catch (Exception ex) {
            log.error("Couldn't read data from DynamoDB", ex);
            throw new RuntimeException("Couldn't read from DynamoDB");
        }
    }

}
