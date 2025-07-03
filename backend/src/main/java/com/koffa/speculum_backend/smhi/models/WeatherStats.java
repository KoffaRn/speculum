package com.koffa.speculum_backend.smhi.models;

import java.time.LocalDate;

public record WeatherStats(
    LocalDate date,
    double minTemp,
    double maxTemp,
    double totalPrecipitation,
    double maxChanceOfRain) {}
