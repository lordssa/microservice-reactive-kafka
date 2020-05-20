package com.soares.app.entrypoint;

import com.soares.app.entrypoint.resource.ContactResource;
import com.soares.core.usecase.contact.*;
import com.soares.app.entrypoint.adapter.ContactResourceToContactConverter;
import com.soares.app.entrypoint.adapter.ContactToContactResourceConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import static java.util.Optional.ofNullable;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/v1/contact")
public class ContactController implements BaseEndpoint {

    private final ContactResourceToContactConverter contactResourceToContactConverter;
    private final ContactToContactResourceConverter contactToContactResourceConverter;
    private final AddContactUseCase addContactUseCase;
    private final UpdateContactUseCase updateContactUseCase;
    private final GetContactUseCase getContactUseCase;
    private final GetAllContactUseCase getAllContactUseCase;
    private final DeleteContactUseCase deleteContactUseCase;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<ContactResource> addContact(@RequestBody @Valid final ContactResource request) {
        return ofNullable(request)
                .map(contactResourceToContactConverter::convert)
                .map(addContactUseCase::execute)
                .get()
                .map(contactToContactResourceConverter::convert)
                .defaultIfEmpty(ContactResource.builder().build());
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<ContactResource> updateContact(@RequestBody @Valid final ContactResource request) {
        return ofNullable(request)
                .map(contactResourceToContactConverter::convert)
                .map(updateContactUseCase::execute)
                .get()
                .map(contactToContactResourceConverter::convert)
                .defaultIfEmpty(ContactResource.builder().build());
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<ContactResource> getContact(@RequestParam(required = true) String idRequest) {
        final var request = ContactResource
                .builder()
                .id(idRequest)
                .build();

        return ofNullable(request)
                .map(contactResourceToContactConverter::convert)
                .map(getContactUseCase::execute)
                .get()
                .map(contactToContactResourceConverter::convert)
                .defaultIfEmpty(ContactResource.builder().build());
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Flux<ContactResource> getAllContact() {
        return getAllContactUseCase.execute()
                .map(contactToContactResourceConverter::convert);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBlacklistedPlayerById(@PathVariable final String id) {
        return deleteContactUseCase.execute(id);
    }
}
