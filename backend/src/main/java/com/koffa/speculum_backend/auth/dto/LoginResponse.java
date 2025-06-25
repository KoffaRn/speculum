package com.koffa.speculum_backend.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;
    private String username;
    private String role;
}
