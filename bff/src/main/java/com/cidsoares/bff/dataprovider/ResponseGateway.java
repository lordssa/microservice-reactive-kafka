package com.cidsoares.bff.dataprovider;

import com.cidsoares.bff.services.IResponseGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ResponseGateway implements IResponseGateway {

    private ResponseRepository responseRepository;

    @Override
    public String getResponse(String key) {
        return responseRepository
                .findById(key)
                .map(ResponseDB::getValue)
                .get();
    }
}
