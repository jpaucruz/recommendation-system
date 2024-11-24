package com.jpaudelacruz.recommendation_system_ms.application;

import com.jpaudelacruz.recommendation_system_ms.application.dto.UserActionApplicationDTO;
import com.jpaudelacruz.recommendation_system_ms.application.mapper.UserApplicationMapper;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.messaging.producer.UserActionMessagingProducer;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.messaging.producer.model.UserActionMessagingEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateUserActionService {

    @Value("${user.action.topic.name}")
    private String userActionTopic;

    private final UserActionMessagingProducer userActionMessagingProducer;

    public CreateUserActionService(UserActionMessagingProducer userActionMessagingProducer){
        this.userActionMessagingProducer = userActionMessagingProducer;
    }

    public void sendUserActionToKafka(UserActionApplicationDTO userAction){
        UserActionMessagingEvent userActionMessagingEvent = UserApplicationMapper.toMessaging(userAction);
        userActionMessagingProducer.send(userActionTopic, userActionMessagingEvent);
    }

}
