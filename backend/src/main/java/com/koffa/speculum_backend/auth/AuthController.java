package com.koffa.speculum_backend.auth;

import com.koffa.speculum_backend.auth.dto.AuthRequest;
import com.koffa.speculum_backend.auth.dto.LoginResponse;
import com.koffa.speculum_backend.common.utils.JwtUtil;
import com.koffa.speculum_backend.user.UserRepository;
import com.koffa.speculum_backend.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(!passwordEncoder.matches(authRequest.password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        LoginResponse response = LoginResponse.builder()
                .role(user.getRole())
                .token(token)
                .username(user.getUsername())
                .build();

        return ResponseEntity.ok(response);
    }
}
