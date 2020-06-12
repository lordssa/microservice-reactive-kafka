package com.soares.app.dataprovider.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.cache.CacheFlux;
import reactor.cache.CacheMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.time.Duration;
import java.util.List;

@Service
public class ContactCacheService<Contact> {

    @Autowired
    private ReactiveRedisTemplate<String, Contact> redisTemplate;

    public Mono<Contact> findCacheValue(String cacheName, String keys, Mono<Contact> fallBackMono) {
        return CacheMono
                .lookup(k -> findCacheValue(cacheName, keys).map(Signal::next), keys)
                .onCacheMissResume(fallBackMono)
                .andWriteWith((k, sig) -> Mono.fromRunnable(() ->
                        writeCacheValue(cacheName, keys, sig.get())));
    }

    public Mono<Contact> findCacheValue(String cacheName, String keys) {
        return redisTemplate.opsForValue().get(cacheName + ":" + keys);
    }


    public Mono<Contact> writeCacheValue(String cacheName, String keys, Contact data) {
        if (data != null) {
            redisTemplate.opsForValue().set(cacheName + ":" + keys, data, Duration.ofMinutes(5));
            return Mono.just(data);
        }
        return Mono.empty();
    }

    public Flux<Contact> findCacheValues(String cacheName, String keys, Flux<Contact> fallBackFlux) {
        return CacheFlux
                .lookup(k -> Mono.justOrEmpty(-1)
                        .flatMap(limit -> findCacheValues(cacheName, keys)), keys)
                .onCacheMissResume(fallBackFlux)
                .andWriteWith((k, sig) -> Flux.fromIterable(sig)
                        .dematerialize()
                        .map(list -> (List<Contact>) list)
                        .map(list -> writeCacheValues(cacheName, keys, list))
                        .then()
                );
    }

    public Mono<List<Signal<Contact>>> findCacheValues(String cacheName, String keys) {
        return redisTemplate.opsForList().range(cacheName + ":" + keys, 0, -1)
                .materialize()
                .collectList();
    }

    public Flux<Contact> writeCacheValues(String cacheName, String keys, List<Contact> data) {
        redisTemplate.opsForList().rightPushAll(cacheName + ":" + keys, data);
        return Flux.fromIterable(data);
    }

    public void evictValue(String cacheName, String keys) {
        redisTemplate.opsForValue().delete(cacheName + ":" + keys);
    }

}