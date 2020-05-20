package com.soares.core.usecase.list;

import com.soares.core.entity.People;
import reactor.core.publisher.Mono;

public interface ListPeopleWithTheirContactsUseCase {
    Mono<People> execute(People people);
}
