package com.jpaudelacruz.recommendation_system_ms.infrastructure.controller.mapper;

import com.jpaudelacruz.recommendation_system_ms.application.dto.UserActionApplicationDTO;
import com.jpaudelacruz.recommendation_system_ms.application.dto.UserPurchaseApplicationDTO;
import org.openapitools.model.UserActionRequestDTO;
import org.openapitools.model.UserPurchaseRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserControllerMapper {

    public static UserActionApplicationDTO toApplication(String userId, UserActionRequestDTO userActionRequestDTO) {
        return UserActionApplicationDTO.builder()
                .userId(userId)
                .productId(userActionRequestDTO.getProductId())
                .action(userActionRequestDTO.getAction().getValue())
                .timestamp(userActionRequestDTO.getTimestamp())
                .build();
    }

    public static List<UserPurchaseApplicationDTO> toApplication(List<UserPurchaseRequestDTO> userPurchaseRequestDTO) {
        return userPurchaseRequestDTO
                .stream()
                .map(purchaseRequestDTO -> UserPurchaseApplicationDTO
                        .builder()
                        .productId(purchaseRequestDTO.getProductId())
                        .amount(purchaseRequestDTO.getAmount())
                        .build()
                ).toList();
    }

}