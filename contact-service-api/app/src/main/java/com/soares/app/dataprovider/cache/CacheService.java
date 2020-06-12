package com.soares.app.dataprovider.cache;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.cache.CacheFlux;
import reactor.cache.CacheMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.time.Duration;
import java.util.List;

@Service
public class CacheService<E> {

    @Autowired
    private ReactiveRedisTemplate<String, E> redisTemplate;

    public Mono<E> findCacheValue(String cacheName, String keys, Mono<E> fallBackMono) {
        return CacheMono
                .lookup(k -> findCacheValue(cacheName, keys).map(Signal::next), keys)
                .onCacheMissResume(fallBackMono)
                .andWriteWith((k, sig) -> Mono.fromRunnable(() ->
                        writeCacheValue(cacheName, keys, sig.get())));
    }

    public Mono<E> findCacheValue(String cacheName, String keys) {
        return redisTemplate.opsForValue().get(cacheName + ":" + keys);
    }


    public Mono<E> writeCacheValue(String cacheName, String keys, E data) {
        System.out.println("writeCacheValue");
        if (data != null) {
            System.out.println(data);
            return //redisTemplate.opsForValue().set(cacheName + ":" + keys, data)
                    redisTemplate.opsForValue().set(keys, data)
                    .filter(Boolean::booleanValue)
                    .map(b -> data);
            //return Mono.just(data);
        }
        return Mono.empty();
    }

    public Flux<E> findCacheValues(String cacheName, String keys, Flux<E> fallBackFlux) {
        return CacheFlux
                .lookup(k -> Mono.justOrEmpty(-1)
                        .flatMap(limit -> findCacheValues(cacheName, keys)), keys)
                .onCacheMissResume(fallBackFlux)
                .andWriteWith((k, sig) -> Flux.fromIterable(sig)
                        .dematerialize()
                        .map(list -> (List<E>) list)
                        .map(list -> writeCacheValues(cacheName, keys, list))
                        .then()
                );
    }

    public Mono<List<Signal<E>>> findCacheValues(String cacheName, String keys) {
        return redisTemplate.opsForList().range(cacheName + ":" + keys, 0, -1)
                .materialize()
                .collectList();
    }

    public Flux<E> writeCacheValues(String cacheName, String keys, List<E> data) {
        redisTemplate.opsForList().rightPushAll(cacheName + ":" + keys, data);
        return Flux.fromIterable(data);
    }

    public void evictValue(String cacheName, String keys) {
        redisTemplate.opsForValue().delete(cacheName + ":" + keys);
    }

}