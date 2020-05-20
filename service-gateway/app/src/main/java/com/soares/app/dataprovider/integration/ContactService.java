package com.soares.app.dataprovider.integration;

import com.soares.app.dataprovider.integration.resource.ContactIntegrationResource;
import com.soares.app.dataprovider.integration.util.WebClientRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Data
@Builder
@NoArgsConstructor
public class ContactService extends WebClientRequest {

    public ContactService(@Value("${api.url.contactService}") String baseUrl){
        super(baseUrl);
    }

    public Flux<ContactIntegrationResource> getContactsByPeople(String idPerson) {
        return fluxGetMapping("/idPerson="+idPerson, ContactIntegrationResource.class)
                .map(response -> (ContactIntegrationResource) response);
    }
}
