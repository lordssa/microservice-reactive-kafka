package com.soares.core.gateway;

import com.soares.core.entity.Contact;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IContactGateway {
    Mono<Contact> save(Contact contact);
    Mono<Void> delete(String id);
    Mono<Contact> getContactById(Contact contact);
    Flux<Contact> getContactsByPeople(Contact contact);
    Flux<Contact> getAllContact();
    Mono<Contact> update(Contact contact);
}
