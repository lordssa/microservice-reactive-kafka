package com.soares.core.usecase.people.impl;

import com.soares.core.entity.People;
import com.soares.core.gateway.IPeopleGateway;
import com.soares.core.usecase.people.AddPeopleUseCase;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class AddPeople implements AddPeopleUseCase {

    private IPeopleGateway peopleGateway;

    @Override
    public Mono<People> execute(People people) {
        return peopleGateway.save(people);
    }
}
