package com.koffa.speculum_backend.vasttrafik.models;

public record LegDTO(
    String travelMode,
    String departureTime,
    String arrivalTime,
    String departurePlaceName,
    String arrivalPlaceName
) {}