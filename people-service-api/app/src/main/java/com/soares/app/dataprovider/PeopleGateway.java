package com.soares.app.dataprovider;

import com.soares.app.dataprovider.repository.PeopleRepository;
import com.soares.app.dataprovider.repository.adapter.PeopleDBToPeopleConverter;
import com.soares.app.dataprovider.repository.adapter.PeopleToPeopleDBConverter;
import com.soares.app.dataprovider.repository.model.PeopleDB;
import com.soares.core.entity.People;
import com.soares.core.gateway.IPeopleGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PeopleGateway implements IPeopleGateway {
    private PeopleRepository peopleRepository;
    private PeopleToPeopleDBConverter peopleToPeopleDBConverter;
    private PeopleDBToPeopleConverter peopleDBToPeopleConverter;

    @Override
    public Mono<People> save(People people) {
        return Mono.just(people)
                .map(peopleToPeopleDBConverter::convert)
                .flatMap(peopleDB -> peopleRepository.insert(peopleDB))
                .map(peopleDB -> peopleDBToPeopleConverter.convert(peopleDB))
                .defaultIfEmpty(People.builder().build());
    }

    @Override
    public Mono<Void> delete(String id) {
        return peopleRepository.deleteById(id);
    }

    @Override
    public Mono<People> getPeopleById(People people) {
        return Mono.just(people)
                .map(peopleToPeopleDBConverter::convert)
                .map(PeopleDB::getId)
                .flatMap(peopleDB -> peopleRepository.findById(peopleDB))
                .map(peopleDBToPeopleConverter::convert)
                .defaultIfEmpty(People.builder().build());
    }

    @Override
    public Flux<People> getAll() {
        return peopleRepository.findAll()
                .map(peopleDBToPeopleConverter::convert);
    }

    @Override
    public Mono<People> update(People people) {
        return Mono.just(people)
                .map(peopleToPeopleDBConverter::convert)
                .flatMap(peopleDB -> peopleRepository.save(peopleDB))
                .map(peopleDBToPeopleConverter::convert)
                .defaultIfEmpty(People.builder().build());
    }
}
