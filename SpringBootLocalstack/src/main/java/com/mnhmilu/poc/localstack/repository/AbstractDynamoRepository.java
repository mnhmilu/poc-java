package com.mnhmilu.poc.localstack.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.mnhmilu.poc.localstack.client.DynamoDbClient;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDynamoRepository<T> {

	private DynamoDbClient dynamoDbClient;
	private DynamoDBRepository repository;

	private Class<T> domainType;
	private String tablePrefix;

	public AbstractDynamoRepository(Class<T> domainType, String profile, String appName, String endpointUrl,
			String endpointRegion) {
		
		dynamoDbClient = new DynamoDbClient(endpointRegion, profile, endpointUrl);
		repository = new DynamoDBRepository();
		this.domainType = domainType;
		this.tablePrefix = new StringBuilder(profile).append("-").append(appName).toString();
	}

	protected abstract String getTableName();
	
	protected Optional<T> getData(Object hashKey) {
		var mapper = dynamoDbClient.getDynamoDBMapper(tablePrefix + getTableName());

		return repository.getData(hashKey, mapper, domainType);
	}

	protected Optional<T> getData(Object hashKey, Object rangeKey) {
		var mapper = dynamoDbClient.getDynamoDBMapper(tablePrefix + getTableName());

		return repository.getData(hashKey, rangeKey, mapper, domainType);
	}

	protected List<T> getData(DynamoDBQueryExpression<T> queryExpression) {
		var mapper = dynamoDbClient.getDynamoDBMapper(tablePrefix + getTableName());

		return repository.getData(queryExpression, mapper, domainType);
	}

	protected T saveData(T model) throws RuntimeException {
		var mapper = dynamoDbClient.getDynamoDBMapper(tablePrefix + getTableName());

		return repository.saveData(model, mapper);
	}

	protected final boolean saveMultipleData(List<T> models) {
		var mapper = dynamoDbClient.getDynamoDBMapper(tablePrefix + getTableName());
		List<DynamoDBMapper.FailedBatch> failedBatches = repository.saveMultipleData(models, mapper);
		return failedBatches.isEmpty();
	}

	protected boolean deleteData(T model) {
		var mapper = dynamoDbClient.getDynamoDBMapper(tablePrefix + getTableName());

		return repository.deleteData(model, mapper);
	}

	protected List<T> scanData(DynamoDBScanExpression scanExpression) {
		var mapper = dynamoDbClient.getDynamoDBMapper(tablePrefix + getTableName());

		return repository.scanData(domainType, mapper, scanExpression);
	}

	protected List<T> queryPage(DynamoDBQueryExpression<T> queryExpression) {
		var mapper = dynamoDbClient.getDynamoDBMapper(tablePrefix + getTableName());

		return repository.queryPage(queryExpression, mapper, domainType);
	}

	protected List<T> queryData(DynamoDBQueryExpression<T> queryExpression) {
		var mapper = dynamoDbClient.getDynamoDBMapper(tablePrefix + getTableName());
		return repository.queryData(queryExpression, mapper, domainType);
	}

	public ScanResultPage<T> scanPage(DynamoDBScanExpression expression) throws RuntimeException {
		try {
			var mapper = dynamoDbClient.getDynamoDBMapper(tablePrefix + getTableName());
			return repository.scanPage(domainType, mapper, expression);
		} catch (Exception ex) {
			throw new RuntimeException("Couldn't read from DynamoDB");
		}
	}
	
}
