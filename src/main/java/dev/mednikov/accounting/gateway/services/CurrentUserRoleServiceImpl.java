package dev.mednikov.accounting.gateway.services;

import dev.mednikov.accounting.gateway.clients.CurrentUserRoleClient;
import dev.mednikov.accounting.gateway.domain.CurrentUserRoleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class CurrentUserRoleServiceImpl implements CurrentUserRoleService{

    private final static Logger logger = LoggerFactory.getLogger(CurrentUserRoleServiceImpl.class);
    private final static Duration TTL_DURATION = Duration.ofSeconds(3L);

    private final CurrentUserRoleClient client;
    private final ReactiveRedisTemplate<String, CurrentUserRoleResponse> redisTemplate;

    public CurrentUserRoleServiceImpl(CurrentUserRoleClient client, ReactiveRedisTemplate<String, CurrentUserRoleResponse> redisTemplate) {
        this.client = client;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Void> revokeContext(String userId) {
        return this.redisTemplate.opsForValue().delete(userId).flatMap(result -> {
            logger.info("Current context revoked for user {}: {}", userId, result.toString());
            return Mono.empty();
        });
    }

    @Override
    public Mono<CurrentUserRoleResponse> getCurrentUserRole(String userId) {
        logger.info("Fetching active context for user {}", userId);
        return this.redisTemplate
                .opsForValue()
                .get(userId)
                .switchIfEmpty(callAuthzService(userId).flatMap(result -> cacheAndReturn(userId, result)));
    }

    Mono<CurrentUserRoleResponse> callAuthzService (String userId){
        logger.info("Context is not available, calling external API");
        return this.client.getCurrentUserRole(userId);
    }

    Mono<CurrentUserRoleResponse> cacheAndReturn(String userId, CurrentUserRoleResponse data){
        logger.info("Cache and return for user {}", userId);
        return redisTemplate.opsForValue().set(userId, data, TTL_DURATION).thenReturn(data);
    }


}
