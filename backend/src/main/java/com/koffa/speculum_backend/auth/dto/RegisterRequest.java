package com.koffa.speculum_backend.auth.dto;

public record RegisterRequest (String name, String email, String password) {
}
