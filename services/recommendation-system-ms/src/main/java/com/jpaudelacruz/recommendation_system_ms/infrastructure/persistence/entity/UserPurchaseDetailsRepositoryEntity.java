package com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "purchases_details")
public class UserPurchaseDetailsRepositoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "\"purchaseId\"")
    private Long purchaseId;
    @Column(name = "\"productId\"")
    private String productId;
    private Long amount;

}
