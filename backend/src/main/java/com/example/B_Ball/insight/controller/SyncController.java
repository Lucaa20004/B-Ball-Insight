package com.example.B_Ball.insight.controller;

import com.example.B_Ball.insight.service.NbaDataSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class SyncController {

    private final NbaDataSyncService syncService;

    /** Sincronizare completă: echipe + clasamente + jucători din BallDontLie API. */
    @PostMapping("/all")
    public ResponseEntity<Map<String, String>> syncAll() {
        try {
            syncService.syncAll();
            return ResponseEntity.ok(Map.of("status", "success", "message", "Sincronizare completă finalizată."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    /** Sincronizare doar echipele. */
    @PostMapping("/teams")
    public ResponseEntity<Map<String, Object>> syncTeams() {
        try {
            int count = syncService.syncTeams();
            return ResponseEntity.ok(Map.of("status", "success", "count", count));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    /** Sincronizare clasamente pentru un sezon. */
    @PostMapping("/standings")
    public ResponseEntity<Map<String, Object>> syncStandings(@RequestParam(defaultValue = "2024") int season) {
        try {
            int count = syncService.syncStandings(season);
            return ResponseEntity.ok(Map.of("status", "success", "count", count));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }

    /** Sincronizare jucători. */
    @PostMapping("/players")
    public ResponseEntity<Map<String, Object>> syncPlayers() {
        try {
            int count = syncService.syncPlayers();
            return ResponseEntity.ok(Map.of("status", "success", "count", count));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
}
