package com.mnhmilu.poc.localstack.service;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageReceiverService {

    @SqsListener(value="dev-poc-queue",deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(String message) {
        log.info("Message Received **************************** "+message );
        //log.info("After Conversion"+new JSONObject(message).getString("payload"));
        throw new RuntimeException("An exception was thrown during the execution of the SQS listener method and Message will be still available in Queue");
    }

}
