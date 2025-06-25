package com.koffa.speculum_backend.vasttrafik.models;

import lombok.Data;

import java.util.List;

@Data
public class LocationResponse {
    private List<Location> results;
    @Data
    public static class Location {
        private String gid;
        private String name;
        private String locationType;
        private double latitude;
        private double longitude;
        private boolean hasLocalService;
    }
}
