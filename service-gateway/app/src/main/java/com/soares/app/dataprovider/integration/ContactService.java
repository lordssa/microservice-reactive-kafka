package com.soares.app.dataprovider.integration;

import com.soares.app.dataprovider.integration.resource.ContactIntegrationResource;
import com.soares.app.dataprovider.integration.util.WebClientRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Data
@Builder
public class ContactService extends WebClientRequest {

    private String baseUrl;

    public ContactService(@Value("${api.url.contactService}") String baseUrl){
        super(baseUrl);
        this.baseUrl = baseUrl;
    }

    public Flux<ContactIntegrationResource> getContactsByPeople(String idPerson) {
        return fluxGetMapping("/v1/contact/people?idPeople="+idPerson, ContactIntegrationResource.class)
                .map(response -> (ContactIntegrationResource) response);
    }
}
