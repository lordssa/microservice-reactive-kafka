package com.soares.app.dataprovider.integration.exception;

import lombok.Getter;

@Getter
public class TypeContactIntegrationNotFoundException extends RuntimeException {
    public TypeContactIntegrationNotFoundException(String error) {
        super(error);
    }
}