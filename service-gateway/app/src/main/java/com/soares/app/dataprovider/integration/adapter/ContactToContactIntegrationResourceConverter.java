package com.soares.app.dataprovider.integration.adapter;

import com.soares.app.dataprovider.integration.resource.ContactIntegrationResource;
import com.soares.core.entity.Contact;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ContactToContactIntegrationResourceConverter {
    private final ModelMapper modelMapper;

    public ContactIntegrationResource convert(Contact contact){
        return modelMapper.map(contact, ContactIntegrationResource.class);
    }
}
