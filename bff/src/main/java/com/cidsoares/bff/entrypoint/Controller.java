package com.cidsoares.bff.entrypoint;

import com.cidsoares.bff.dataprovider.ResponseDB;
import com.cidsoares.bff.dataprovider.ResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
@AllArgsConstructor
public class Controller {

    private ResponseRepository responseRepository;

    @PostMapping
    public ResponseDB save(@RequestBody ResponseDB model){
        final var retorno = responseRepository.save(model);
        return retorno;
    }

    @GetMapping("/{id}")
    public String find(@PathVariable String id){
        return responseRepository.findById(id).map(ResponseDB::getValue).get();
    }

}
