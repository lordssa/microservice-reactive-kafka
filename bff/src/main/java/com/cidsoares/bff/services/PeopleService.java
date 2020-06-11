package com.cidsoares.bff.services;

import com.cidsoares.bff.services.messager.Producer;
import com.cidsoares.bff.entrypoint.resource.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PeopleService {

    private final Producer producer;
    private final IResponseGateway responseGateway;

    public PeopleResource getPeopleWithContacts(final String idPeople) throws ExecutionException, InterruptedException {
        final var key = UUID.randomUUID().toString();
        CompletableFuture<String> reply = producer.send(idPeople, key);

        final var keyReplied = reply.get();
        System.out.println("Peguei a chave replied");
        Thread.sleep(150);
        final String responseEntity = responseGateway.getResponse(keyReplied);
        System.out.println("Vou retornar do getPeopleWithContacts");
        return fromBinary(responseEntity, PeopleResource.class);
    }

    public void saveResponse(String key, String payload){
        responseGateway.save(key, payload);
    }

    private static <T> T fromBinary(String object, Class<T> resultType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(object, resultType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
