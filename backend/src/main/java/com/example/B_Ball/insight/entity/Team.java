package com.example.B_Ball.insight.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_id", unique = true, nullable = false)
    private Integer apiId;

    @Column(nullable = false)
    private String conference;

    @Column(nullable = false)
    private String division;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String name;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, length = 5)
    private String abbreviation;

    // ---- Standings fields ----

    private Integer wins;

    private Integer losses;

    @Column(name = "conference_rank")
    private Integer conferenceRank;

    @Column(name = "division_rank")
    private Integer divisionRank;

    @Column(name = "conference_record")
    private String conferenceRecord;

    @Column(name = "division_record")
    private String divisionRecord;

    @Column(name = "home_record")
    private String homeRecord;

    @Column(name = "road_record")
    private String roadRecord;

    private Integer season;
}
