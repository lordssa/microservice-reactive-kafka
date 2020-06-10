package com.cidsoares.bff.dataprovider;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends CrudRepository<ResponseDB, String> {
}
