package com.koffa.speculum_backend.vasttrafik.controllers;

import com.koffa.speculum_backend.vasttrafik.services.VasttrafikTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/journeys")
public class JourneyController {
    @Autowired
    private VasttrafikTokenService tokenService;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<JoruneyRespo>
}
