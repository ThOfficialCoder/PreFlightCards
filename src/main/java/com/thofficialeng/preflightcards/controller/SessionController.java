package com.thofficialeng.preflightcards.controller;

import com.thofficialeng.preflightcards.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Controller
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/session")
    public String create() {
        UUID id = sessionService.create();
        return "redirect:/s/" + id;
    }

    @GetMapping("/s/{id}")
    public String view(@PathVariable("id") UUID id, Model model) {
        var view = sessionService.getView(id);
        model.addAttribute("view", view);
        return "session";
    }
}
