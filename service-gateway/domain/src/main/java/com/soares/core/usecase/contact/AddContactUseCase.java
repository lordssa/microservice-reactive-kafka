package com.soares.core.usecase.contact;

import com.soares.core.entity.Contact;
import reactor.core.publisher.Mono;

public interface AddContactUseCase {
    Mono<Contact> execute(Contact contact);
}
