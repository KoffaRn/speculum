package com.koffa.speculum_backend.vasttrafik.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koffa.speculum_backend.vasttrafik.models.JourneyResponse;
import com.koffa.speculum_backend.vasttrafik.models.LegDTO;
import com.koffa.speculum_backend.vasttrafik.services.VasttrafikTokenService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/journeys")
public class JourneyController {
    @Autowired
    private VasttrafikTokenService tokenService;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/journey")
    public ResponseEntity<List<LegDTO>> getJourney(@RequestParam double fromLat, @RequestParam double fromLon, @RequestParam double toLat, @RequestParam double toLon) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenService.getToken());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = "https://ext-api.vasttrafik.se/pr/v4/journeys?" +
                "originLatitude=" + fromLat +
                "&originLongitude=" + fromLon +
                "&destinationLatitude=" + toLat +
                "&destinationLongitude=" + toLon +
                "&limit=1&onlyDirectConnections=false&includeNearbyStopAreas=false&useRealTimeMode=false&includeOccupancy=false&bodSearch=false";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        ArrayList<LegDTO> legs = new ArrayList<>();
        if(response.getBody() != null) {
            String jsonString = response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(jsonString);
                JsonNode results = root.path("results");
                for (JsonNode result : results) {
                    // Extract from departureAccessLink
                    JsonNode dal = result.path("departureAccessLink");
                    if (!dal.isMissingNode()) {
                        legs.add(buildLegFromLink(dal));
                    }

                    // Extract from tripLegs
                    for (JsonNode tripLeg : result.path("tripLegs")) {
                        legs.add(buildLegFromTripLeg(tripLeg));
                    }

                    // Extract from connectionLinks
                    for (JsonNode link : result.path("connectionLinks")) {
                        legs.add(buildLegFromLink(link));
                    }

                    // Extract from arrivalAccessLink
                    JsonNode aal = result.path("arrivalAccessLink");
                    if (!aal.isMissingNode()) {
                        legs.add(buildLegFromLink(aal));
                    }

                    // Extract from destinationLink
                    JsonNode dl = result.path("destinationLink");
                    if (!dl.isMissingNode()) {
                        legs.add(buildLegFromLink(dl));
                    }
                }
            } catch (JsonProcessingException e) {
                return ResponseEntity.internalServerError().body(null);
            }
        }
        return ResponseEntity.status(200).body(legs);
    }

    private LegDTO buildLegFromLink(JsonNode link) {
        String travelMode = link.path("transportMode").asText();
        String departureTime = link.path("plannedDepartureTime").asText();
        String arrivalTime = link.path("plannedArrivalTime").asText();

        String departurePlaceName = link.path("origin").path("name").asText(null);
        if (departurePlaceName == null) {
            departurePlaceName = link.path("origin").path("stopPoint").path("name").asText(null);
        }

        String arrivalPlaceName = link.path("destination").path("name").asText(null);
        if (arrivalPlaceName == null) {
            arrivalPlaceName = link.path("destination").path("stopPoint").path("name").asText(null);
        }

        return new LegDTO(travelMode, departureTime, arrivalTime, departurePlaceName, arrivalPlaceName);
    }
    private LegDTO buildLegFromTripLeg(JsonNode tripLeg) {
        JsonNode serviceJourney = tripLeg.path("serviceJourney");
        String travelMode = serviceJourney.path("line").path("transportMode").asText();

        String departureTime = tripLeg.path("plannedDepartureTime").asText();
        String arrivalTime = tripLeg.path("plannedArrivalTime").asText();

        String departurePlaceName = tripLeg.path("origin").path("stopPoint").path("name").asText(null);
        String arrivalPlaceName = tripLeg.path("destination").path("stopPoint").path("name").asText(null);

        return new LegDTO(travelMode, departureTime, arrivalTime, departurePlaceName, arrivalPlaceName);
    }
}
