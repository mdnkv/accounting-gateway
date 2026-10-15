package dev.mednikov.accounting.gateway.clients;

import dev.mednikov.accounting.gateway.domian.AuthorityResponse;
import dev.mednikov.accounting.gateway.domian.CurrentUserRoleResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public final class CurrentUserAuthoritiesClientImpl implements CurrentUserAuthoritiesClient {

    private final WebClient webClient;

    public CurrentUserAuthoritiesClientImpl() {
        this.webClient = WebClient.create();
    }

    @Override
    public Mono<List<String>> getAuthorities (UUID userId) {
        return this.webClient.get()
                .uri("http://accounting-authz/user-roles/current/{userId}", userId)
                .retrieve()
                .bodyToMono(CurrentUserRoleResponse.class)
                .map(result -> {
                    List<String> permissions = new ArrayList<>();
                    permissions.add(result.organizationId().toString());
                    for (AuthorityResponse authorityResponse:result.role().authorities()){
                        permissions.add(authorityResponse.name());
                    }
                    return permissions;
                })
                .onErrorReturn(List.of());
    }

}
