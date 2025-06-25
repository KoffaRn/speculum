package com.koffa.speculum_backend.vasttrafik.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TokenResponse {
    private String accessToken;
    private String scope;
    private String tokenType;
    private int expiresIn;
}
