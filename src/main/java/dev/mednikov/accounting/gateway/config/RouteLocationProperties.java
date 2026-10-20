package dev.mednikov.accounting.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.locations")
public record RouteLocationProperties(
        String organizationServiceLocation,
        String ledgerServiceLocation,
        String roleServiceLocation
) {
}
