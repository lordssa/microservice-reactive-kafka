package com.soares.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeContact {
    EMAIL("email"),
    WHATSAPP("whatsapp"),
    PHONE("phone");

    private String name;
}
