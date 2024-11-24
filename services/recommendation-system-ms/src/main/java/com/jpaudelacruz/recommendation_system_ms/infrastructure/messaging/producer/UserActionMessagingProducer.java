package com.jpaudelacruz.recommendation_system_ms.infrastructure.messaging.producer;

import com.jpaudelacruz.recommendation_system_ms.infrastructure.messaging.producer.model.UserActionMessagingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserActionMessagingProducer {

    private static final Logger logger = LoggerFactory.getLogger(UserActionMessagingProducer.class);

    private final KafkaTemplate<String, UserActionMessagingEvent> kafkaTemplate;

    public UserActionMessagingProducer(KafkaTemplate<String, UserActionMessagingEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, UserActionMessagingEvent userAction) {
        CompletableFuture<SendResult<String, UserActionMessagingEvent>> call =
                kafkaTemplate.send(topicName, userAction.getUserId(), userAction);
        call.thenAccept(sendResult -> {
            logger.info("Message sent successfully: {}", sendResult.getRecordMetadata().toString());
        }).exceptionally(ex -> {
            logger.info("There was a problem sending message: {}", ex.toString());
            throw new RuntimeException("", ex);
        });
        call.join(); // synchronous
    }

}
