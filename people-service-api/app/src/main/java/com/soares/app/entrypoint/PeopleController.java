package com.soares.app.entrypoint;

import com.soares.core.usecase.people.*;
import com.soares.app.entrypoint.adapter.PeopleResourceToPeopleConverter;
import com.soares.app.entrypoint.adapter.PeopleToPeopleResourceConverter;
import com.soares.app.entrypoint.resource.PeopleResource;
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
@RequestMapping("/v1/people")
public class PeopleController implements BaseEndpoint {

    private final PeopleResourceToPeopleConverter peopleResourceToPeopleConverter;
    private final PeopleToPeopleResourceConverter peopleToPeopleResourceConverter;
    private final AddPeopleUseCase addPeopleUseCase;
    private final UpdatePeopleUseCase updatePeopleUseCase;
    private final GetPeopleUseCase getPeopleUseCase;
    private final GetAllPeopleUseCase getAllPeopleUseCase;
    private final DeletePeopleUseCase deletePeopleUseCase;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<PeopleResource> addPeople(@RequestBody @Valid final PeopleResource request) {

        return ofNullable(request)
                .map(peopleResourceToPeopleConverter::convert)
                .map(addPeopleUseCase::execute)
                .get()
                .map(peopleToPeopleResourceConverter::convert)
                .defaultIfEmpty(PeopleResource.builder().build());
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<PeopleResource> updatePeople(@RequestBody @Valid final PeopleResource request) {
        return ofNullable(request)
                .map(peopleResourceToPeopleConverter::convert)
                .map(updatePeopleUseCase::execute)
                .get()
                .map(peopleToPeopleResourceConverter::convert)
                .defaultIfEmpty(PeopleResource.builder().build());
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<PeopleResource> getPeople(@RequestParam(required = true) String idRequest) {
        final var request = PeopleResource
                .builder()
                .id(idRequest)
                .build();

        return ofNullable(request)
                .map(peopleResourceToPeopleConverter::convert)
                .map(getPeopleUseCase::execute)
                .get()
                .map(peopleToPeopleResourceConverter::convert)
                .defaultIfEmpty(PeopleResource.builder().build());
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Flux<PeopleResource> getAllPeople() {
        return getAllPeopleUseCase.execute()
                .map(peopleToPeopleResourceConverter::convert);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBlacklistedPlayerById(@PathVariable final String id) {
        return deletePeopleUseCase.execute(id);
    }
}
