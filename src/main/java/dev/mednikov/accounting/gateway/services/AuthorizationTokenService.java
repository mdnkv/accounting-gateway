package dev.mednikov.accounting.gateway.services;

import dev.mednikov.accounting.gateway.domian.CurrentUserRoleResponse;

public interface AuthorizationTokenService {

    String getAuthorizationToken (CurrentUserRoleResponse currentUserRole);

}
