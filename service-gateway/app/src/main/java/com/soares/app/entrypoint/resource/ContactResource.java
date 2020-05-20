package com.soares.app.entrypoint.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResource {
    private String id;
    @NotNull
    private TypeContactResource typeContact;
    @NotBlank
    private String value;
    @NotNull
    private String peopleId;
}
