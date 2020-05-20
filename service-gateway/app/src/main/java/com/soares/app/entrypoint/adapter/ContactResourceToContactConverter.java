package com.soares.app.entrypoint.adapter;

import com.soares.app.entrypoint.resource.ContactResource;
import com.soares.core.entity.Contact;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContactResourceToContactConverter {

    private final ModelMapper modelMapper;

    public Contact convert(ContactResource contactResource){
        return modelMapper.map(contactResource, Contact.class);
    }
}
