package com.jpaudelacruz.recommendation_system_ms.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActionApplicationDTO {

    private String userId;
    private String productId;
    private String action;
    private String timestamp;

}
