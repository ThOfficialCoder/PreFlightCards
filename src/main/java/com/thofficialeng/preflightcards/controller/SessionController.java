package com.thofficialeng.preflightcards.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
    @GetMapping("/s/{id}")
    public String session() {
        return "session";
    }
}
