package com.example.B_Ball.insight.config;

import com.example.B_Ball.insight.entity.Player;
import com.example.B_Ball.insight.entity.Team;
import com.example.B_Ball.insight.repository.PlayerRepository;
import com.example.B_Ball.insight.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Încarcă date inițiale (seed) în baza de date la pornirea aplicației,
 * doar dacă tabelele sunt goale.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    @Override
    public void run(String... args) {
        if (teamRepository.count() > 0) {
            log.info("Database already seeded. Skipping...");
            return;
        }
        log.info("Seeding database with NBA teams and players...");
        seedTeams();
        seedPlayers();
        log.info("Database seeding complete!");
    }

    private Team t(int apiId, String conf, String div, String city, String name, String full, String abbr,
            int wins, int losses, int confRank, int divRank, String confRec, String divRec, String homeRec,
            String roadRec) {
        return Team.builder().apiId(apiId).conference(conf).division(div).city(city).name(name)
                .fullName(full).abbreviation(abbr).wins(wins).losses(losses).conferenceRank(confRank)
                .divisionRank(divRank).conferenceRecord(confRec).divisionRecord(divRec)
                .homeRecord(homeRec).roadRecord(roadRec).season(2024).build();
    }

    private void seedTeams() {
        List<Team> teams = List.of(
                t(1, "East", "Central", "Cleveland", "Cavaliers", "Cleveland Cavaliers", "CLE", 64, 18, 1, 1, "41-11",
                        "12-4", "34-7", "30-11"),
                t(2, "East", "Atlantic", "Boston", "Celtics", "Boston Celtics", "BOS", 60, 22, 2, 1, "38-14", "11-5",
                        "33-8", "27-14"),
                t(3, "East", "Atlantic", "New York", "Knicks", "New York Knicks", "NYK", 56, 26, 3, 2, "36-16", "12-4",
                        "30-11", "26-15"),
                t(4, "East", "Central", "Milwaukee", "Bucks", "Milwaukee Bucks", "MIL", 52, 30, 4, 2, "34-18", "10-6",
                        "28-13", "24-17"),
                t(5, "East", "Central", "Indiana", "Pacers", "Indiana Pacers", "IND", 50, 32, 5, 3, "32-20", "10-6",
                        "27-14", "23-18"),
                t(6, "East", "Southeast", "Orlando", "Magic", "Orlando Magic", "ORL", 48, 34, 6, 1, "31-21", "10-6",
                        "26-15", "22-19"),
                t(7, "East", "Central", "Detroit", "Pistons", "Detroit Pistons", "DET", 44, 38, 7, 4, "28-24", "8-8",
                        "24-17", "20-21"),
                t(8, "East", "Southeast", "Atlanta", "Hawks", "Atlanta Hawks", "ATL", 42, 40, 8, 2, "27-25", "8-8",
                        "23-18", "19-22"),
                t(9, "East", "Central", "Chicago", "Bulls", "Chicago Bulls", "CHI", 40, 42, 9, 5, "26-26", "7-9",
                        "22-19", "18-23"),
                t(10, "East", "Southeast", "Miami", "Heat", "Miami Heat", "MIA", 38, 44, 10, 3, "24-28", "7-9", "21-20",
                        "17-24"),
                t(11, "East", "Atlantic", "Philadelphia", "76ers", "Philadelphia 76ers", "PHI", 36, 46, 11, 3, "23-29",
                        "6-10", "20-21", "16-25"),
                t(12, "East", "Atlantic", "Toronto", "Raptors", "Toronto Raptors", "TOR", 30, 52, 12, 4, "19-33",
                        "5-11", "17-24", "13-28"),
                t(13, "East", "Southeast", "Charlotte", "Hornets", "Charlotte Hornets", "CHA", 28, 54, 13, 4, "18-34",
                        "4-12", "16-25", "12-29"),
                t(14, "East", "Atlantic", "Brooklyn", "Nets", "Brooklyn Nets", "BKN", 26, 56, 14, 5, "16-36", "4-12",
                        "15-26", "11-30"),
                t(15, "East", "Southeast", "Washington", "Wizards", "Washington Wizards", "WAS", 20, 62, 15, 5, "12-40",
                        "3-13", "12-29", "8-33"),
                t(16, "West", "Northwest", "Oklahoma City", "Thunder", "Oklahoma City Thunder", "OKC", 68, 14, 1, 1,
                        "39-13", "12-4", "35-6", "33-8"),
                t(17, "West", "Southwest", "Houston", "Rockets", "Houston Rockets", "HOU", 58, 24, 2, 1, "37-15",
                        "11-5", "31-10", "27-14"),
                t(18 + 1000, "West", "Pacific", "Golden State", "Warriors", "Golden State Warriors", "GSW", 10, 10, 3,
                        1,
                        "6-6", "2-2", "5-5", "5-5"), // placeholder apiId
                t(19, "West", "Northwest", "Minnesota", "Timberwolves", "Minnesota Timberwolves", "MIN", 52, 30, 4, 2,
                        "33-19", "10-6", "28-13", "24-17"),
                t(20, "West", "Northwest", "Denver", "Nuggets", "Denver Nuggets", "DEN", 50, 32, 5, 3, "32-20", "10-6",
                        "27-14", "23-18"),
                t(21, "West", "Pacific", "LA", "Clippers", "LA Clippers", "LAC", 48, 34, 6, 2, "30-22", "9-7", "26-15",
                        "22-19"),
                t(22, "West", "Pacific", "Sacramento", "Kings", "Sacramento Kings", "SAC", 46, 36, 7, 3, "29-23", "9-7",
                        "25-16", "21-20"),
                t(23, "West", "Southwest", "Dallas", "Mavericks", "Dallas Mavericks", "DAL", 44, 38, 8, 2, "28-24",
                        "8-8", "24-17", "20-21"),
                t(24, "West", "Southwest", "Memphis", "Grizzlies", "Memphis Grizzlies", "MEM", 42, 40, 9, 3, "27-25",
                        "8-8", "23-18", "19-22"),
                t(25, "West", "Pacific", "Phoenix", "Suns", "Phoenix Suns", "PHX", 40, 42, 10, 4, "25-27", "7-9",
                        "22-19", "18-23"),
                t(14 + 1000, "West", "Pacific", "Los Angeles", "Lakers", "Los Angeles Lakers", "LAL", 38, 44, 11, 5,
                        "24-28", "7-9", "21-20", "17-24"), // placeholder apiId
                t(26, "West", "Southwest", "New Orleans", "Pelicans", "New Orleans Pelicans", "NOP", 36, 46, 12, 4,
                        "23-29", "6-10", "20-21", "16-25"),
                t(27, "West", "Northwest", "Portland", "Trail Blazers", "Portland Trail Blazers", "POR", 30, 52, 13, 4,
                        "19-33", "5-11", "17-24", "13-28"),
                t(28, "West", "Northwest", "Utah", "Jazz", "Utah Jazz", "UTA", 26, 56, 14, 5, "16-36", "4-12", "15-26",
                        "11-30"),
                t(25 + 1000, "West", "Southwest", "San Antonio", "Spurs", "San Antonio Spurs", "SAS", 22, 60, 15, 5,
                        "14-38", "3-13", "13-28", "9-32") // placeholder apiId
        );

        teamRepository.saveAll(teams);
        log.info("Seeded {} teams.", teams.size());
    }

    private Player p(int apiId, String first, String last, String pos, String jersey, String height, String weight,
            String college, String country, String teamAbbr) {
        Team team = teamRepository.findAll().stream()
                .filter(t2 -> t2.getAbbreviation().equals(teamAbbr))
                .findFirst().orElse(null);
        return Player.builder().apiId(apiId).firstName(first).lastName(last).position(pos)
                .jerseyNumber(jersey).height(height).weight(weight).college(college).country(country).team(team)
                .build();
    }

    private void seedPlayers() {
        List<Player> players = List.of(
                // Cleveland Cavaliers
                p(666786, "Donovan", "Mitchell", "G", "45", "6-1", "215", "Louisville", "USA", "CLE"),
                p(115, "Darius", "Garland", "G", "10", "6-1", "192", "Vanderbilt", "USA", "CLE"),
                p(666896, "Evan", "Mobley", "F-C", "4", "6-11", "215", "USC", "USA", "CLE"),
                p(246, "Jarrett", "Allen", "C", "31", "6-11", "243", "Texas", "USA", "CLE"),
                // Boston Celtics
                p(434, "Jayson", "Tatum", "F", "0", "6-8", "210", "Duke", "USA", "BOS"),
                p(70, "Jaylen", "Brown", "G-F", "7", "6-6", "223", "California", "USA", "BOS"),
                p(666737, "Derrick", "White", "G", "9", "6-4", "190", "Colorado", "USA", "BOS"),
                p(240, "Kristaps", "Porzingis", "C-F", "8", "7-2", "240", "N/A", "Latvia", "BOS"),
                // New York Knicks
                p(52, "Jalen", "Brunson", "G", "11", "6-2", "190", "Villanova", "USA", "NYK"),
                p(666949, "Karl-Anthony", "Towns", "C", "32", "6-11", "248", "Kentucky", "USA", "NYK"),
                p(666820, "OG", "Anunoby", "F", "8", "6-7", "232", "Indiana", "USA", "NYK"),
                // Oklahoma City Thunder
                p(666969, "Shai", "Gilgeous-Alexander", "G", "2", "6-6", "195", "Kentucky", "Canada", "OKC"),
                p(666983, "Jalen", "Williams", "G-F", "8", "6-6", "195", "Santa Clara", "USA", "OKC"),
                p(666943, "Chet", "Holmgren", "C", "7", "7-0", "195", "Gonzaga", "USA", "OKC"),
                // Houston Rockets
                p(666987, "Jalen", "Green", "G", "4", "6-4", "186", "Prolific Prep", "USA", "HOU"),
                p(666988, "Alperen", "Sengun", "C", "28", "6-10", "243", "N/A", "Turkey", "HOU"),
                p(666990, "Jabari", "Smith Jr.", "F", "1", "6-10", "220", "Auburn", "USA", "HOU"),
                // Golden State Warriors
                p(115, "Stephen", "Curry", "G", "30", "6-2", "185", "Davidson", "USA", "GSW"),
                p(125, "Andrew", "Wiggins", "F", "22", "6-7", "197", "Kansas", "Canada", "GSW"),
                p(666884, "Jonathan", "Kuminga", "F", "0", "6-7", "225", "G League", "Congo", "GSW"),
                // Milwaukee Bucks
                p(15, "Giannis", "Antetokounmpo", "F", "34", "6-11", "243", "N/A", "Greece", "MIL"),
                p(126, "Damian", "Lillard", "G", "0", "6-2", "195", "Weber State", "USA", "MIL"),
                p(666780, "Bobby", "Portis", "F-C", "9", "6-10", "250", "Arkansas", "USA", "MIL"),
                // Denver Nuggets
                p(246001, "Nikola", "Jokic", "C", "15", "6-11", "284", "N/A", "Serbia", "DEN"),
                p(237, "Jamal", "Murray", "G", "27", "6-4", "215", "Kentucky", "Canada", "DEN"),
                p(666791, "Michael", "Porter Jr.", "F", "1", "6-10", "218", "Missouri", "USA", "DEN"),
                // Los Angeles Lakers
                p(237001, "LeBron", "James", "F", "23", "6-9", "250", "N/A", "USA", "LAL"),
                p(132, "Anthony", "Davis", "F-C", "3", "6-10", "253", "Kentucky", "USA", "LAL"),
                p(666971, "Austin", "Reaves", "G", "15", "6-5", "197", "Oklahoma", "USA", "LAL"),
                // Dallas Mavericks
                p(132001, "Luka", "Doncic", "G-F", "77", "6-7", "230", "N/A", "Slovenia", "DAL"),
                p(666875, "Kyrie", "Irving", "G", "11", "6-2", "195", "Duke", "USA", "DAL"),
                // Phoenix Suns
                p(132002, "Kevin", "Durant", "F", "35", "6-11", "240", "Texas", "USA", "PHX"),
                p(51, "Devin", "Booker", "G", "1", "6-5", "206", "Kentucky", "USA", "PHX"),
                p(50, "Bradley", "Beal", "G", "3", "6-4", "207", "Florida", "USA", "PHX"),
                // Indiana Pacers
                p(666950, "Tyrese", "Haliburton", "G", "0", "6-5", "185", "Iowa State", "USA", "IND"),
                p(666798, "Pascal", "Siakam", "F", "43", "6-8", "230", "New Mexico St", "Cameroon", "IND"),
                // Minnesota Timberwolves
                p(666892, "Anthony", "Edwards", "G-F", "5", "6-4", "225", "Georgia", "USA", "MIN"),
                p(666895, "Rudy", "Gobert", "C", "27", "7-1", "258", "N/A", "France", "MIN"),
                // Sacramento Kings
                p(666880, "De'Aaron", "Fox", "G", "5", "6-3", "185", "Kentucky", "USA", "SAC"),
                p(666881, "Domantas", "Sabonis", "F-C", "10", "6-10", "240", "Gonzaga", "USA", "SAC"),
                // Orlando Magic
                p(666951, "Paolo", "Banchero", "F", "5", "6-10", "250", "Duke", "USA", "ORL"),
                p(666952, "Franz", "Wagner", "F", "22", "6-10", "220", "Michigan", "Germany", "ORL"),
                // Memphis Grizzlies
                p(666900, "Ja", "Morant", "G", "12", "6-3", "174", "Murray State", "USA", "MEM"),
                p(666901, "Desmond", "Bane", "G", "22", "6-5", "215", "TCU", "USA", "MEM"),
                // Philadelphia 76ers
                p(145, "Joel", "Embiid", "C", "21", "7-0", "280", "Kansas", "Cameroon", "PHI"),
                p(666953, "Tyrese", "Maxey", "G", "0", "6-2", "200", "Kentucky", "USA", "PHI"),
                // Miami Heat
                p(50001, "Jimmy", "Butler", "F", "22", "6-7", "230", "Marquette", "USA", "MIA"),
                p(666782, "Bam", "Adebayo", "C", "13", "6-9", "255", "Kentucky", "USA", "MIA"),
                // Atlanta Hawks
                p(666905, "Trae", "Young", "G", "11", "6-1", "164", "Oklahoma", "USA", "ATL"),
                p(666906, "Jalen", "Johnson", "F", "1", "6-9", "220", "Duke", "USA", "ATL"));

        // Fix duplicate apiIds by making them unique
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setApiId(90000 + i);
        }

        playerRepository.saveAll(players);
        log.info("Seeded {} players.", players.size());
    }
}
