package com.koffa.speculum_backend.auth;

import com.koffa.speculum_backend.auth.dto.AuthRequest;
import com.koffa.speculum_backend.auth.dto.AuthResponse;
import com.koffa.speculum_backend.auth.dto.RegisterRequest;
import com.koffa.speculum_backend.auth.model.AuthResult;
import com.koffa.speculum_backend.user.model.User;
import com.koffa.speculum_backend.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterRequest registerRequest) {
        User user = authService.register(registerRequest.email(), registerRequest.name(), registerRequest.password());
        return UserDTO.fromUser(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        AuthResult result = authService.login(authRequest.email(), authRequest.password());
        return new AuthResponse(result.token(), UserDTO.fromUser(result.user()));
    }
}