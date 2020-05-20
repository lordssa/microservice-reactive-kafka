package com.soares.core.usecase.people.impl;

import com.soares.core.gateway.IPeopleGateway;
import com.soares.core.usecase.people.DeletePeopleUseCase;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@AllArgsConstructor(onConstructor = @__(@Inject))
public class DeletePeople implements DeletePeopleUseCase {

    private IPeopleGateway peopleGateway;

    @Override
    public Mono<Void> execute(String id) {
        return peopleGateway.delete(id);
    }
}
