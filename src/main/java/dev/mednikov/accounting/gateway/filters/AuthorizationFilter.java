package dev.mednikov.accounting.gateway.filters;

import dev.mednikov.accounting.gateway.clients.CurrentUserRoleClient;
import dev.mednikov.accounting.gateway.services.AuthorizationTokenService;
import dev.mednikov.accounting.gateway.services.CurrentUserRoleService;
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

@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    private final CurrentUserRoleService currentUserRoleService;
    private final AuthorizationTokenService authorizationTokenService;

    public AuthorizationFilter(CurrentUserRoleService currentUserRoleService, AuthorizationTokenService authorizationTokenService) {
        this.currentUserRoleService = currentUserRoleService;
        this.authorizationTokenService = authorizationTokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return exchange.getPrincipal()
                .ofType(JwtAuthenticationToken.class)
                .map(auth -> Objects.requireNonNull(auth.getToken().getSubject()))
                .filter(subject -> !subject.isBlank())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)))
                .flatMap(currentUserRoleService::getCurrentUserRole)
                .flatMap(currentUserRole -> {
                    String authorizationToken = this.authorizationTokenService.getAuthorizationToken(currentUserRole);
                    var mutatedRequest = exchange.getRequest().mutate()
                            .headers(headers -> {
                                headers.remove(HttpHeaders.AUTHORIZATION);
                                headers.remove("X-APP-AUTHORIZATION"); // sanity check
                                headers.set("X-APP-AUTHORIZATION", authorizationToken);
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
