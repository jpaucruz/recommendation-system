package com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence;

import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.UserPurchaseRepositoryEntity;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.UserRecommendationRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPurchaseRepository extends JpaRepository<UserPurchaseRepositoryEntity, Long> {

}
