package com.koffa.speculum_backend.smhi.models;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class WeatherForecast {
    private String approvedTime;
    private List<TimeSeries> timeSeries;

    @Data
    public static class TimeSeries {
        private String validTime;
        private List<Parameter> parameters;
        public Double getParameterValue(String name) {
            return parameters.stream()
                    .filter(p -> p.getName().equals(name))
                    .findFirst()
                    .map(p -> p.getValues().get(0))
                    .orElse(null);
        }
    }

    @Data
    public static class Parameter {
        private String name;
        private String unit;
        private List<Double> values;
    }
}