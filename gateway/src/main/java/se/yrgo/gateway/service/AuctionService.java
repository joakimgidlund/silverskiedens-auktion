package se.yrgo.gateway.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;

import se.yrgo.gateway.dto.AuctionDTO;
import se.yrgo.gateway.dto.AuctionLotDTO;
import se.yrgo.gateway.dto.CreateAuctionDTO;
import se.yrgo.gateway.dto.UpdateAuctionDTO;

public interface AuctionService {

    ResponseEntity<List<AuctionLotDTO>> allAuctions();

    ResponseEntity<AuctionDTO> createAuction(CreateAuctionDTO auction);

    ResponseEntity<AuctionDTO> getAuction(Long id);

    ResponseEntity<AuctionLotDTO> getAuctionLot(Long id);

    ResponseEntity<AuctionDTO> updateAuction(Long id, UpdateAuctionDTO updated);

    ResponseEntity<Void> deleteAuction(Long id);

    ResponseEntity<Void> placeBid(Long auctionId, Long userId, BigDecimal bidAmount, Instant timestamp);

}
