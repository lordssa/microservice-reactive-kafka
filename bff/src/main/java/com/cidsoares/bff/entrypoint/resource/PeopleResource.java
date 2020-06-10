package com.cidsoares.bff.entrypoint.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PeopleResource {
    private String id;
    private String firstname;
    private String lastname;
    private List<ContactResource> contacts;
    private MetaResource metaResource;
}
