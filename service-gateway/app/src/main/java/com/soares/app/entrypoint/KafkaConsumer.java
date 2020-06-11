package com.soares.app.entrypoint;

import com.soares.app.entrypoint.adapter.PeopleResourceToPeopleConverter;
import com.soares.app.entrypoint.adapter.PeopleToPeopleResourceConverter;
import com.soares.app.entrypoint.messager.KafkaProducer;
import com.soares.app.entrypoint.resource.PeopleResource;
import com.soares.core.usecase.list.ListPeopleWithTheirContactsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Optional.ofNullable;

@Component
@AllArgsConstructor
public class KafkaConsumer {

    private final PeopleResourceToPeopleConverter peopleResourceToPeopleConverter;
    private final PeopleToPeopleResourceConverter peopleToPeopleResourceConverter;
    private final ListPeopleWithTheirContactsUseCase listPeopleWithTheirContactsUseCase;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "${kafka.request.topic}", groupId = "${kafka.group.id}")
    @SendTo
    public String handle(String idRequest,
                         @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) throws ExecutionException, InterruptedException {

        final var request = PeopleResource
                .builder()
                .id(idRequest)
                .build();

        return ofNullable(request)
                .map(peopleResourceToPeopleConverter::convert)
                .map(listPeopleWithTheirContactsUseCase::execute)
                .get()
                .map(peopleToPeopleResourceConverter::convert)
                .flatMap(payload -> kafkaProducer.publish(payload, key))
                .toFuture()
                .get();
    }
}