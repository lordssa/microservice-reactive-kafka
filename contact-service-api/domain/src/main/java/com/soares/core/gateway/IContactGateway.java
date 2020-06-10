package com.soares.core.gateway;

import com.soares.core.entity.Contact;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IContactGateway extends ICrudGateway<Contact> {
   // Mono<Contact> save(Contact contact);
  //  Mono<Void> delete(String id);
    Mono<Contact> getContactById(Contact contact);
    Flux<Contact> getContactsByPeople(Contact contact);
   // Mono<Contact> update(Contact contact);
}
