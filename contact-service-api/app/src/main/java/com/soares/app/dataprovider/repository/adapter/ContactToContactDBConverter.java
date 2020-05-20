package com.soares.app.dataprovider.repository.adapter;

import com.soares.app.dataprovider.repository.model.ContactDB;
import com.soares.core.entity.Contact;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ContactToContactDBConverter {
    private final ModelMapper modelMapper;

    public ContactDB convert(Contact contact){
        return modelMapper.map(contact, ContactDB.class);
    }
}
