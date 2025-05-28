package com.koffa.speculum_backend.auth.model;

import com.koffa.speculum_backend.user.model.User;

public record AuthResult (String token, User user) {}