package com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence;

import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.UserPurchaseDetailsRepositoryEntity;
import com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity.UserPurchaseRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPurchaseDetailsRepository extends JpaRepository<UserPurchaseDetailsRepositoryEntity, Long> {

}
