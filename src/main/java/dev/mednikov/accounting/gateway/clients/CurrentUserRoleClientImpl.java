package dev.mednikov.accounting.gateway.clients;

import dev.mednikov.accounting.gateway.config.RouteLocationProperties;
import dev.mednikov.accounting.gateway.domain.CurrentUserRoleResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public final  class CurrentUserRoleClientImpl implements CurrentUserRoleClient{

    private final WebClient webClient;

    public CurrentUserRoleClientImpl(RouteLocationProperties routeLocation){
        this.webClient = WebClient.builder().baseUrl(routeLocation.roleServiceLocation()).build();
    }

    @Override
    public Mono<CurrentUserRoleResponse> getCurrentUserRole(String userId) {
        return this.webClient.get()
                .uri("/user-roles/current/{userId}", userId)
                .retrieve()
                .bodyToMono(CurrentUserRoleResponse.class);
    }
}
