package com.example.btsconcert.controller;

import com.example.btsconcert.model.Concert;
import com.example.btsconcert.repository.ConcertRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private final ConcertRepository repo;

    public ManagerController(ConcertRepository repo) {
        this.repo = repo;
    }

    // Manager dashboard for viewing all concerts
    @GetMapping
    public String managerHome(Model model) {
        model.addAttribute("concerts", repo.findAll());
        return "manager-concerts";
    }

    @GetMapping("/concerts")
    public String manageConcerts(Model model) {
        model.addAttribute("concerts", repo.findAll());
        return "manager-concerts";
    }

    @GetMapping("/concerts/new")
    public String createConcertForm(Model model) {
        model.addAttribute("concert", new Concert());
        return "concert-form";
    }

    @PostMapping("/concerts/save")
    public String saveConcert(Concert concert) {
        repo.save(concert);
        return "redirect:/manager";
    }

    @GetMapping("/concerts/edit/{id}")
    public String editConcert(@PathVariable Long id, Model model) {
        model.addAttribute("concert", repo.findById(id).orElse(null));
        return "concert-form";
    }

    @GetMapping("/concerts/delete/{id}")
    public String deleteConcert(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/manager";
    }
}
