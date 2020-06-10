package com.soares.core.usecase.contact.impl;

import com.soares.core.entity.Contact;
import com.soares.core.gateway.IContactGateway;
import com.soares.core.usecase.contact.GetAllContactUseCase;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class GetAllContact implements GetAllContactUseCase {

    private IContactGateway contactGateway;

    @Override
    public Flux<Contact> execute() {
        return contactGateway.getAll();
    }
}
