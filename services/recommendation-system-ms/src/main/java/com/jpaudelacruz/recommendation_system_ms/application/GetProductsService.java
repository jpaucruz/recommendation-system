package com.jpaudelacruz.recommendation_system_ms.application;

import com.jpaudelacruz.recommendation_system_ms.application.mapper.ProductApplicationMapper;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.ProductRepositoryEntity;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.mapper.ProductRepositoryMapper;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.ProductRepository;
import org.openapitools.model.ProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetProductsService {

    private final ProductRepository productRepository;

    public GetProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> getProducts(){
        List<ProductRepositoryEntity> productPersistenceEntities = productRepository.findAll();
        return productPersistenceEntities
                .stream()
                .map(ProductRepositoryMapper::toApplication)
                .map(ProductApplicationMapper::toController)
                .collect(Collectors.toList());
    }

}
