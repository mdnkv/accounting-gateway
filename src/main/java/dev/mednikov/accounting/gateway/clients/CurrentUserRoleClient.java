package dev.mednikov.accounting.gateway.clients;

import dev.mednikov.accounting.gateway.domain.CurrentUserRoleResponse;
import reactor.core.publisher.Mono;

public interface CurrentUserRoleClient {

    Mono<CurrentUserRoleResponse> getCurrentUserRole (String userId);

}
