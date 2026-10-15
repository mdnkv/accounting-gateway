package dev.mednikov.accounting.gateway.services;

import cn.hutool.jwt.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class AuthzTokenServiceImpl implements AuthzTokenService{

    private final byte[] key;

    public AuthzTokenServiceImpl(@Value("${app.signing-key}") String signingKey) {
        this.key = signingKey.getBytes();
    }

    @Override
    public String getAuthzToken(List<String> authorities) {
        return JWT.create().setPayload("authorities", authorities).setKey(this.key).sign();
    }

}
