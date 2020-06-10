package com.cidsoares.bff.services;

import com.cidsoares.bff.services.messager.Producer;
import com.cidsoares.bff.dataprovider.ResponseDB;
import com.cidsoares.bff.dataprovider.ResponseRepository;
import com.cidsoares.bff.entrypoint.resource.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class PeopleService {

    private Producer producer;
    private IResponseGateway responseGateway;

    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleService.class);

    public DeferredResult<PeopleResource> getPeopleWithContacts(String idPeople){
        DeferredResult<PeopleResource> result = new DeferredResult<>();
        final var key = UUID.randomUUID().toString();

        CompletableFuture<String> reply = producer.send(idPeople, key);
        reply.thenAccept(keyReplied -> {
            final String responseEntity = responseGateway.getResponse(keyReplied);
            final var peopleResource = fromBinary(responseEntity, PeopleResource.class);
            result.setResult(peopleResource);
        }).exceptionally(ex -> {
            LOGGER.error(ex.getCause().getMessage());
            result.setErrorResult(ex.getCause().getMessage());
            return null;
        });

        return result;
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
