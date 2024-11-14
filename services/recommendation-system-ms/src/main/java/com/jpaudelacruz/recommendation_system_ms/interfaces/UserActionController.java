//package com.jpaudelacruz.recommendation_system_ms.interfaces;
//
//import com.jpaudelacruz.recommendation_system_ms.application.UserActionService;
//import com.jpaudelacruz.recommendation_system_ms.models.avro.UserAction;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/users/action")
//public class UserActionController {
//
//    private final UserActionService userActionService;
//
//    public UserActionController(UserActionService userActionService){
//        this.userActionService = userActionService;
//    }
//
//    @PostMapping()
//    public void sendAction(@RequestBody UserAction userAction) {
//
//        userActionService.sendUserActionToKafka(userAction);
//    }
//}
