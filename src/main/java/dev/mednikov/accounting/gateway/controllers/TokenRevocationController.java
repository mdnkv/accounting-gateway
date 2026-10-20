package dev.mednikov.accounting.gateway.controllers;

import dev.mednikov.accounting.gateway.services.CurrentUserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TokenRevocationController {

    private final CurrentUserRoleService currentUserRoleService;

    public TokenRevocationController(CurrentUserRoleService currentUserRoleService) {
        this.currentUserRoleService = currentUserRoleService;
    }

    @PostMapping("/revoke-context/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> revokeUserContext(@PathVariable String userId){
        return this.currentUserRoleService.revokeContext(userId);
    }

}
