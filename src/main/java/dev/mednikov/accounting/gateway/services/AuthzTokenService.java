package dev.mednikov.accounting.gateway.services;

import dev.mednikov.accounting.gateway.domian.CurrentUserRoleResponse;

import java.util.List;

public interface AuthzTokenService {

    String getAuthzToken (List<String> permissions);

}
