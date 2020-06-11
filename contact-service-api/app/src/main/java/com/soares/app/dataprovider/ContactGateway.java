package com.soares.app.dataprovider;

import com.soares.app.dataprovider.repository.ContactRepository;
import com.soares.app.dataprovider.repository.adapter.ContactDBToContactConverter;
import com.soares.app.dataprovider.repository.adapter.ContactToContactDBConverter;
import com.soares.app.dataprovider.repository.model.ContactDB;
import com.soares.core.entity.Contact;
import com.soares.core.gateway.IContactGateway;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ContactGateway implements IContactGateway {
    private ContactRepository contactRepository;
    private ContactToContactDBConverter contactToContactDBConverter;
    private ContactDBToContactConverter contactDBToContactConverter;

    @Override
    public Mono<Contact> save(Contact contact) {
        return Mono.just(contact)
                .map(contactToContactDBConverter::convert)
                .flatMap(contactDB -> contactRepository.insert(contactDB))
                .map(contactDB -> contactDBToContactConverter.convert(contactDB))
                .defaultIfEmpty(Contact.builder().build());
    }

    @Override
    @CacheEvict(cacheNames = "Contact", key = "#id")
    public Mono<Void> delete(String id) {
        return contactRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "Contact", key="#contact.getId()")
    public Mono<Contact> getContactById(Contact contact) {
        return Mono.just(contact)
                .map(contactToContactDBConverter::convert)
                .map(ContactDB::getId)
                .flatMap(contactDB -> contactRepository.findById(contactDB))
                .map(contactDBToContactConverter::convert)
                .defaultIfEmpty(Contact.builder().build());
    }

    @Override
    @Cacheable(cacheNames = "Contact", key="#contact.getPeopleId()")
    public Flux<Contact> getContactsByPeople(Contact contact) {
        System.out.println(contact);
        return Flux.just(contact)
                .map(contactToContactDBConverter::convert)
                .map(ContactDB::getPeopleId)
                .flatMap(peopleId -> contactRepository.findAllByPeopleId(peopleId))
                .doOnNext(System.out::println)
                .map(contactDBToContactConverter::convert)
                .defaultIfEmpty(Contact.builder().build());
    }

    @Override
    @Cacheable(cacheNames = "Contact", key="#root.method.name")
    public Flux<Contact> getAll() {
        return contactRepository.findAll()
                .map(contactDBToContactConverter::convert);
    }

    @Override
    @CachePut(cacheNames = "Contact", key="#contact.getPeopleId()")
    public Mono<Contact> update(Contact contact) {
        return Mono.just(contact)
                .map(contactToContactDBConverter::convert)
                .flatMap(contactDB -> contactRepository.save(contactDB))
                .map(contactDBToContactConverter::convert)
                .defaultIfEmpty(Contact.builder().build());
    }
}
