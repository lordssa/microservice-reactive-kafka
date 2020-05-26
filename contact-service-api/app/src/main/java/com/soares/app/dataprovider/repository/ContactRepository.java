package com.soares.app.dataprovider.repository;

import com.soares.app.dataprovider.repository.model.ContactDB;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ContactRepository extends ReactiveMongoRepository<ContactDB, String> {
    Flux<ContactDB> findAllByPeopleId(String peopleId);
}
