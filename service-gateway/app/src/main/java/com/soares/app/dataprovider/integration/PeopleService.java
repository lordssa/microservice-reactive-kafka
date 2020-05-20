package com.soares.app.dataprovider.integration;

import com.soares.app.dataprovider.integration.resource.PeopleIntegrationResource;
import com.soares.app.dataprovider.integration.util.WebClientRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Data
@Builder
@NoArgsConstructor
public class PeopleService extends WebClientRequest {

    public PeopleService(@Value("${api.url.peopleService}") String baseUrl){
        super(baseUrl);
    }

    public Mono<PeopleIntegrationResource> getPeople(String idRequest) {
        return monoGetMapping("/idRequest="+idRequest, PeopleIntegrationResource.class)
                .map(response -> (PeopleIntegrationResource) response);
    }
}
