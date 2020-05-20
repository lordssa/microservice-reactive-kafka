package com.soares.core.usecase.people;

import com.soares.core.entity.People;
import reactor.core.publisher.Flux;

public interface GetAllPeopleUseCase {
    Flux<People> execute();
}
