package com.soares.core.usecase.people;

import reactor.core.publisher.Mono;

public interface DeletePeopleUseCase {
    Mono<Void> execute(String id);
}
