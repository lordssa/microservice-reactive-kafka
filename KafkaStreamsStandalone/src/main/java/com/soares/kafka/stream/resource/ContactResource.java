package com.soares.kafka.stream.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResource {
    private String id;
    private TypeContactResource typeContact;
    private String value;
    private String peopleId;
}
