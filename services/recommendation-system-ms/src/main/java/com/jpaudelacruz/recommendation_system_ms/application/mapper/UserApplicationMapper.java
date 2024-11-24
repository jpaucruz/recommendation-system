package com.jpaudelacruz.recommendation_system_ms.application.mapper;

import com.jpaudelacruz.recommendation_system_ms.application.dto.UserActionApplicationDTO;
import com.jpaudelacruz.recommendation_system_ms.application.dto.UserPurchaseApplicationDTO;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.messaging.producer.model.UserActionMessagingEvent;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.UserPurchaseDetailsRepositoryEntity;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.UserPurchaseRepositoryEntity;
import com.jpaudelacruz.recommendation_system_ms.shared.CustomDateUtils;

public class UserApplicationMapper {

    public static UserActionMessagingEvent toMessaging(UserActionApplicationDTO userActionApplicationDTO) {
        return UserActionMessagingEvent.newBuilder()
                .setUserId(userActionApplicationDTO.getUserId())
                .setProductId(userActionApplicationDTO.getProductId())
                .setAction(userActionApplicationDTO.getAction())
                .setTimestamp(userActionApplicationDTO.getTimestamp())
                .build();
    }

    public static UserPurchaseDetailsRepositoryEntity toRepository(Long purchaseId, UserPurchaseApplicationDTO userPurchaseApplicationDTO) {
        return UserPurchaseDetailsRepositoryEntity.builder()
                .productId(userPurchaseApplicationDTO.getProductId())
                .purchaseId(purchaseId)
                .amount(userPurchaseApplicationDTO.getAmount().longValue())
                .build();
    }

}