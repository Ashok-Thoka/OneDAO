package com.onedao.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping({"/", "/login"}) public String login() { return "login"; }
    @GetMapping("/signup") public String signup() { return "signup"; }
    @GetMapping("/dashboard") public String dashboard() { return "dashboard"; }
    @GetMapping("/settings") public String settings() { return "settings"; }
    @GetMapping("/products") public String products() { return "products"; }
    @GetMapping("/error-page") public String errorPage() { return "error"; }
}
