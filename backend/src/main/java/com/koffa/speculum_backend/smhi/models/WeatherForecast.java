package com.koffa.speculum_backend.smhi.models;

import lombok.Data;

import java.util.List;

@Data
public class WeatherForecast {
    private String approvedTime;
    private List<TimeSeries> timeSeries;

    @Data
    public static class TimeSeries {
        private String validTime;
        private List<Parameter> parameters;
    }

    @Data
    public static class Parameter {
        private String name;
        private String unit;
        private List<Double> values;
    }
}