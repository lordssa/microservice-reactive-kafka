package com.soares.app.dataprovider.integration.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Optional.of;

@Slf4j
@Component
public class WebClientRequest {

    private WebClient webClient;

    public WebClientRequest(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public WebClientRequest() {
        this.webClient = WebClient.create();
    }

    public Mono<Object> monoGetMapping(String url, String header, String valueHeader, Class<?> classConverter) {

        return webClient.get()
                .uri(url)
                .header(header, valueHeader)
                .exchange()
                .flatMap(clientResponse -> getBodyMonoResponse(url, clientResponse, classConverter));
    }

    public Mono<Object> monoGetMapping(String url, Class<?> classConverter) {

        return webClient.get()
                .uri(url)
                .exchange()
                .flatMap(clientResponse -> getBodyMonoResponse(url, clientResponse, classConverter));
    }

    public Flux<Object> fluxGetMapping(String url, String header, String valueHeader, Class<?> classConverter) {

        return webClient.get()
                .uri(url)
                .header(header, valueHeader)
                .exchange()
                .flux()
                .flatMap(clientResponse -> getBodyFluxResponse(url, clientResponse, classConverter));
    }

    public Flux<Object> fluxGetMapping(String url, Class<?> classConverter) {

        return webClient.get()
                .uri(url)
                .exchange()
                .flux()
                .flatMap(clientResponse -> getBodyFluxResponse(url, clientResponse, classConverter));
    }

    private Mono<Object> getBodyMonoResponse(String url, ClientResponse clientResponse, Class<?> classConverter) {
        return of(clientResponse.statusCode())
                .map(HttpStatus::isError)
                .filter(Boolean::booleanValue)
                .map(isError -> {
                    log.error("Error {} occurred calling {}", clientResponse.statusCode().toString(), url);
                    return Mono.empty();
                })
                .orElse(clientResponse.bodyToMono(classConverter));
    }

    private Flux<Object> getBodyFluxResponse(String url, ClientResponse clientResponse, Class<?> classConverter) {
        return of(clientResponse.statusCode())
                .map(HttpStatus::isError)
                .filter(Boolean::booleanValue)
                .map(isError -> {
                    log.error("Error {} occurred calling {}", clientResponse.statusCode().toString(), url);
                    return Flux.empty();
                })
                .orElse(clientResponse.bodyToFlux(classConverter));
    }
}
