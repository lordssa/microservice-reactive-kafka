package com.soares.app.entrypoint;

import com.soares.app.entrypoint.resource.ErrorResponseResource;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "/contact", tags = "contact")
public interface BaseEndpoint {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    default ResponseEntity<ErrorResponseResource> handleException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(x -> MessageFormat.format("`{0}` {1}",
                        x.getField(), x.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponseResource
                                .builder()
                                .errors(errors)
                                .build()
                );
    }
}