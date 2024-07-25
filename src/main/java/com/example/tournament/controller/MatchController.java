package com.example.tournament.controller;

import com.example.tournament.model.Match;
import com.example.tournament.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/matches")
    public String showMatchesPage(Model model) {
        model.addAttribute("matches", matchService.getAllMatches());
        return "matches";
    }

    @PostMapping("/recordResult")
    public String recordMatchResult(@RequestParam("matchId") Long matchId, @RequestParam("result") String result) {
        matchService.recordMatchResult(matchId, result);
        return "redirect:/matches";
    }

    @GetMapping("/createPlayoffs")
    public String createPlayoffs(Model model) {
        model.addAttribute("playoffs", matchService.createPlayoffs());
        return "playoffs";
    }

    @GetMapping("/generateSchedule")
    public String generateSchedule() {
        matchService.generateRoundRobinSchedule();
        return "redirect:/matches";
    }
}

