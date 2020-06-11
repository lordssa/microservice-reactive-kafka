package com.cidsoares.bff.dataprovider;

import com.cidsoares.bff.services.IResponseGateway;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ResponseGateway implements IResponseGateway {

    private final ResponseRepository responseRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseGateway.class);

    @Value("${redis.expiretime}")
    private Long expirationTime;

    @Override
    public String getResponse(String key) throws NoSuchElementException {
        System.out.println("Tentando pegar a resposta");
        final var test = responseRepository
                .findById(key)
                .map(ResponseDB::getValue)
                .get();
        System.out.println(test);
        return test;
    }

    @Override
    public ResponseDB save(String key, String payload) {
        final var responseDB = ResponseDB
                .builder()
                .correlationId(key)
                .value(payload)
                .ttl(expirationTime)
                .build();

        return responseRepository.save(responseDB);
    }

    @Override
    public void fallback(NoSuchElementException e) {
        LOGGER.error("It was not possible to respond in time for the request");
    }

}
