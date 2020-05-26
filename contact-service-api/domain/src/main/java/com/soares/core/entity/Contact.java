package com.soares.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    private String id;
    private TypeContact typeContact;
    private String value;
    private String peopleId;
}
