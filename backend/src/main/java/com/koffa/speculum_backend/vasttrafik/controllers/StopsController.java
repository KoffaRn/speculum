package com.koffa.speculum_backend.vasttrafik.controllers;

import com.koffa.speculum_backend.vasttrafik.models.LocationResponse;
import com.koffa.speculum_backend.vasttrafik.services.VasttrafikTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/stops")
public class StopsController {
    @Autowired
    private VasttrafikTokenService tokenService;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/search")
    public ResponseEntity<LocationResponse> searchStops(@RequestParam String q) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenService.getToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = "https://ext-api.vasttrafik.se/pr/v4/locations/by-text?q=" + q;
        ResponseEntity<LocationResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, LocationResponse.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
