package com.onedao.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Set;

@Service
public class GeoLocationService {

    private final RestClient restClient;
    private final Set<String> blockedCountries;
    private final String localCountry;

    public GeoLocationService(
            @Value("${app.geo.base-url}") String baseUrl,
            @Value("${app.security.blocked-countries}") String blockedCountries,
            @Value("${app.geo.local-country:India}") String localCountry) {

        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
        this.localCountry = localCountry;

        this.blockedCountries = Set.of(
                blockedCountries.split(","))
                .stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(s -> !s.isBlank())
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }

    public String resolveCountry(String clientIp) {
        if (isPrivateOrLocal(clientIp)) {
            return localCountry;
        }

        try {
            GeoResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("fields", "status,country,countryCode")
                            .queryParam("query", clientIp)
                            .build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(GeoResponse.class);

            if (response == null || !"success".equalsIgnoreCase(response.status())) {
                return "UNKNOWN";
            }

            return response.country();
        } catch (Exception ex) {
            // Do not silently block a valid user because the geo provider is unavailable.
            // In production, this policy can be changed to fail closed.
            return "UNKNOWN";
        }
    }

    public boolean isBlockedCountry(String country) {
        return country != null && blockedCountries.contains(country.toLowerCase());
    }

    private boolean isPrivateOrLocal(String ip) {
        if (ip == null || ip.isBlank()) return true;
        return ip.equals("127.0.0.1")
                || ip.equals("0:0:0:0:0:0:0:1")
                || ip.equals("::1")
                || ip.startsWith("10.")
                || ip.startsWith("192.168.")
                || ip.startsWith("172.16.")
                || ip.startsWith("172.17.")
                || ip.startsWith("172.18.")
                || ip.startsWith("172.19.")
                || ip.startsWith("172.20.")
                || ip.startsWith("172.21.")
                || ip.startsWith("172.22.")
                || ip.startsWith("172.23.")
                || ip.startsWith("172.24.")
                || ip.startsWith("172.25.")
                || ip.startsWith("172.26.")
                || ip.startsWith("172.27.")
                || ip.startsWith("172.28.")
                || ip.startsWith("172.29.")
                || ip.startsWith("172.30.")
                || ip.startsWith("172.31.");
    }

    private record GeoResponse(String status, String country, String countryCode) {
    }
}
