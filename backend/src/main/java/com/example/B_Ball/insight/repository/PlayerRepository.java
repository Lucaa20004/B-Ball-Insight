package com.example.B_Ball.insight.repository;

import com.example.B_Ball.insight.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByApiId(Integer apiId);

    List<Player> findByTeamIdOrderByLastNameAsc(Long teamId);

    List<Player> findByTeamApiIdOrderByLastNameAsc(Integer teamApiId);

    List<Player> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
