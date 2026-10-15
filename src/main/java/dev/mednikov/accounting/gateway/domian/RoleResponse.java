package dev.mednikov.accounting.gateway.domian;

import java.util.List;

public record RoleResponse(List<AuthorityResponse> authorities) {
}
