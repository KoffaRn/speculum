package com.koffa.speculum_backend.auth;

import com.koffa.speculum_backend.auth.model.AuthResult;
import com.koffa.speculum_backend.common.exceptions.LoginFailedException;
import com.koffa.speculum_backend.user.model.User;
import com.koffa.speculum_backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public AuthResult login(String email, String password) {
        throw new LoginFailedException("Login failed");
    }

    public User register(String email, String name, String password) {
        return null;
    }
}
