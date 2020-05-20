package com.soares.app.dataprovider.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeContactDB {
    EMAIL("email"),
    WHATSAPP("whatsapp"),
    PHONE("phone");

    private String name;
}
