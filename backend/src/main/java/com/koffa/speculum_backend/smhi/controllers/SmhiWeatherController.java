package com.koffa.speculum_backend.smhi.controllers;

import com.koffa.speculum_backend.smhi.models.WeatherForecast;
import com.koffa.speculum_backend.smhi.models.WeatherSummary;
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

@RestController
@RequestMapping("/api/weather")
public class SmhiWeatherController {
    @Autowired
    private SmhiWeatherService weatherService;

    @GetMapping("/forecast")
    public ResponseEntity<WeatherSummary> getForecast(@RequestParam double lat, @RequestParam double lon) {
        return ResponseEntity.ok(weatherService.getWeatherForecast(lat, lon));
    }
}
