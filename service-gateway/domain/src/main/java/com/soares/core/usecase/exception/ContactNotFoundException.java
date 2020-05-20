package com.soares.core.usecase.exception;

import lombok.Getter;

@Getter
public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String error) {
        super(error);
    }
}