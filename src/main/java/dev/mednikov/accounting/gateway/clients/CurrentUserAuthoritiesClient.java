package dev.mednikov.accounting.gateway.clients;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface CurrentUserAuthoritiesClient {

    Mono<List<String>> getAuthorities (UUID userId);

}
