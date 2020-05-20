package com.soares.app.dataprovider.integration.resource;

import com.soares.app.dataprovider.integration.exception.TypeContactIntegrationNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.util.Arrays.stream;

@Getter
@AllArgsConstructor
public enum TypeContactIntegrationResource {
    EMAIL("email"),
    WHATSAPP("whatsapp"),
    PHONE("phone");

    private String name;

    public static TypeContactIntegrationResource fromValue(final String text) {
        return stream(TypeContactIntegrationResource.values())
                .filter(value -> String.valueOf(value.name).equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new TypeContactIntegrationNotFoundException("Type Contact:" + text + " is not supported."));
    }
}