package dev.mednikov.accounting.gateway.filters;

import dev.mednikov.accounting.gateway.clients.CurrentUserAuthoritiesClient;
import dev.mednikov.accounting.gateway.services.AuthzTokenService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    private final CurrentUserAuthoritiesClient authoritiesClient;
    private final AuthzTokenService authzTokenService;

    public AuthorizationFilter(CurrentUserAuthoritiesClient authoritiesClient, AuthzTokenService authzTokenService) {
        this.authoritiesClient = authoritiesClient;
        this.authzTokenService = authzTokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return exchange.getPrincipal()
                .ofType(JwtAuthenticationToken.class)
                .map(auth -> Objects.requireNonNull(auth.getToken().getSubject()))
                .filter(subject -> !subject.isBlank())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)))
                .flatMap(subject -> {
                    UUID userId = UUID.fromString(subject);
                    return authoritiesClient.getAuthorities(userId);
                })
                .flatMap(authorities -> {
                    String authzToken = authzTokenService.getAuthzToken(authorities);
                    var mutatedRequest = exchange.getRequest().mutate()
                            .headers(headers -> {
                                headers.remove(HttpHeaders.AUTHORIZATION);
                                headers.set("X-APP-AUTHZ-TOKEN", authzToken);
                            }).build();
                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                    return chain.filter(mutatedExchange);
                });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
