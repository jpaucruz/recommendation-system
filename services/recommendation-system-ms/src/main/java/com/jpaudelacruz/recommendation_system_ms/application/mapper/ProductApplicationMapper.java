package com.jpaudelacruz.recommendation_system_ms.application.mapper;

import com.jpaudelacruz.recommendation_system_ms.application.dto.ProductApplicationDTO;
import org.openapitools.model.ProductResponseDTO;

public class ProductApplicationMapper {

    public static ProductResponseDTO toController(ProductApplicationDTO productApplicationDTO) {
        return ProductResponseDTO.builder()
                .id(productApplicationDTO.getId())
                .name(productApplicationDTO.getName())
                .description(productApplicationDTO.getDescription())
                .imageUrl(productApplicationDTO.getUrl())
                .type(productApplicationDTO.getType())
                .build();
    }

}