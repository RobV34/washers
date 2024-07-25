package com.example.tournament.service;

import com.example.tournament.model.Match;
import com.example.tournament.model.Team;
import com.example.tournament.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamService teamService;

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    public Match getMatchById(Long id) {
        return matchRepository.findById(id).orElse(null);
    }

    public void recordMatchResult(Long matchId, String result) {
        Match match = getMatchById(matchId);
        if (match != null) {
            match.setResult(result);
            saveMatch(match);

            if (result.equals("team1")) {
                teamService.updateTeamRecord(match.getTeam1().getId(), true);
                teamService.updateTeamRecord(match.getTeam2().getId(), false);
            } else if (result.equals("team2")) {
                teamService.updateTeamRecord(match.getTeam1().getId(), false);
                teamService.updateTeamRecord(match.getTeam2().getId(), true);
            }
        }
    }

    public List<Match> createPlayoffs() {
        List<Team> topTeams = teamService.getTopTeams(8); // Adjust number of teams as needed
        List<Match> playoffMatches = new ArrayList<>();

        while (topTeams.size() > 1) {
            Team team1 = topTeams.remove(0);
            Team team2 = topTeams.remove(0);
            Match match = new Match(team1, team2);
            playoffMatches.add(match);
            saveMatch(match);
        }

        return playoffMatches;
    }

    public void generateRoundRobinSchedule() {
        List<Team> teams = teamService.getAllTeams();
        Collections.shuffle(teams); // Randomize team order

        for (int round = 0; round < teams.size() - 1; round++) {
            for (int i = 0; i < teams.size() / 2; i++) {
                Team team1 = teams.get(i);
                Team team2 = teams.get(teams.size() - 1 - i);
                Match match = new Match(team1, team2);
                saveMatch(match);
            }
            // Rotate teams for next round (except the first one)
            Team lastTeam = teams.remove(teams.size() - 1);
            teams.add(1, lastTeam);
        }
    }
}
