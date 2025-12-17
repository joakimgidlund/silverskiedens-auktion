package se.yrgo.gateway.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import se.yrgo.gateway.dto.AuctionDTO;
import se.yrgo.gateway.dto.AuctionLotDTO;
import se.yrgo.gateway.dto.CreateAuctionDTO;
import se.yrgo.gateway.dto.UpdateAuctionDTO;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final RestClient restClient;

    private static final String BASEURL = "http://auction-api:8080/auctions/";

    public AuctionServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ResponseEntity<List<AuctionLotDTO>> allAuctions() {
        return restClient.get().uri(BASEURL)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<List<AuctionLotDTO>>() {
                        })));
    }

    @Override
    public ResponseEntity<AuctionDTO> getAuction(Long id) {
        return restClient.get().uri(BASEURL + "auction/" + id)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<AuctionDTO>() {
                        })));
    }

    @Override
    public ResponseEntity<AuctionLotDTO> getAuctionLot(Long id) {
        return restClient.get().uri(BASEURL + id)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<AuctionLotDTO>() {
                        })));
    }

    @Override
    public ResponseEntity<AuctionDTO> createAuction(CreateAuctionDTO dto) {
        return restClient.post().uri(BASEURL + "create-auction").body(dto)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<AuctionDTO>() {
                        })));
    }

    @Override
    public ResponseEntity<AuctionDTO> updateAuction(Long id, UpdateAuctionDTO updateDto) {
        return restClient.post().uri(BASEURL + id).body(updateDto)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<AuctionDTO>() {
                        })));
    }

    @Override
    public ResponseEntity<Void> deleteAuction(Long id) {
        return restClient.delete().uri(BASEURL + id)
                .exchange((request, response) -> ResponseEntity.status(response.getStatusCode())
                        .body(response.bodyTo(new ParameterizedTypeReference<Void>() {
                        })));
    }

    @Override
    public ResponseEntity<Void> placeBid(Long auctionId, Long userId, BigDecimal bidAmount, Instant timestamp) {
        // TODO Auto-generated method stub
        return null;
    }

}
