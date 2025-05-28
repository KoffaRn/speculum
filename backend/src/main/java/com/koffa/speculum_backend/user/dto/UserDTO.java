package com.koffa.speculum_backend.user.dto;

import com.koffa.speculum_backend.user.model.User;

public record UserDTO(Long id, String email, String name) {
    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getName());
    }
}