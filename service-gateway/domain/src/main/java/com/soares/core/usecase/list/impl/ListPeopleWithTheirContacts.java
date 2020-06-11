package com.soares.core.usecase.list.impl;

import com.soares.core.entity.Contact;
import com.soares.core.entity.People;
import com.soares.core.gateway.IContactGateway;
import com.soares.core.gateway.IPeopleGateway;
import com.soares.core.usecase.list.ListPeopleWithTheirContactsUseCase;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class ListPeopleWithTheirContacts implements ListPeopleWithTheirContactsUseCase {

    private IPeopleGateway peopleGateway;
    private IContactGateway contactGateway;

    @Override
    public Mono<People> execute(final People people) {
        final var monoContact = Mono.fromCallable(()->{
            final var contact = Contact
                    .builder()
                    .peopleId(people.getId())
                    .build();
            return contactGateway.getContactsByPeople(contact);
        }).subscribeOn(Schedulers.parallel());

        final var monoPeople = peopleGateway
                .getPeopleById(people)
                .subscribeOn(Schedulers.parallel());

        return Mono.zip(monoPeople, monoContact)
                .flatMap(tuple -> generatePeopleWithContacts(tuple.getT1(), tuple.getT2()));

    }

    private Mono<People> generatePeopleWithContacts(People person, Flux<Contact> contacts) {
        return contacts
                .collectList()
                .map(listContacts -> getBuild(listContacts, person));
    }

    private People getBuild(List<Contact> listContacts, People people) {
        return people.toBuilder()
                .contacts(listContacts)
                .build();
    }
}
