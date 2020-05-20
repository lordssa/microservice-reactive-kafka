package com.soares.app.entrypoint.adapter;

import com.soares.core.entity.People;
import com.soares.app.entrypoint.resource.PeopleResource;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class PeopleToPeopleResourceConverter {
    private final ModelMapper modelMapper;

    public PeopleResource convert(People people){
        return modelMapper.map(people, PeopleResource.class);
    }
}
