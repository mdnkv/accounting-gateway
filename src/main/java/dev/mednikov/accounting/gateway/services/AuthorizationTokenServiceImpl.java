package dev.mednikov.accounting.gateway.services;

import cn.hutool.jwt.JWT;
import dev.mednikov.accounting.gateway.domian.CurrentUserRoleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class AuthorizationTokenServiceImpl implements AuthorizationTokenService{

    private final byte[] key;

    public AuthorizationTokenServiceImpl(@Value("${app.signing-key}") String signingKey) {
        this.key = signingKey.getBytes();
    }

    @Override
    public String getAuthorizationToken(CurrentUserRoleResponse currentUserRole) {
        return JWT.create()
                .setSubject(currentUserRole.userId().toString())
                .setPayload("authorities", currentUserRole.authorities())
                .setPayload("organizationId", currentUserRole.organizationId().toString())
                .setKey(this.key)
                .sign();
    }
}
