package com.koffa.speculum_backend.auth.dto;

import com.koffa.speculum_backend.user.dto.UserDTO;

public record AuthResponse(String token, UserDTO user) {
}
