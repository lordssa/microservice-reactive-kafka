package com.soares.app.entrypoint.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeopleResource {
    private String id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    private List<ContactResource> contacts;
}
