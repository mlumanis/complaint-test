package com.complaint.infrastructure.config;

import com.complaint.application.port.out.GeoLocationPort;
import com.complaint.infrastructure.rest.GeoLocationAdapter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    GeoLocationPort testGeoLocationPort() {
        WebClient webClient = WebClient.create("http://localhost:" + TestConstants.WIREMOCK_PORT);
        return new GeoLocationAdapter(webClient);
    }
}