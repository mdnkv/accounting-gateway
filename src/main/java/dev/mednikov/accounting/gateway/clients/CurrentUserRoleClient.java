package dev.mednikov.accounting.gateway.clients;

import dev.mednikov.accounting.gateway.domian.CurrentUserRoleResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CurrentUserRoleClient {

    Mono<CurrentUserRoleResponse> getCurrentUserRole (UUID userId);

}
