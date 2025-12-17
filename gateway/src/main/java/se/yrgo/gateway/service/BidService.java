package se.yrgo.gateway.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import se.yrgo.gateway.dto.BidRequest;
import se.yrgo.gateway.dto.BidResponse;

@Service
public class BidService {
    private final RestClient restClient;

    private static final String BASEURL = "http://auction-bidbroker:8080/bids/";

    public BidService(RestClient restClient) {
        this.restClient = restClient;
    }

    public ResponseEntity<BidResponse> placeBid(BidRequest bidRequest, Long userId) {
        return restClient.post().uri(BASEURL).header("X-User-Id", userId.toString()).body(bidRequest)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<BidResponse>() {
                        })));
    }
}
