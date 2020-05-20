package com.soares.app.dataprovider.integration.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeopleIntegrationResource {
    private String id;
    private String firstname;
    private String lastname;
}
