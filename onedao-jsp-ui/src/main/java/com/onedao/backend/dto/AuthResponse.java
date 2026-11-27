package com.onedao.backend.dto;

public record AuthResponse(
        String token,
        String tokenType,
        long expiresInSeconds,
        String username,
        String userType) {
}
