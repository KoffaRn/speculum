package com.koffa.speculum_backend.smhi.models;

import java.util.List;

public record WeatherSummary(
        double currentTemp,
        List<WeatherStats> dailyStats) {}
