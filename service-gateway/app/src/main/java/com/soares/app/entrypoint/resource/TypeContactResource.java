package com.soares.app.entrypoint.resource;

import com.soares.app.entrypoint.exception.TypeContactNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.util.Arrays.stream;

@Getter
@AllArgsConstructor
public enum TypeContactResource {
    EMAIL("email"),
    WHATSAPP("whatsapp"),
    PHONE("phone");

    private String name;

    @JsonCreator
    public static TypeContactResource fromValue(final String text) {
        return stream(TypeContactResource.values())
                .filter(value -> String.valueOf(value.name).equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new TypeContactNotFoundException("Type Contact:" + text + " is not supported."));
    }
}
