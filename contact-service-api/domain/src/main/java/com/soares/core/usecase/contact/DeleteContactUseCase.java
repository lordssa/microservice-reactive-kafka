package com.soares.core.usecase.contact;

import reactor.core.publisher.Mono;

public interface DeleteContactUseCase {
    Mono<Void> execute(String id);
}
