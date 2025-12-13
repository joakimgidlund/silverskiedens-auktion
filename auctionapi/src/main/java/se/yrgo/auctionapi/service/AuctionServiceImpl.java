package se.yrgo.auctionapi.service;

import org.springframework.stereotype.Service;
import se.yrgo.auctionapi.data.AuctionRepository;
import se.yrgo.auctionapi.data.LotRepository;
import se.yrgo.auctionapi.domain.Auction;
import se.yrgo.auctionapi.domain.Lot;
import se.yrgo.auctionapi.dto.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService{

    private final AuctionRepository auctionRepository;
    private final LotRepository lotRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository, LotRepository lotRepository) {
        this.auctionRepository = auctionRepository;
        this.lotRepository = lotRepository;
    }

    @Override
    public List<AuctionDTO> allAuctions() {

        return auctionRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<AuctionLotDTO> allAuctionsWithLotInfo() {
        List<Auction> auctions = auctionRepository.findAll();
        return auctions.stream().map(this::toAuctionLotDto).toList();
    }

    @Override
    public AuctionDTO createAuction(CreateAuctionDTO created) {
        Lot lot = lotRepository.findById(created.getLotId())
                .orElseThrow(() -> new RuntimeException("Lot not found"));
        Auction auction = new Auction();
        auction.setEstimate(created.getEstimate());
        auction.setLot(lot);
        auction.setEndTime(Instant.now().plus(4, ChronoUnit.DAYS));
        Auction saved = auctionRepository.save(auction);
        return toDto(saved);
    }

    @Override
    public AuctionDTO findAuctionById(Long id) {
        Auction found = auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        return toDto(found);
    }

    @Override
    public AuctionLotDTO findFullAuctionById(Long id) {
        Auction foundAuction = auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        return toAuctionLotDto(foundAuction);
    }

    @Override
    public AuctionDTO updateAuction(Long id, UpdateAuctionDTO updated) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        auction.setEstimate(updated.getEstimate());
        Auction saved = auctionRepository.save(auction);
        return toDto(saved);
    }

    @Override
    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }

    private AuctionLotDTO toAuctionLotDto(Auction auction) {
        Lot lot = auction.getLot();
        return new AuctionLotDTO(
                auction.getId(),
                auction.getEndTime(),
                auction.getEstimate(),
                lot.getId(),
                lot.getTitle(),
                lot.getDescription(),
                lot.getImagePath()
        );
    }

    private AuctionDTO toDto(Auction auction) {
        return new AuctionDTO(
                auction.getId(),
                auction.getEstimate(),
                auction.getLot().getId(),
                auction.getEndTime()
        );
    }
}
