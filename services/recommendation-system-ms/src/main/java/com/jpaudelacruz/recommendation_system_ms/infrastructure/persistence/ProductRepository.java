package com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence;

import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.ProductRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductRepositoryEntity, Long> {

}
