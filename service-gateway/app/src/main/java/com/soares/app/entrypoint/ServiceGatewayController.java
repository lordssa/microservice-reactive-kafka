package com.soares.app.entrypoint;

import com.soares.app.entrypoint.adapter.PeopleResourceToPeopleConverter;
import com.soares.app.entrypoint.adapter.PeopleToPeopleResourceConverter;
import com.soares.app.entrypoint.resource.PeopleResource;
import com.soares.core.usecase.list.ListPeopleWithTheirContactsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static java.util.Optional.ofNullable;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/v1/list/people")
public class ServiceGatewayController implements BaseEndpoint {

    private final PeopleResourceToPeopleConverter peopleResourceToPeopleConverter;
    private final PeopleToPeopleResourceConverter peopleToPeopleResourceConverter;
    private final ListPeopleWithTheirContactsUseCase listPeopleWithTheirContactsUseCase;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<PeopleResource> getPeople(@RequestParam(required = true) String idRequest) {
        final var request = PeopleResource
                .builder()
                .id(idRequest)
                .build();

        return ofNullable(request)
                .map(peopleResourceToPeopleConverter::convert)
                .map(listPeopleWithTheirContactsUseCase::execute)
                .get()
                .log()
                .map(peopleToPeopleResourceConverter::convert)
                .doOnNext(System.out::println)
                .defaultIfEmpty(PeopleResource.builder().build());
    }
}
