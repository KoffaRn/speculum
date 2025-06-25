package com.koffa.speculum_backend.vasttrafik.services;

import com.koffa.speculum_backend.vasttrafik.models.TokenResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;
import java.time.Instant;
import java.util.Objects;

@Service
@Slf4j
public class VasttrafikTokenService {
    @Autowired
    Environment environment;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String tokenUrl = "https://ext-api.vasttrafik.se/token";
    private String accessToken;
    private Instant tokenExpiry;

    @PostConstruct
    public void init() {
        refreshToken();
    }

    public synchronized String getToken() {
        if (accessToken == null || Instant.now().isAfter(tokenExpiry.minusSeconds(60))) {
            refreshToken();
        }
        return accessToken;
    }

    private void refreshToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(Objects.requireNonNull(environment.getProperty("vasttrafik.auth.key")));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body,headers);
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(tokenUrl, request, TokenResponse.class);
        if(response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            accessToken = response.getBody().getAccessToken();
            tokenExpiry = Instant.now().plusSeconds(response.getBody().getExpiresIn());
            log.info("Got new token");
        } else {
            throw new RuntimeException("Failed to obtain västtrafik token: " + response);
        }
    }
}
