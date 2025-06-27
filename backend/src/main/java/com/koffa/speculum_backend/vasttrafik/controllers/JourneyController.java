package com.koffa.speculum_backend.vasttrafik.controllers;

import com.koffa.speculum_backend.vasttrafik.models.JourneyResponse;
import com.koffa.speculum_backend.vasttrafik.services.VasttrafikTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/journeys")
public class JourneyController {
    @Autowired
    private VasttrafikTokenService tokenService;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<JourneyResponse> getJourney(@RequestParam double fromLat, @RequestParam double fromLon, @RequestParam double toLat, @RequestParam double toLon) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenService.getToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = "https://ext-api.vasttrafik.se/pr/v4/journeys?" +
                "originLatitude=" + fromLat +
                "&originLongitude=" + fromLon +
                "&destinationLatitude=" + toLat +
                "&destinationLongitude=" + fromLon +
                "&limit=1&onlyDirectConnections=false&includeNearbyStopAreas=false&useRealTimeMode=false&includeOccupancy=false&bodSearch=false";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                JourneyResponse.class
        );
    }
}
