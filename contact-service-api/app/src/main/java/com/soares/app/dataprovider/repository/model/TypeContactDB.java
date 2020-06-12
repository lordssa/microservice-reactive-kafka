package com.soares.app.dataprovider.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum TypeContactDB implements Serializable {
    EMAIL("email"),
    WHATSAPP("whatsapp"),
    PHONE("phone");

    private String name;
}
