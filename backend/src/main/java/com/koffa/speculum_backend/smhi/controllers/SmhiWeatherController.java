package com.koffa.speculum_backend.smhi.controllers;

import com.koffa.speculum_backend.smhi.models.WeatherForecast;
import com.koffa.speculum_backend.smhi.services.SmhiWeatherService;
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
@RequestMapping("/api/weather")
public class SmhiWeatherController {
    private final RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/forecast")
    public ResponseEntity<WeatherForecast> getForecast(@RequestParam double lat, @RequestParam double lon) {
        String url = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/" + lon + "/lat/ " + lat + "/data.json";
        ResponseEntity<WeatherForecast> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Void>(new HttpHeaders()), WeatherForecast.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
