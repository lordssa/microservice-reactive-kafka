package com.soares.core.usecase.contact.impl;

import com.soares.core.gateway.IContactGateway;
import com.soares.core.usecase.contact.DeleteContactUseCase;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@AllArgsConstructor(onConstructor = @__(@Inject))
public class DeleteContact implements DeleteContactUseCase {

    private IContactGateway contactGateway;

    @Override
    public Mono<Void> execute(String id) {
        return contactGateway.delete(id);
    }
}
