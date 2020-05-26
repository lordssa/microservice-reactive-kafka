package com.soares.app.dataprovider;

import com.soares.app.dataprovider.integration.ContactService;
import com.soares.app.dataprovider.integration.adapter.ContactIntegrationResourceToContactConverter;
import com.soares.app.dataprovider.integration.adapter.ContactToContactIntegrationResourceConverter;
import com.soares.app.dataprovider.integration.resource.ContactIntegrationResource;
import com.soares.core.entity.Contact;
import com.soares.core.gateway.IContactGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ContactGateway implements IContactGateway {
    private ContactService contactService;
    private ContactToContactIntegrationResourceConverter contactToContactIntegrationResourceConverter;
    private ContactIntegrationResourceToContactConverter contactIntegrationResourceToContactConverter;

    @Override
    public Mono<Contact> save(Contact contact) {
        return null;
    }

    @Override
    public Mono<Void> delete(String id) {
        return null;
    }

    @Override
    public Mono<Contact> getContactById(Contact contact) {
        return null;
    }

    @Override
    public Flux<Contact> getContactsByPeople(Contact contact) {
        return Flux.just(contact)
                .map(contactToContactIntegrationResourceConverter::convert)
                .map(ContactIntegrationResource::getPeopleId)
                .flatMap(contactService::getContactsByPeople)
                .map(contactIntegrationResourceToContactConverter::convert)
                .defaultIfEmpty(Contact.builder().build());
    }

    @Override
    public Flux<Contact> getAllContact() {
        return null;
    }

    @Override
    public Mono<Contact> update(Contact contact) {
        return null;
    }
}
