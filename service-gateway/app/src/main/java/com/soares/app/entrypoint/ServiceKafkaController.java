package com.soares.app.entrypoint;

import com.soares.app.entrypoint.adapter.PeopleResourceToPeopleConverter;
import com.soares.app.entrypoint.adapter.PeopleToPeopleResourceConverter;
import com.soares.app.entrypoint.messager.KafkaProducer;
import com.soares.app.entrypoint.resource.PeopleResource;
import com.soares.core.usecase.list.ListPeopleWithTheirContactsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/v1/kafka/list/people")
public class ServiceKafkaController implements BaseEndpoint {

    private final PeopleResourceToPeopleConverter peopleResourceToPeopleConverter;
    private final PeopleToPeopleResourceConverter peopleToPeopleResourceConverter;
    private final ListPeopleWithTheirContactsUseCase listPeopleWithTheirContactsUseCase;
    private final KafkaProducer kafkaProducer;

     @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<String> getPeople(@RequestParam(required = true) String idRequest) {
       final var request = PeopleResource
                .builder()
                .id(idRequest)
                .build();

       final var key = UUID.randomUUID().toString();

        return ofNullable(request)
                .map(peopleResourceToPeopleConverter::convert)
                .map(listPeopleWithTheirContactsUseCase::execute)
                .get()
                .map(peopleToPeopleResourceConverter::convert)
                .flatMap(payload -> kafkaProducer.publish(payload, key));
    }
}
