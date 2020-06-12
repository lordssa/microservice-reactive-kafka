package com.soares.app.dataprovider.cache;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.cache.CacheMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.List;

@Service
@AllArgsConstructor
public class BaseService<E> {

    @Autowired
    protected HazelcastService hazlecastService;


    public Mono<E> findCacheValue(String cacheName, String keys, Mono<E> fallBackMono) {
        return CacheMono
                .lookup(k -> findCacheValue(cacheName, keys, fallBackMono).map(Signal::next), keys)
                .onCacheMissResume(fallBackMono)
                .andWriteWith((k, sig) -> Mono.fromRunnable(() ->
                        writeCacheValue(cacheName, keys, sig.get())));
    }


    public Mono<E> writeCacheValue(String cacheName, String keys, E data) {
        if (data != null) {
            hazlecastService.getHzInstance().getMap(cacheName).set(keys, data);
            return Mono.just(data);
        }
        return Mono.empty();
    }

    public Flux<E> writeCacheValues(String cacheName, String keys, List<E> data) {
        hazlecastService.getHzInstance().getMap(cacheName).set(keys, data);
        return Flux.fromIterable(data);
    }

    public void evictValue(String cacheName, String keys) {
        hazlecastService.getHzInstance().getMap(cacheName).evict(keys);
    }

}