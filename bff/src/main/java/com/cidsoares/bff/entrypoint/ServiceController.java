package com.cidsoares.bff.entrypoint;

import com.cidsoares.bff.services.PeopleService;
import com.cidsoares.bff.entrypoint.resource.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/back-for-front/people")
public class ServiceController {

    private PeopleService peopleService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public DeferredResult<ResponseEntity<PeopleResource>> getPeople(@RequestParam(required = true) String idRequest) {

        DeferredResult<ResponseEntity<PeopleResource>> output = new DeferredResult<>();
        final var peopleResource = (PeopleResource) peopleService
                .getPeopleWithContacts(idRequest)
                .getResult();

        final var response = new ResponseEntity<PeopleResource>(peopleResource, HttpStatus.OK);
        output.setResult(response);

        return output;
    }
}
