package se.yrgo.gateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TestServiceImpl {
    private final RestClient restClient;

    public TestServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    public String test() {
        return restClient.get().uri("http://localhost:8081/auth/test").retrieve().body(String.class);
    }
}
