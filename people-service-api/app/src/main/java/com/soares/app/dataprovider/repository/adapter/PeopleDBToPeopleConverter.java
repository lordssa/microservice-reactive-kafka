package com.soares.app.dataprovider.repository.adapter;

import com.soares.app.dataprovider.repository.model.PeopleDB;
import com.soares.core.entity.People;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class PeopleDBToPeopleConverter {
    private final ModelMapper modelMapper;

    public People convert(PeopleDB peopleDB){
        return modelMapper.map(peopleDB, People.class);
    }
}
