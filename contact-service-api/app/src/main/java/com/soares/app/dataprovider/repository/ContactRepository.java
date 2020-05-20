package com.soares.app.dataprovider.repository;

import com.soares.app.dataprovider.repository.model.ContactDB;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ContactRepository extends ReactiveMongoRepository<ContactDB, String> {
}
