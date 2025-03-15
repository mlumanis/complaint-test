package com.complaint.infrastructure.rest;

import com.complaint.application.port.out.GeoLocationPort;
import com.complaint.domain.model.Country;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GeoLocationAdapter implements GeoLocationPort {
    
    private final WebClient webClient;

    public GeoLocationAdapter() {
        this(WebClient.create("http://ip-api.com"));
    }

    public GeoLocationAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Country getCountryFromIp(String ipAddress) {
        try {
            GeoLocationResponse response = webClient.get()
                    .uri("/json/" + ipAddress)
                    .retrieve()
                    .bodyToMono(GeoLocationResponse.class)
                    .block();

            if (response != null && "success".equals(response.status())) {
                return Country.of(response.country());
            }
            return Country.unknown();
        } catch (Exception e) {
            return Country.unknown();
        }
    }

    private record GeoLocationResponse(
            String status,
            String country,
            String countryCode
    ) {}
} 