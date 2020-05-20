package com.soares.app.dataprovider.integration.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactIntegrationResource {
    private String id;
    private TypeContactIntegrationResource typeContact;
    private String value;
    private String peopleId;
}
