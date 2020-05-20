package com.soares.core.usecase.people;

import com.soares.core.entity.People;
import reactor.core.publisher.Mono;

public interface AddPeopleUseCase {
    Mono<People> execute(People people);
}
