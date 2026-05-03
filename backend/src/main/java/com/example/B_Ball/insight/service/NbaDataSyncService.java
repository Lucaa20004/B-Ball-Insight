package com.example.B_Ball.insight.service;

import com.example.B_Ball.insight.entity.Player;
import com.example.B_Ball.insight.entity.Team;
import com.example.B_Ball.insight.repository.PlayerRepository;
import com.example.B_Ball.insight.repository.TeamRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviciu de sincronizare a datelor NBA.
 * Preia datele din BallDontLie API și le persistă în baza de date locală.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NbaDataSyncService {

    private final NbaApiClient nbaApiClient;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    /**
     * Sincronizează echipele din API în baza de date.
     */
    @Transactional
    public int syncTeams() {
        log.info("Starting team synchronization...");
        JsonNode response = nbaApiClient.fetchTeams();
        JsonNode data = response.get("data");

        int count = 0;
        if (data != null && data.isArray()) {
            for (JsonNode teamNode : data) {
                int apiId = teamNode.get("id").asInt();
                Team team = teamRepository.findByApiId(apiId)
                        .orElse(new Team());

                team.setApiId(apiId);
                team.setConference(teamNode.get("conference").asText());
                team.setDivision(teamNode.get("division").asText());
                team.setCity(teamNode.get("city").asText());
                team.setName(teamNode.get("name").asText());
                team.setFullName(teamNode.get("full_name").asText());
                team.setAbbreviation(teamNode.get("abbreviation").asText());

                teamRepository.save(team);
                count++;
            }
        }

        log.info("Synchronized {} teams.", count);
        return count;
    }

    /**
     * Sincronizează clasamentele pentru un sezon dat.
     */
    @Transactional
    public int syncStandings(int season) {
        log.info("Starting standings synchronization for season {}...", season);
        JsonNode response = nbaApiClient.fetchStandings(season);
        JsonNode data = response.get("data");

        int count = 0;
        if (data != null && data.isArray()) {
            for (JsonNode standingNode : data) {
                JsonNode teamNode = standingNode.get("team");
                int apiId = teamNode.get("id").asInt();

                Team team = teamRepository.findByApiId(apiId).orElse(null);
                if (team == null) {
                    // Creează echipa dacă nu există
                    team = new Team();
                    team.setApiId(apiId);
                    team.setConference(teamNode.get("conference").asText());
                    team.setDivision(teamNode.get("division").asText());
                    team.setCity(teamNode.get("city").asText());
                    team.setName(teamNode.get("name").asText());
                    team.setFullName(teamNode.get("full_name").asText());
                    team.setAbbreviation(teamNode.get("abbreviation").asText());
                }

                team.setWins(standingNode.get("wins").asInt());
                team.setLosses(standingNode.get("losses").asInt());
                team.setConferenceRank(standingNode.get("conference_rank").asInt());
                team.setDivisionRank(standingNode.get("division_rank").asInt());
                team.setConferenceRecord(standingNode.get("conference_record").asText());
                team.setDivisionRecord(standingNode.get("division_record").asText());
                team.setHomeRecord(standingNode.get("home_record").asText());
                team.setRoadRecord(standingNode.get("road_record").asText());
                team.setSeason(standingNode.get("season").asInt());

                teamRepository.save(team);
                count++;
            }
        }

        log.info("Synchronized standings for {} teams.", count);
        return count;
    }

    /**
     * Sincronizează jucătorii din API (cu paginare).
     * Parcurge toate paginile disponibile.
     */
    @Transactional
    public int syncPlayers() {
        log.info("Starting player synchronization...");
        int totalCount = 0;
        Integer cursor = null;

        do {
            JsonNode response = nbaApiClient.fetchPlayers(cursor, 100);
            JsonNode data = response.get("data");
            JsonNode meta = response.get("meta");

            if (data != null && data.isArray()) {
                for (JsonNode playerNode : data) {
                    int apiId = playerNode.get("id").asInt();
                    Player player = playerRepository.findByApiId(apiId)
                            .orElse(new Player());

                    player.setApiId(apiId);
                    player.setFirstName(playerNode.get("first_name").asText());
                    player.setLastName(playerNode.get("last_name").asText());
                    player.setPosition(getTextOrNull(playerNode, "position"));
                    player.setHeight(getTextOrNull(playerNode, "height"));
                    player.setWeight(getTextOrNull(playerNode, "weight"));
                    player.setJerseyNumber(getTextOrNull(playerNode, "jersey_number"));
                    player.setCollege(getTextOrNull(playerNode, "college"));
                    player.setCountry(getTextOrNull(playerNode, "country"));
                    player.setDraftYear(getIntOrNull(playerNode, "draft_year"));
                    player.setDraftRound(getIntOrNull(playerNode, "draft_round"));
                    player.setDraftNumber(getIntOrNull(playerNode, "draft_number"));

                    // Legătura cu echipa
                    JsonNode teamNode = playerNode.get("team");
                    if (teamNode != null && !teamNode.isNull()) {
                        int teamApiId = teamNode.get("id").asInt();
                        Team team = teamRepository.findByApiId(teamApiId).orElse(null);
                        player.setTeam(team);
                    }

                    playerRepository.save(player);
                    totalCount++;
                }
            }

            // Paginare
            cursor = (meta != null && meta.has("next_cursor") && !meta.get("next_cursor").isNull())
                    ? meta.get("next_cursor").asInt()
                    : null;

            log.info("Synced batch of players... total so far: {}", totalCount);

        } while (cursor != null);

        log.info("Synchronized {} players total.", totalCount);
        return totalCount;
    }

    /**
     * Sincronizare completă: echipe → clasamente → jucători
     */
    public void syncAll() {
        syncTeams();
        syncStandings(2024);
        syncPlayers();
    }

    private String getTextOrNull(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || value.isNull() || value.asText().isEmpty()) {
            return null;
        }
        return value.asText();
    }

    private Integer getIntOrNull(JsonNode node, String field) {
        JsonNode value = node.get(field);
        if (value == null || value.isNull()) {
            return null;
        }
        return value.asInt();
    }
}
