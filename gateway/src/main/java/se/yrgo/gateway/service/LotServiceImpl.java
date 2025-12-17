package se.yrgo.gateway.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import se.yrgo.gateway.dto.CreateLotDTO;
import se.yrgo.gateway.dto.LotDTO;
import se.yrgo.gateway.dto.UpdateLotDTO;

@Service
public class LotServiceImpl implements LotService {
    private final RestClient restClient;

    private static final String BASEURL = "http://auction-api:8080/lots/";

    public LotServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ResponseEntity<List<LotDTO>> allLots() {
        return restClient.get().uri(BASEURL)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<List<LotDTO>>() {
                        })));
    }

    @Override
    public ResponseEntity<LotDTO> createLot(CreateLotDTO dto) {
        return restClient.put().uri(BASEURL + "create-lot").body(dto)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<LotDTO>() {
                        })));
    }

    @Override
    public ResponseEntity<LotDTO> findLotById(Long id) {
        return restClient.get().uri(BASEURL + id)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<LotDTO>() {
                        })));
    }

    @Override
    public ResponseEntity<LotDTO> updateLot(Long id, UpdateLotDTO updated) {
        return restClient.put().uri(BASEURL + id).body(updated)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<LotDTO>() {
                        })));
    }

    @Override
    public ResponseEntity<Void> deleteLot(Long id) {
        return restClient.delete().uri(BASEURL + id)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<Void>() {
                        })));
    }
}
