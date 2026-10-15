package dev.mednikov.accounting.gateway.domian;

import java.util.List;
import java.util.UUID;

public record CurrentUserRoleResponse(
        UUID id,
        UUID organizationId,
        RoleResponse role
) {
}
