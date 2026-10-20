package dev.mednikov.accounting.gateway.services;

import dev.mednikov.accounting.gateway.domain.CurrentUserRoleResponse;
import reactor.core.publisher.Mono;

public interface CurrentUserRoleService {

    Mono<CurrentUserRoleResponse> getCurrentUserRole (String userId);

    Mono<Void> revokeContext (String userId);


}
