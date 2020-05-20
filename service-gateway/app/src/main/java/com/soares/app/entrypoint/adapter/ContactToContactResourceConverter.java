package com.soares.app.entrypoint.adapter;

import com.soares.app.entrypoint.resource.ContactResource;
import com.soares.core.entity.Contact;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ContactToContactResourceConverter {
    private final ModelMapper modelMapper;

    public ContactResource convert(Contact contact){
        return modelMapper.map(contact, ContactResource.class);
    }
}
