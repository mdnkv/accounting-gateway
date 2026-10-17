package dev.mednikov.accounting.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.locations")
public record RouteLocation(
        String organizationServiceLocation,
        String ledgerServiceLocation,
        String roleServiceLocation
) {
}
