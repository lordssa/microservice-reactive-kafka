package com.soares.core.usecase.contact.impl;

import com.soares.core.entity.Contact;
import com.soares.core.gateway.IContactGateway;
import com.soares.core.usecase.contact.AddContactUseCase;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class AddContact implements AddContactUseCase {

    private IContactGateway contactGateway;

    @Override
    public Mono<Contact> execute(Contact contact) {
        return contactGateway.save(contact);
    }
}
