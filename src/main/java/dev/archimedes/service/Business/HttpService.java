package dev.archimedes.service.business;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HttpService {

    private final RestClient restClient;

    public HttpService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }
}
