package com.mnhmilu.poc.localstack.client;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.*;
import com.amazonaws.util.StringUtils;
import com.mnhmilu.poc.localstack.util.ProfileConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SSMClient {

    private final AWSSimpleSystemsManagement awsSimpleSystemsManagement;

    private final String appName;

    private final String profile;

    public SSMClient(String appName, String profile, String ssmEndPointUrl, String ssmEndPointRegion) {
        this.appName = appName;
        this.profile = profile;

        if (ProfileConstants.PROFILE_DEV.equals(profile)) {

            awsSimpleSystemsManagement = AWSSimpleSystemsManagementClientBuilder
                    .standard()
                    .withEndpointConfiguration(new EndpointConfiguration(ssmEndPointUrl, ssmEndPointRegion))
                    .build();
        } else {
            awsSimpleSystemsManagement = AWSSimpleSystemsManagementClientBuilder.standard().build();
        }
    }

    public String getSSMParameterValue(String paramName) {

        GetParameterRequest parameterRequest = new GetParameterRequest();
        parameterRequest.withName(profile + "/" + appName + "/" + paramName).setWithDecryption(Boolean.TRUE);
        GetParameterResult parameterResult = awsSimpleSystemsManagement.getParameter(parameterRequest);
        return parameterResult.getParameter().getValue();
    }

    public Map<String, String> getSsmParams() {

        String path = "/" + profile + "/" + appName + "/";
        GetParametersByPathRequest request = new GetParametersByPathRequest();
        request.withRecursive(true);
        request.withPath(path);
        request.withWithDecryption(true);
        Map<String, String> parameters = new HashMap<>();
        GetParametersByPathResult response;

        do {

            response = awsSimpleSystemsManagement.getParametersByPath(request);

            for (Parameter p : response.getParameters()) {
                String name = p.getName();
                name = name.substring(path.length());
                parameters.put(name, p.getValue());
            }

            request.setNextToken(response.getNextToken());
        } while (!StringUtils.isNullOrEmpty(response.getNextToken()));

        return parameters;

    }
}
