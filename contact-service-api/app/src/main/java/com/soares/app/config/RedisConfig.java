package com.soares.app.config;

import com.soares.app.dataprovider.repository.model.ContactDB;
import com.soares.app.dataprovider.repository.model.TypeContactDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveKeyCommands;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.ReactiveStringCommands;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration
import javax.annotation.PreDestroy;

@Configuration
public class RedisConfig {

    @Autowired
    RedisConnectionFactory factory;



    @Bean
    @Primary
    public ReactiveRedisTemplate<String, ContactDB> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<ContactDB> serializer = new Jackson2JsonRedisSerializer<>(ContactDB.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, ContactDB> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, ContactDB> context = builder.value(serializer)
            .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

   /* @Bean
    public ReactiveRedisTemplate<String, TypeContactDB> reactiveRedisTemplateEnum(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<TypeContactDB> serializer = new Jackson2JsonRedisSerializer<>(TypeContactDB.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, TypeContactDB> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, TypeContactDB> context = builder.value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }*/

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplateString(ReactiveRedisConnectionFactory connectionFactory) {
        return new ReactiveRedisTemplate<>(connectionFactory, RedisSerializationContext.string());
    }

    @Bean
    public ReactiveKeyCommands keyCommands(final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return reactiveRedisConnectionFactory.getReactiveConnection()
            .keyCommands();
    }

    @Bean
    public ReactiveStringCommands stringCommands(final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        return reactiveRedisConnectionFactory.getReactiveConnection()
            .stringCommands();
    }

    @PreDestroy
    public void cleanRedis() {
        factory.getConnection()
            .flushDb();
    }
}