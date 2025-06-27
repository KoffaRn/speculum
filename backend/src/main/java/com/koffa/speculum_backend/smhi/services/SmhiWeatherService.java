package com.koffa.speculum_backend.smhi.services;

import com.koffa.speculum_backend.smhi.models.WeatherForecast;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmhiWeatherService {
    private final RestTemplate restTemplate = new RestTemplate();
    public WeatherForecast getWeatherForecast(double lat, double lon) {
        String url = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/" + lon + "/lat/ " + lat + "/data.json";
        return restTemplate.getForObject(url, WeatherForecast.class);
    }
}
