package com.jpaudelacruz.recommendation_system_ms.infrastructure.controller;

import com.jpaudelacruz.recommendation_system_ms.application.GetProductsService;
import org.openapitools.api.ProductApi;
import org.openapitools.model.ProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements ProductApi {

    private final GetProductsService getProductsService;

    public ProductController(GetProductsService getProductsService) {
        this.getProductsService = getProductsService;
    }

    @Override
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET})
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        List<ProductResponseDTO> productResponseDTOs = getProductsService.getProducts();
        return ResponseEntity.ok(productResponseDTOs);
    }

}
