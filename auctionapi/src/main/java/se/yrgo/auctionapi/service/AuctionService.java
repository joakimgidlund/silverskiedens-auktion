package se.yrgo.auctionapi.service;

import se.yrgo.auctionapi.data.AuctionRepository;
import se.yrgo.auctionapi.domain.Auction;
import se.yrgo.auctionapi.dto.AuctionDTO;
import se.yrgo.auctionapi.dto.AuctionLotDTO;

import java.util.List;
import java.util.Optional;

public interface AuctionService {

    List<Auction> allAuctions();

    List<AuctionLotDTO> allAuctionsWithLotInfo();

    Auction createAuction(Auction auction);

    Optional<Auction> findAuctionById(Long id);

    Auction updateAuction(Long id, AuctionDTO updated);

    void deleteAuction(Long id);

}
