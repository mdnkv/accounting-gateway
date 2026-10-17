package dev.mednikov.accounting.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    private final RouteLocation routeLocation;

    public RoutesConfig(RouteLocation routeLocation) {
        this.routeLocation = routeLocation;
    }

    @Bean
    public RouteLocator routeLocator (RouteLocatorBuilder builder){
        return builder.routes()
                .route("organizations", r->r.path("/organizations/**").uri(routeLocation.organizationServiceLocation()))
                .route("accounts", r->r.path("/accounts/**").uri(routeLocation.ledgerServiceLocation()))
                .route("account-categories", r->r.path("/account-categories/**").uri(routeLocation.ledgerServiceLocation()))
                .route("journals", r->r.path("/journals/**").uri(routeLocation.ledgerServiceLocation()))
                .route("currencies", r->r.path("/currencies/**").uri(routeLocation.ledgerServiceLocation()))
                .route("user-roles", r->r.path("/user-roles/**").uri(routeLocation.roleServiceLocation()))
                .route("roles", r->r.path("/roles/**").uri(routeLocation.roleServiceLocation()))
                .route("authorities", r->r.path("/authorities/**").uri(routeLocation.roleServiceLocation()))
                .build();
    }

}
