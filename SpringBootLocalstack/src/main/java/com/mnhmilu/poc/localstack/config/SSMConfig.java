package com.mnhmilu.poc.localstack.config;

//import com.bkash.savingsdps.core.common.ssm.SSMClient;
//import com.bkash.savingsdps.core.common.ssm.SSMParameterService;

import com.mnhmilu.poc.localstack.client.SSMClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SSMConfig {

	@Value("${ssm.endpointUrl}")
	private String ssmEndPointUrl;

	@Value("${ssm.endpointRegion}")
	private String ssmEndPointRegion;

    @Value("${resource.prefix}")
    private String appProfile;

	@Value("${spring.application.name}")
	private String appName;

	@Bean
	public SSMClient sSMClient() {
		return new SSMClient(appName, appProfile, ssmEndPointUrl, ssmEndPointRegion);
	}

}
