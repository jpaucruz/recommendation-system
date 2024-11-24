package com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recommendations")
public class UserRecommendationRepositoryEntity {

    @Id
    private String id;
    @Column(name = "\"userId\"")
    private String userId;
    @Column(name = "\"productId\"")
    private String productId;
    private double score;
    private String timestamp;

}
