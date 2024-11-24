package com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.mapper;

import com.jpaudelacruz.recommendation_system_ms.application.dto.ProductApplicationDTO;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.ProductRepositoryEntity;

public class ProductRepositoryMapper {

    public static ProductApplicationDTO toApplication(ProductRepositoryEntity productRepositoryEntity) {
        return ProductApplicationDTO.builder()
                .id(productRepositoryEntity.getId())
                .name(productRepositoryEntity.getName())
                .description(productRepositoryEntity.getDescription())
                .url(productRepositoryEntity.getUrl())
                .type(productRepositoryEntity.getType())
                .build();
    }

}