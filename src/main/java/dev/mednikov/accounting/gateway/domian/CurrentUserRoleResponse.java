package dev.mednikov.accounting.gateway.domian;

import java.util.List;
import java.util.UUID;

public record CurrentUserRoleResponse(
        UUID userId,
        boolean hasRole,
        UUID organizationId,
        String roleName,
        List<String> authorities
) {
}
