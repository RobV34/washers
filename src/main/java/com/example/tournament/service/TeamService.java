package com.example.tournament.service;

import com.example.tournament.model.Team;
import com.example.tournament.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    public void updateTeamRecord(Long teamId, boolean won) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team != null) {
            if (won) {
                team.setWins(team.getWins() + 1);
            } else {
                team.setLosses(team.getLosses() + 1);
            }
            saveTeam(team);
        }
    }

    public List<Team> getTopTeams(int numberOfTeams) {
        List<Team> allTeams = getAllTeams();
        allTeams.sort(Comparator.comparingInt(Team::getWins).reversed());
        return allTeams.subList(0, Math.min(numberOfTeams, allTeams.size()));
    }
}


