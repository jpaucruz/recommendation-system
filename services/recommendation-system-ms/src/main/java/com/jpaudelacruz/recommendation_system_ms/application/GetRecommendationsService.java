package com.jpaudelacruz.recommendation_system_ms.application;

import com.jpaudelacruz.recommendation_system_ms.application.mapper.ProductApplicationMapper;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.messaging.stream.RecommendationMessagingStream;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.ProductRepository;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.UserRecommendationRepository;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.ProductRepositoryEntity;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.UserRecommendationRepositoryEntity;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.mapper.ProductRepositoryMapper;
import org.openapitools.model.ProductResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GetRecommendationsService {

    private static final Logger logger = LoggerFactory.getLogger(GetRecommendationsService.class);

    private final UserRecommendationRepository userRecommendationRepository;
    private final ProductRepository productRepository;

    public GetRecommendationsService(UserRecommendationRepository userRecommendationRepository, ProductRepository productRepository) {
        this.userRecommendationRepository = userRecommendationRepository;
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> getUserRecommendations(String userId){
        List<UserRecommendationRepositoryEntity> userRecommendations = userRecommendationRepository.findTop2ByUserIdOrderByScoreDesc(userId);
        logger.info("Recommendations: {}", userRecommendations.toString());
        return userRecommendations
                .stream()
                .map(userRecommendation -> productRepository.findById(Long.valueOf(userRecommendation.getProductId())))
                .flatMap(recommendedProduct -> recommendedProduct.stream().map(ProductRepositoryMapper::toApplication))
                .map(ProductApplicationMapper::toController)
                .toList();
    }

}
