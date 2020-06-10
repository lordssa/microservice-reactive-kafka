package com.soares.core.gateway;

import com.soares.core.entity.People;
import reactor.core.publisher.Mono;

public interface IPeopleGateway extends ICrudGateway<People> {
    //Mono<People> save(People people);
   // Mono<Void> delete(String id);
    Mono<People> getPeopleById(People people);
   // Flux<People> getAllPeople();
   // Mono<People> update(People people);
}
