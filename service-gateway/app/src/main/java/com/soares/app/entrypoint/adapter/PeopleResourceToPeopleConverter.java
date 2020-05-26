package com.soares.app.entrypoint.adapter;

import com.soares.app.entrypoint.resource.PeopleResource;
import com.soares.core.entity.People;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PeopleResourceToPeopleConverter {

    private final ModelMapper modelMapper;

    public People convert(PeopleResource peopleResource){
        return modelMapper.map(peopleResource, People.class);
    }
}
