package com.example.B_Ball.insight.controller;

import com.example.B_Ball.insight.entity.Player;
import com.example.B_Ball.insight.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerRepository playerRepository;

    /** Returnează toți jucătorii. */
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerRepository.findAll());
    }

    /** Returnează jucătorii unei echipe (după ID-ul intern al echipei). */
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Player>> getPlayersByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(playerRepository.findByTeamIdOrderByLastNameAsc(teamId));
    }

    /** Caută jucători după nume. */
    @GetMapping("/search")
    public ResponseEntity<List<Player>> searchPlayers(@RequestParam String query) {
        return ResponseEntity.ok(
            playerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query)
        );
    }
}
