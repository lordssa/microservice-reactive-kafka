package com.soares.app.dataprovider;

import com.soares.app.dataprovider.integration.PeopleService;
import com.soares.app.dataprovider.integration.adapter.PeopleIntegrationResourceToPeopleConverter;
import com.soares.app.dataprovider.integration.adapter.PeopleToPeopleIntegrationResourceConverter;
import com.soares.app.dataprovider.integration.resource.PeopleIntegrationResource;
import com.soares.core.entity.People;
import com.soares.core.gateway.IPeopleGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PeopleGateway implements IPeopleGateway {
    private PeopleService peopleService;
    private PeopleIntegrationResourceToPeopleConverter peopleIntegrationResourceToPeopleConverter;
    private PeopleToPeopleIntegrationResourceConverter peopleToPeopleIntegrationResourceConverter;

    @Override
    public Mono<People> save(People people) {
        return null;
    }

    @Override
    public Mono<Void> delete(String id) {
        return null;
    }

    @Override
    public Mono<People> getPeopleById(People people) {
        return Mono.just(people)
                .map(peopleToPeopleIntegrationResourceConverter::convert)
                .map(PeopleIntegrationResource::getId)
                .flatMap(peopleService::getPeople)
                .map(peopleIntegrationResourceToPeopleConverter::convert)
                .defaultIfEmpty(People.builder().build());
    }

    @Override
    public Flux<People> getAllPeople() {
        return null;
    }

    @Override
    public Mono<People> update(People people) {
        return null;
    }
}
