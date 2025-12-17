package se.yrgo.auctionapi.service;

import se.yrgo.auctionapi.data.AuctionRepository;
import se.yrgo.auctionapi.domain.Auction;
import se.yrgo.auctionapi.dto.AuctionDTO;
import se.yrgo.auctionapi.dto.AuctionLotDTO;
import se.yrgo.auctionapi.dto.CreateAuctionDTO;
import se.yrgo.auctionapi.dto.UpdateAuctionDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AuctionService {

    List<AuctionDTO> allAuctions();

    List<AuctionLotDTO> allAuctionsWithLotInfo();

    AuctionDTO createAuction(CreateAuctionDTO auction);

    AuctionDTO findAuctionById(Long id);

    AuctionLotDTO findFullAuctionById(Long id);

    AuctionDTO updateAuction(Long id, UpdateAuctionDTO updated);

    void deleteAuction(Long id);

    void placeBid(Long auctionId, Long userId, BigDecimal bidAmount);

}
