package com.soares.core.gateway;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICrudGateway<T> {
    Mono<T> save(T object);
    Mono<Void> delete(String id);
    Flux<T> getAll();
    Mono<T> update(T object);
}
