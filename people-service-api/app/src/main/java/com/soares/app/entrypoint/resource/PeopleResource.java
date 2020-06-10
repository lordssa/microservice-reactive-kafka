package com.soares.app.entrypoint.resource;

import lombok.*;

import javax.validation.constraints.NotBlank;

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
}
