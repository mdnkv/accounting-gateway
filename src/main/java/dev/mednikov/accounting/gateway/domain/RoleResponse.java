package dev.mednikov.accounting.gateway.domain;

import java.util.List;

public record RoleResponse(List<AuthorityResponse> authorities) {
}
