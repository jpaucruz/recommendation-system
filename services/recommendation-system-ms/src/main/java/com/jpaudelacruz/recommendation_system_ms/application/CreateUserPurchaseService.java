package com.jpaudelacruz.recommendation_system_ms.application;

import com.jpaudelacruz.recommendation_system_ms.application.dto.UserPurchaseApplicationDTO;
import com.jpaudelacruz.recommendation_system_ms.application.mapper.UserApplicationMapper;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.UserPurchaseDetailsRepository;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.UserPurchaseRepository;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.UserPurchaseRepositoryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreateUserPurchaseService {

    private static final Logger logger = LoggerFactory.getLogger(CreateUserPurchaseService.class);

    private final UserPurchaseRepository userPurchaseRepository;
    private final UserPurchaseDetailsRepository userPurchaseDetailsRepository;

    public CreateUserPurchaseService(
            UserPurchaseRepository userPurchaseRepository,
            UserPurchaseDetailsRepository userPurchaseDetailsRepository) {
        this.userPurchaseRepository = userPurchaseRepository;
        this.userPurchaseDetailsRepository = userPurchaseDetailsRepository;
    }

    @Transactional
    public void createPurchaseInDatabase(String userId, List<UserPurchaseApplicationDTO> userPurchase) {
        UserPurchaseRepositoryEntity userPurchaseEntity = userPurchaseRepository.save(UserPurchaseRepositoryEntity.builder().userId(userId).build());
        logger.info("Purchase created with ID {}", userPurchaseEntity.getId().toString());
        userPurchase
                .forEach(purchaseDetails -> {
                    userPurchaseDetailsRepository.save(UserApplicationMapper.toRepository(userPurchaseEntity.getId(), purchaseDetails));
                });
    }

}
