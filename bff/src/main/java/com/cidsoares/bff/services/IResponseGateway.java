package com.cidsoares.bff.services;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.util.NoSuchElementException;

public interface IResponseGateway {

    @Retryable(
            value={NoSuchElementException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay=1000)
    )
    String getResponse(String key);
}
