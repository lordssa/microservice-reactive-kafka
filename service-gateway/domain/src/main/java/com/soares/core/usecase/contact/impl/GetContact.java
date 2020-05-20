package com.soares.core.usecase.contact.impl;

import com.soares.core.entity.Contact;
import com.soares.core.gateway.IContactGateway;
import com.soares.core.usecase.contact.GetContactUseCase;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class GetContact implements GetContactUseCase {

    private IContactGateway contactGateway;

    @Override
    public Mono<Contact> execute(Contact contact) {
        return contactGateway.getContactById(contact);
    }
}
