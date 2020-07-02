package io.x22x22.docker.learn.shoppingcartserver.config;

import io.x22x22.docker.learn.shoppingcartserver.entities.Goods;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
class RedisConfiguration {
    @Bean
    ReactiveRedisOperations<String, Goods> goodsRedisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Goods> serializer = new Jackson2JsonRedisSerializer<>(Goods.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Goods> builder =
            RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, Goods> context = builder.value(serializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
}
