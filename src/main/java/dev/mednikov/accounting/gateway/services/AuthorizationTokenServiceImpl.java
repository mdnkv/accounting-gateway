package dev.mednikov.accounting.gateway.services;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.mednikov.accounting.gateway.domian.CurrentUserRoleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public final class AuthorizationTokenServiceImpl implements AuthorizationTokenService{

    private final static Logger logger = LoggerFactory.getLogger(AuthorizationTokenServiceImpl.class);

    private final byte[] key;

    public AuthorizationTokenServiceImpl(@Value("${app.signing-key}") String signingKey) {
        this.key = signingKey.getBytes();
    }

    @Override
    public String getAuthorizationToken(CurrentUserRoleResponse currentUserRole) {
        try {
            // Create signer
            JWSSigner signer = new MACSigner(key);
            String organizationId = (currentUserRole.organizationId() != null)
                    ? currentUserRole.organizationId().toString()
                    : "";

            // Create claims set
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(currentUserRole.userId().toString())
                    .issuer("http://accounting-gateway")
                    .claim("authorities", currentUserRole.authorities())
                    .claim("organizationId", organizationId)
                    .build();

            // Create signed JWT
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);

            // Sign
            signedJWT.sign(signer);

            // Serialize
            return signedJWT.serialize();
        } catch (Exception ex){
            logger.error(ex.getMessage());
            return "";
        }
    }
}
