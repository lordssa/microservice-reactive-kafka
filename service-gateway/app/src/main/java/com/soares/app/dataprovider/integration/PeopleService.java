package com.soares.app.dataprovider.integration;

import com.soares.app.dataprovider.integration.resource.PeopleIntegrationResource;
import com.soares.app.dataprovider.integration.util.WebClientRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Data
@Builder
public class PeopleService extends WebClientRequest {

    private String baseUrl;

    public PeopleService(@Value("${api.url.peopleService}") String baseUrl){
        super(baseUrl);
        this.baseUrl = baseUrl;
    }

    public Mono<PeopleIntegrationResource> getPeople(String idRequest) {
        return monoGetMapping("/v1/people?idRequest="+idRequest, PeopleIntegrationResource.class)
                .map(response -> (PeopleIntegrationResource) response);
    }
}
