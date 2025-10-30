package com.thofficialeng.preflightcards.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FocusController {
    @GetMapping("/s/{id}/focus")
    public String focus() {
        return "focus";
    }
}
