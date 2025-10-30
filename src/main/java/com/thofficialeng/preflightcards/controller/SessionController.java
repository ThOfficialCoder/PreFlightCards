package com.thofficialeng.preflightcards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SessionController {
    @GetMapping("/s/{id}")
    public String session() {
        return "session";
    }
}
