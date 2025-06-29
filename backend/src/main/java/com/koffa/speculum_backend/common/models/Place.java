package com.koffa.speculum_backend.common.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class Place {
    private String name;
    private double latitude;
    private double longitude;
    private boolean showWeather;
}
