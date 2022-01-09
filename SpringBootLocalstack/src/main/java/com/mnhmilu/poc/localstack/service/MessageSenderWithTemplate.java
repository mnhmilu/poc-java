package com.mnhmilu.poc.localstack.service;


import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author nahid
 */
@Slf4j
@Service
public class MessageSenderWithTemplate {

    private final QueueMessagingTemplate messagingTemplate;

    public MessageSenderWithTemplate(QueueMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void send(final String messagePayload, final String queueName) {

        Message<String> message = MessageBuilder.withPayload(messagePayload)
                .setHeader("sender", "poc-localstack")
                .setHeader("X-B3-TraceId", MDC.get("X-B3-TraceId"))
                .setHeaderIfAbsent("country", "BD")
                .build();
        messagingTemplate.convertAndSend(queueName, message);
        log.info("Message sent");
    }

    public void sendToFifoQueue(final String messagePayload, final String messageGroupID,
                                final String messageDedupID, final String queueName) {

        Message<String> message = MessageBuilder.withPayload(messagePayload)
                .setHeader("message-group-id", messageGroupID)
                .setHeader("message-deduplication-id", messageDedupID)
                .build();
        messagingTemplate.convertAndSend(queueName, message);
        log.info("Message sent");
    }
}