package com.jpaudelacruz.recommendation_system_ms.producers;


import com.jpaudelacruz.recommendation_system_ms.models.avro.UserAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserActionKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(UserActionKafkaProducer.class);

    private final KafkaTemplate<String, UserAction> kafkaTemplate;

    public UserActionKafkaProducer(KafkaTemplate<String, UserAction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, UserAction userAction) {
        CompletableFuture<SendResult<String, UserAction>> call =
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
