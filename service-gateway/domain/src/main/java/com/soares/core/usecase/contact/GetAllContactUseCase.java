package com.soares.core.usecase.contact;

import com.soares.core.entity.Contact;
import reactor.core.publisher.Flux;

public interface GetAllContactUseCase {
    Flux<Contact> execute();
}
