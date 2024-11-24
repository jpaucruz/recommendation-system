package com.jpaudelacruz.recommendation_system_ms.infrastructure.controller;

import com.jpaudelacruz.recommendation_system_ms.application.CreateUserActionService;
import com.jpaudelacruz.recommendation_system_ms.application.CreateUserPurchaseService;
import com.jpaudelacruz.recommendation_system_ms.application.GetRecommendationsService;
import com.jpaudelacruz.recommendation_system_ms.application.dto.UserActionApplicationDTO;
import com.jpaudelacruz.recommendation_system_ms.application.dto.UserPurchaseApplicationDTO;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.controller.mapper.UserControllerMapper;
import org.openapitools.api.UserApi;
import org.openapitools.model.ProductResponseDTO;
import org.openapitools.model.UserActionRequestDTO;
import org.openapitools.model.UserPurchaseRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {

    private final CreateUserActionService createUserActionService;
    private final GetRecommendationsService getRecommendationsService;
    private final CreateUserPurchaseService createUserPurchaseService;

    public UserController(
            CreateUserActionService createUserActionService,
            GetRecommendationsService getRecommendationsService,
            CreateUserPurchaseService createUserPurchaseService) {
        this.createUserActionService = createUserActionService;
        this.getRecommendationsService = getRecommendationsService;
        this.createUserPurchaseService = createUserPurchaseService;
    }

    @Override
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST})
    public ResponseEntity<Void> registerAction(String userId, UserActionRequestDTO userActionRequestDTO) {
        UserActionApplicationDTO userActionApplicationDTO = UserControllerMapper.toApplication(userId, userActionRequestDTO);
        createUserActionService.sendUserActionToKafka(userActionApplicationDTO);
        return ResponseEntity.noContent().build();
    }

    @Override
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET})
    public ResponseEntity<List<ProductResponseDTO>> getRecommendations(String userId){
        List<ProductResponseDTO> recommendedProductsResponseDTOs = getRecommendationsService.getUserRecommendations(userId);
        return ResponseEntity.ok(recommendedProductsResponseDTOs);
    }

    @Override
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST})
    public ResponseEntity<Void> registerPurchase(String userId, List<UserPurchaseRequestDTO> userPurchaseRequestDTO){
        List<UserPurchaseApplicationDTO> userPurchaseApplicationDTOs = UserControllerMapper.toApplication(userPurchaseRequestDTO);
        createUserPurchaseService.createPurchaseInDatabase(userId, userPurchaseApplicationDTOs);
        return ResponseEntity.noContent().build();
    }

}
