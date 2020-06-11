package com.cidsoares.bff.entrypoint;

import com.cidsoares.bff.services.PeopleService;
import com.cidsoares.bff.entrypoint.resource.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ExecutionException;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/back-for-front/people")
public class ServiceController {

    private PeopleService peopleService;


    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<PeopleResource> getPeople1(@RequestParam(required = true) String idRequest) throws ExecutionException, InterruptedException {
        final var peopleResource = (PeopleResource) peopleService
                .getPeopleWithContacts(idRequest);

        return new ResponseEntity<PeopleResource>(peopleResource, HttpStatus.OK);
    }
}
