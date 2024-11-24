package com.jpaudelacruz.recommendation_system_ms.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "purchases")
public class UserPurchaseRepositoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "\"userId\"")
    private String userId;
    private Long timestamp;

    @PrePersist
    public void prePersist() {
        this.timestamp = System.currentTimeMillis();
    }

}
