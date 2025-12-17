package se.yrgo.gateway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import se.yrgo.gateway.dto.AuthRequestDTO;
import se.yrgo.gateway.dto.UserInfoDTO;

@Service
public class AuthServiceImpl {
    private final RestClient restClient;

    public AuthServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    public String registerUser(UserInfoDTO userInfo) {
        return restClient.post().uri("http://auction-auth-api:8080/auth/register").body(userInfo).retrieve().body(String.class);
    }

    public String loginUser(AuthRequestDTO authRequest) {
        return restClient.post().uri("http://auction-auth-api:8080/auth/login").body(authRequest).retrieve().body(String.class);
    }
}
