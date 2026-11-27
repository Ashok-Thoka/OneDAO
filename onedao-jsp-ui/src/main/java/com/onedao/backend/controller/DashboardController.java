package com.onedao.backend.controller;

import com.onedao.backend.dto.MessageResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/dashboard")
    public MessageResponse dashboard(Authentication authentication) {
        return new MessageResponse("Welcome " + authentication.getName());
    }

    @GetMapping("/settings")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public MessageResponse settings(Authentication authentication) {
        return new MessageResponse("Settings access granted for " + authentication.getName());
    }
}
