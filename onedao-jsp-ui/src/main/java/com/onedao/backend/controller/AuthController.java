package com.onedao.backend.controller;

import com.onedao.backend.dto.*;
import com.onedao.backend.security.JwtService;
import com.onedao.backend.security.TokenBlacklistService;
import com.onedao.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthController(
            UserService userService,
            JwtService jwtService,
            TokenBlacklistService tokenBlacklistService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse signup(
            @Valid @RequestBody SignupRequest request,
            HttpServletRequest httpRequest) {

        userService.register(request, resolveClientIp(httpRequest));
        return new MessageResponse("User registered successfully");
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        String token = userService.login(request);
        var user = userService.findByUsername(request.username().trim());

        return new AuthResponse(
                token,
                "Bearer",
                jwtService.getExpirationSeconds(),
                user.username(),
                user.userType().name());
    }

    @GetMapping("/error")
    public MessageResponse loginError() { return new MessageResponse("Login Failed"); }

    @PostMapping("/logout")
    public MessageResponse logout(HttpServletRequest request) {
        String token = extractBearerToken(request);

        if (token == null || !jwtService.isValid(token)) {
            throw new IllegalArgumentException("Invalid or missing token");
        }

        String username = jwtService.extractUsername(token);
        tokenBlacklistService.blacklist(token, jwtService.getExpiration(token));

        return new MessageResponse(username + " Logged Out");
    }

    private String resolveClientIp(HttpServletRequest request) {
        // Only trust X-Forwarded-For when your reverse proxy is configured to sanitize it.
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private String extractBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ")
                ? header.substring(7)
                : null;
    }
}
