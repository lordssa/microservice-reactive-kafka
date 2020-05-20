package com.soares.app.dataprovider.integration.adapter;

import com.soares.app.dataprovider.integration.resource.ContactIntegrationResource;
import com.soares.core.entity.Contact;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class ContactIntegrationResourceToContactConverter {
    private final ModelMapper modelMapper;

    public Contact convert(ContactIntegrationResource contactIntegrationResource){
        return modelMapper.map(contactIntegrationResource, Contact.class);
    }
}
