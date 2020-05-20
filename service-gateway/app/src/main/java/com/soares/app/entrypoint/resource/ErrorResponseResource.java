package com.soares.app.entrypoint.resource;

import lombok.*;
import java.util.List;

@Builder
@Getter
public class ErrorResponseResource {
    @Singular(value = "addError") private List<String> errors;
}