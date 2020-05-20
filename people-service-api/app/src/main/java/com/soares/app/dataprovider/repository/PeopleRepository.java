package com.soares.app.dataprovider.repository;

import com.soares.app.dataprovider.repository.model.PeopleDB;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PeopleRepository extends ReactiveMongoRepository<PeopleDB, String> {
}
