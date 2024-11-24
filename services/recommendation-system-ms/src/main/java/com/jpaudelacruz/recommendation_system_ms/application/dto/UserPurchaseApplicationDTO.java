package com.jpaudelacruz.recommendation_system_ms.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPurchaseApplicationDTO {

    private String productId;
    private Integer amount;

}
