package com.jpaudelacruz.recommendation_system_ms.application;

import com.jpaudelacruz.recommendation_system_ms.models.avro.UserAction;
import com.jpaudelacruz.recommendation_system_ms.producers.UserActionKafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserActionService {

    @Value("${user.action.topic.name}")
    private String userActionTopic;

    private final UserActionKafkaProducer userActionKafkaProducer;

    public UserActionService(UserActionKafkaProducer userActionKafkaProducer){
        this.userActionKafkaProducer = userActionKafkaProducer;
    }

    public void sendUserActionToKafka(UserAction userAction){
        userActionKafkaProducer.send(userActionTopic, userAction);
    }

}
