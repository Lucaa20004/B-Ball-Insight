package com.example.B_Ball.insight.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Client HTTP pentru BallDontLie NBA API.
 * Gestionează autentificarea și paginarea.
 */
@Service
@Slf4j
public class NbaApiClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${balldontlie.api.key}")
    private String apiKey;

    public NbaApiClient(@Value("${balldontlie.api.base-url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Fetch toate echipele NBA.
     */
    public JsonNode fetchTeams() {
        log.info("Fetching teams from BallDontLie API...");
        String response = restClient.get()
                .uri("/teams")
                .header("Authorization", apiKey)
                .retrieve()
                .body(String.class);
        return parseJson(response);
    }

    /**
     * Fetch jucători cu paginare.
     * @param cursor cursorul pentru pagina următoare (null pentru prima pagină)
     * @param perPage numărul de rezultate per pagină (max 100)
     */
    public JsonNode fetchPlayers(Integer cursor, int perPage) {
        log.info("Fetching players from BallDontLie API (cursor={}, perPage={})...", cursor, perPage);

        String uri = "/players?per_page=" + perPage;
        if (cursor != null) {
            uri += "&cursor=" + cursor;
        }

        String response = restClient.get()
                .uri(uri)
                .header("Authorization", apiKey)
                .retrieve()
                .body(String.class);
        return parseJson(response);
    }

    /**
     * Fetch jucătorii activi.
     * @param cursor cursorul pentru pagina următoare (null pentru prima pagină)
     * @param perPage numărul de rezultate per pagină (max 100)
     */
    public JsonNode fetchActivePlayers(Integer cursor, int perPage) {
        log.info("Fetching active players from BallDontLie API (cursor={}, perPage={})...", cursor, perPage);

        String uri = "/players/active?per_page=" + perPage;
        if (cursor != null) {
            uri += "&cursor=" + cursor;
        }

        String response = restClient.get()
                .uri(uri)
                .header("Authorization", apiKey)
                .retrieve()
                .body(String.class);
        return parseJson(response);
    }

    /**
     * Fetch clasamentele pentru un sezon dat.
     * @param season anul sezonului (ex: 2024 pentru sezonul 2024-2025)
     */
    public JsonNode fetchStandings(int season) {
        log.info("Fetching standings for season {} from BallDontLie API...", season);
        String response = restClient.get()
                .uri("/standings?season=" + season)
                .header("Authorization", apiKey)
                .retrieve()
                .body(String.class);
        return parseJson(response);
    }

    private JsonNode parseJson(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            log.error("Error parsing JSON response: {}", e.getMessage());
            throw new RuntimeException("Failed to parse API response", e);
        }
    }
}
