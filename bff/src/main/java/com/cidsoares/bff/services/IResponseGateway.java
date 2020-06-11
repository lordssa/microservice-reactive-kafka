package com.cidsoares.bff.services;

import com.cidsoares.bff.dataprovider.ResponseDB;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.util.NoSuchElementException;

public interface IResponseGateway {

    @Retryable(
            value={NoSuchElementException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay=100)
    )
    String getResponse(String key) throws NoSuchElementException;

    ResponseDB save(String key, String payload);

    @Recover
    void fallback(NoSuchElementException e);
}
