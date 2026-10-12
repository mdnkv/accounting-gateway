package dev.mednikov.accounting.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    @Bean
    public RouteLocator routeLocator (RouteLocatorBuilder builder){
        return builder.routes()
                .route("organizations", r->r.path("/organizations/**").uri("http://localhost:8001"))
                .route("accounts", r->r.path("/accounts/**").uri("http://localhost:8002"))
                .route("account-categories", r->r.path("/account-categories/**").uri("http://localhost:8002"))
                .route("journals", r->r.path("/journals/**").uri("http://localhost:8002"))
                .route("currencies", r->r.path("/currencies/**").uri("http://localhost:8002"))
                .route("user-roles", r->r.path("/user-roles/**").uri("http://localhost:8003"))
                .build();
    }

}
