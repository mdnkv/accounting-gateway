package dev.mednikov.accounting.gateway.config;

import dev.mednikov.accounting.gateway.domain.CurrentUserRoleResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {


    @Bean
    public ReactiveRedisTemplate<String, CurrentUserRoleResponse> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        RedisSerializer<CurrentUserRoleResponse> valueSerializer = new JacksonJsonRedisSerializer<>(CurrentUserRoleResponse.class);
        RedisSerializationContext<String, CurrentUserRoleResponse> context = RedisSerializationContext
                        .<String, CurrentUserRoleResponse>newSerializationContext(keySerializer)
                        .value(valueSerializer)
                        .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

}
