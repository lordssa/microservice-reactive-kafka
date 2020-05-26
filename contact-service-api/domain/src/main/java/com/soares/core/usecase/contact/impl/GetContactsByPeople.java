package com.soares.core.usecase.contact.impl;

import com.soares.core.entity.Contact;
import com.soares.core.gateway.IContactGateway;
import com.soares.core.usecase.contact.GetContactsByPeopleUseCase;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class GetContactsByPeople implements GetContactsByPeopleUseCase {

    private IContactGateway contactGateway;

    @Override
    public Flux<Contact> execute(Contact contact) {
        return contactGateway.getContactsByPeople(contact);
    }
}
