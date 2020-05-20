package com.soares.app.entrypoint.exception;

import lombok.Getter;

@Getter
public class TypeContactNotFoundException extends RuntimeException {
    public TypeContactNotFoundException(String error) {
        super(error);
    }
}