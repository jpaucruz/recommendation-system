package com.jpaudelacruz.recommendation_system_ms.interfaces;

import com.jpaudelacruz.recommendation_system_ms.application.UserActionService;
import com.jpaudelacruz.recommendation_system_ms.models.avro.UserAction;
import org.openapitools.api.UserApi;
import org.openapitools.model.RegisterActionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestUserActionController implements UserApi {

    private final UserActionService userActionService;

    public TestUserActionController(UserActionService userActionService){
        this.userActionService = userActionService;
    }

    @Override
    public ResponseEntity<Void> registerAction(String userId, RegisterActionRequest registerActionRequest) {
        UserAction userAction = UserAction.newBuilder()
                .setUserId(userId)
                .setAction(registerActionRequest.getAction().getValue())
                .setProductId(registerActionRequest.getProductId())
                .setTimestamp(registerActionRequest.getTimestamp())
                .build();
        userActionService.sendUserActionToKafka(userAction);
        return ResponseEntity.noContent().build();
    }
}
