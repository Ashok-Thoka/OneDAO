package com.onedao.backend.model;

import java.time.Instant;

public record User(
        String username,
        String email,
        String passwordHash,
        UserType userType,
        String country,
        Instant createdAt) {
}
