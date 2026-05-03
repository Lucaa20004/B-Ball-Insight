package com.example.B_Ball.insight.controller;

import com.example.B_Ball.insight.entity.Team;
import com.example.B_Ball.insight.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamRepository teamRepository;

    /** Returnează toate echipele ordonate după conferință și rang. */
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamRepository.findAllByOrderByConferenceAscConferenceRankAsc());
    }

    /** Returnează o echipă după ID-ul intern. */
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return teamRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** Returnează clasamentele grupate pe conferințe. */
    @GetMapping("/standings")
    public ResponseEntity<Map<String, List<Team>>> getStandings() {
        Map<String, List<Team>> standings = new LinkedHashMap<>();
        standings.put("East", teamRepository.findByConferenceOrderByConferenceRankAsc("East"));
        standings.put("West", teamRepository.findByConferenceOrderByConferenceRankAsc("West"));
        return ResponseEntity.ok(standings);
    }
}
