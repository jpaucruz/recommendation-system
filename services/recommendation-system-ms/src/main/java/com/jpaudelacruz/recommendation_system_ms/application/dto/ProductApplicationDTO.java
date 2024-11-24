package com.jpaudelacruz.recommendation_system_ms.application.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductApplicationDTO {

    private Long id;
    private String name;
    private String url;
    private String description;
    private Integer type;

}
