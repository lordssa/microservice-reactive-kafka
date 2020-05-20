package com.soares.core.usecase.people.impl;

import com.soares.core.entity.People;
import com.soares.core.gateway.IPeopleGateway;
import com.soares.core.usecase.people.GetAllPeopleUseCase;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class GetAllPeople implements GetAllPeopleUseCase {

    private IPeopleGateway peopleGateway;

    @Override
    public Flux<People> execute() {
        return peopleGateway.getAllPeople();
    }
}
