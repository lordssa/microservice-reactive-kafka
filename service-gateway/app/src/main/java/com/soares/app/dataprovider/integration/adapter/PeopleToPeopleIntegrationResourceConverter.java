package com.soares.app.dataprovider.integration.adapter;

import com.soares.app.dataprovider.integration.resource.PeopleIntegrationResource;
import com.soares.core.entity.People;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class PeopleToPeopleIntegrationResourceConverter {
    private final ModelMapper modelMapper;

    public PeopleIntegrationResource convert(People people){
        return modelMapper.map(people, PeopleIntegrationResource.class);
    }
}
