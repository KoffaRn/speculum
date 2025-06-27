package com.koffa.speculum_backend.vasttrafik.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;

public class JourneyResponse {
    public List<JourneyResult> results;
    public Pagination pagination;
    public Links links;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JourneyResult {
        public String reconstructionReference;
        public String detailsReference;
        public AccessLink departureAccessLink;
        public List<TripLeg> tripLegs;
        public List<Object> connectionLinks;
        public AccessLink arrivalAccessLink;
        public boolean isDeparted;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccessLink {
        public String transportMode;
        public String transportSubMode;
        public Location origin;
        public Location destination;
        public List<LinkCoordinate> linkCoordinates;
        public int distanceInMeters;
        public OffsetDateTime plannedDepartureTime;
        public OffsetDateTime plannedArrivalTime;
        public int plannedDurationInMinutes;
        public OffsetDateTime estimatedDepartureTime;
        public OffsetDateTime estimatedArrivalTime;
        public int estimatedDurationInMinutes;
        public int estimatedNumberOfSteps;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        public String gid;
        public String name;
        public String locationType;
        public double latitude;
        public double longitude;
        public OffsetDateTime plannedTime;
        public OffsetDateTime estimatedTime;
        public OffsetDateTime estimatedOtherwisePlannedTime;
        public List<Object> notes;

        @JsonProperty("stopPoint")
        public StopPoint stopPoint;  // Optional, present only in some cases
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StopPoint {
        public String gid;
        public String name;
        public String platform;

        @JsonProperty("stopArea")
        public StopArea stopArea;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StopArea {
        public String gid;
        public String name;
        public double latitude;
        public double longitude;

        @JsonProperty("tariffZone1")
        public TariffZone tariffZone1;

        @JsonProperty("tariffZone2")
        public TariffZone tariffZone2;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TariffZone {
        public String gid;
        public String name;
        public int number;
        public String shortName;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LinkCoordinate {
        public double latitude;
        public double longitude;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TripLeg {
        public Location origin;
        public Location destination;
        public boolean isCancelled;
        public boolean isPartCancelled;
        public ServiceJourney serviceJourney;
        public OffsetDateTime plannedDepartureTime;
        public OffsetDateTime plannedArrivalTime;
        public OffsetDateTime estimatedDepartureTime;
        public OffsetDateTime estimatedArrivalTime;
        public int plannedDurationInMinutes;
        public int estimatedDurationInMinutes;
        public int journeyLegIndex;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ServiceJourney {
        public String gid;
        public String direction;

        @JsonProperty("directionDetails")
        public DirectionDetails directionDetails;

        public String number;
        public Line line;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DirectionDetails {
        public String fullDirection;
        public String shortDirection;
        public boolean isFrontEntry;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Line {
        public String shortName;
        public String designation;
        public boolean isWheelchairAccessible;
        public String name;
        public String backgroundColor;
        public String foregroundColor;
        public String borderColor;
        public String transportMode;
        public String transportSubMode;
    }

    public static class Pagination {
        public int limit;
        public int offset;
        public int size;
    }

    public static class Links {
        public String previous;
    }
}