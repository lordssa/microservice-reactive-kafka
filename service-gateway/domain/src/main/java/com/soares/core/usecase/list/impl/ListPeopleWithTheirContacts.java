package com.soares.core.usecase.people.impl;

import com.soares.core.entity.Contact;
import com.soares.core.entity.People;
import com.soares.core.gateway.IContactGateway;
import com.soares.core.gateway.IPeopleGateway;
import com.soares.core.usecase.list.ListPeopleWithTheirContactsUseCase;
import com.soares.core.usecase.people.GetPeopleUseCase;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class ListPeopleWithTheirContacts implements ListPeopleWithTheirContactsUseCase {

    private IPeopleGateway peopleGateway;
    private IContactGateway contactGateway;

    @Override
    public Mono<People> execute(final People people) {
        return peopleGateway.getPeopleById(people)
                .flatMap(this::getContacts);
    }

    private Mono<People> getContacts(final People people) {
        final var contact = Contact
                .builder()
                .peopleId(people.getId())
                .build();

        return contactGateway.getContactsByPeople(contact)
                .collectList()
                .map(listContacts -> people.toBuilder()
                        .contacts(listContacts)
                        .build());
    }
}
