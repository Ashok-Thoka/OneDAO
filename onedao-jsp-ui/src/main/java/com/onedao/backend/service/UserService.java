package com.onedao.backend.service;

import com.onedao.backend.dto.LoginRequest;
import com.onedao.backend.dto.SignupRequest;
import com.onedao.backend.model.User;
import com.onedao.backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final Map<String, User> usersByUsername = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final GeoLocationService geoLocationService;

    public UserService(
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            GeoLocationService geoLocationService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.geoLocationService = geoLocationService;
    }

    public void register(SignupRequest request, String clientIp) {
        String username = request.username().trim();
        String email = request.email().trim().toLowerCase();

        String country = geoLocationService.resolveCountry(clientIp);

        // Do not bypass country restrictions when the Geo-IP provider cannot verify the location.
        if (country == null || "UNKNOWN".equalsIgnoreCase(country)) {
            throw new IllegalArgumentException("Unable to verify your country. Please try again.");
        }

        if (geoLocationService.isBlockedCountry(country)) {
            throw new IllegalArgumentException("Registration is not allowed from your country");
        }

        if (usersByUsername.containsKey(username.toLowerCase())) {
            throw new IllegalArgumentException("Username already exists");
        }

        boolean emailExists = usersByUsername.values().stream()
                .anyMatch(user -> user.email().equalsIgnoreCase(email));

        if (emailExists) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(
                username,
                email,
                passwordEncoder.encode(request.password()),
                request.userType(),
                country,
                Instant.now());

        usersByUsername.put(username.toLowerCase(), user);
    }

    public String login(LoginRequest request) {
        User user = usersByUsername.get(request.username().trim().toLowerCase());

        if (user == null || !passwordEncoder.matches(request.password(), user.passwordHash())) {
            throw new IllegalArgumentException("Login Failed");
        }

        return jwtService.generateToken(user.username(), user.userType().name());
    }

    public User findByUsername(String username) {
        User user = usersByUsername.get(username.toLowerCase());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }
}
