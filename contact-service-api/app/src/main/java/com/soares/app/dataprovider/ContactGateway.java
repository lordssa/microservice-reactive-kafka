package com.soares.app.dataprovider;

import com.soares.app.dataprovider.cache.CacheService;
import com.soares.app.dataprovider.repository.ContactRepository;
import com.soares.app.dataprovider.repository.adapter.ContactDBToContactConverter;
import com.soares.app.dataprovider.repository.adapter.ContactToContactDBConverter;
import com.soares.app.dataprovider.repository.model.ContactDB;
import com.soares.core.entity.Contact;
import com.soares.core.gateway.IContactGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ContactGateway extends CacheService<ContactDB> implements IContactGateway {
    private ContactRepository contactRepository;
    private ContactToContactDBConverter contactToContactDBConverter;
    private ContactDBToContactConverter contactDBToContactConverter;

    private final static String CACHE_NAME = "ContactGateway";

    @Override
    public Mono<Contact> save(Contact contact) {
        return Mono.just(contact)
                .map(contactToContactDBConverter::convert)
                .flatMap(contactDB -> contactRepository.insert(contactDB))
                .map(contactDB -> contactDBToContactConverter.convert(contactDB))
                .defaultIfEmpty(Contact.builder().build());
    }

    @Override
    public Mono<Void> delete(String id) {
        return contactRepository.deleteById(id)
        .doOnNext(aVoid -> evictValue(CACHE_NAME,id));
    }

    @Override
    public Mono<Contact> getContactById(Contact contact) {
        return Mono.just(contact)
                .map(contactToContactDBConverter::convert)
                .map(ContactDB::getId)
                .flatMap(idContactDB ->
                        findCacheValue(CACHE_NAME, idContactDB, contactRepository.findById(idContactDB)))
                .map(contactDBToContactConverter::convert)
                .defaultIfEmpty(Contact.builder().build());
    }

    @Override
    public Flux<Contact> getContactsByPeople(Contact contact) {
        System.out.println(contact);
        return Flux.just(contact)
                .map(contactToContactDBConverter::convert)
                .map(ContactDB::getPeopleId)
                .flatMap(peopleId ->
                        findCacheValues(CACHE_NAME, peopleId, contactRepository.findAllByPeopleId(peopleId)))
                .doOnNext(System.out::println)
                .map(contactDBToContactConverter::convert)
                .defaultIfEmpty(Contact.builder().build());
    }

    @Override
    public Flux<Contact> getAll() {
        return findCacheValues(CACHE_NAME, "getAll", contactRepository.findAll())
                .map(contactDBToContactConverter::convert);
    }

    @Override
    public Mono<Contact> update(Contact contact) {
        return Mono.just(contact)
                .map(contactToContactDBConverter::convert)
                .flatMap(contactDB -> contactRepository.save(contactDB))
                .map(contactDBToContactConverter::convert)
                .defaultIfEmpty(Contact.builder().build());
    }
}
