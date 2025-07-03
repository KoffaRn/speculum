package com.koffa.speculum_backend.smhi.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.koffa.speculum_backend.smhi.models.WeatherForecast;
import com.koffa.speculum_backend.smhi.models.WeatherStats;
import com.koffa.speculum_backend.smhi.models.WeatherSummary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class SmhiWeatherService {
    private final RestTemplate restTemplate = new RestTemplate();
    public WeatherSummary getWeatherForecast(double lat, double lon) {
        String url = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/" + lon + "/lat/ " + lat + "/data.json";
        WeatherForecast forecast = restTemplate.getForObject(url, WeatherForecast.class);
        if(forecast == null)
            return null;
        return extractWeatherSummary(forecast);
    }
    private WeatherSummary extractWeatherSummary(WeatherForecast forecast) {
        Double currentTemp = forecast.getTimeSeries().get(0).getParameterValue("t");
        Map<LocalDate, List<WeatherForecast.TimeSeries>> groupedByDay = new TreeMap<>();
        for(WeatherForecast.TimeSeries ts : forecast.getTimeSeries()) {
            LocalDate date = OffsetDateTime.parse(ts.getValidTime()).toLocalDateTime().toLocalDate();
            groupedByDay.computeIfAbsent(date, k -> new ArrayList<>()).add(ts);
        }
        List<WeatherStats> stats = groupedByDay.entrySet().stream()
                .limit(5)
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    double minTemp = Double.MAX_VALUE, maxTemp = Double.MIN_VALUE;
                    double totalPrecip = 0.0;
                    double maxSpp = 0.0;
                    for(WeatherForecast.TimeSeries ts : entry.getValue()) {
                        for(WeatherForecast.Parameter param : ts.getParameters()) {
                            String name = param.getName();
                            double value = param.getValues().get(0);
                            switch (name) {
                                case "t" -> {
                                    minTemp = Math.min(minTemp, value);
                                    maxTemp = Math.max(maxTemp, value);
                                }
                                case "tp" -> totalPrecip += value;
                                case "spp" -> maxSpp = Math.max(maxSpp, value);

                            }
                        }
                    }
                    return new WeatherStats(date, minTemp, maxTemp, totalPrecip, maxSpp);
                }).toList();
        return new WeatherSummary(currentTemp, stats);
    }
}
