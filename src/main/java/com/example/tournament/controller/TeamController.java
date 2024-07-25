package com.example.tournament.controller;

import com.example.tournament.model.Team;
import com.example.tournament.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/teams")
    public String showTeamsPage(Model model) {
        model.addAttribute("teams", teamService.getAllTeams());
        return "teams";
    }

    @PostMapping("/teams")
    public String createTeam(@RequestParam("name") String name) {
        Team team = new Team();
        team.setName(name);
        teamService.saveTeam(team);
        return "redirect:/teams";
    }
}


