package se.yrgo.auctionapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.auctionapi.data.AuctionRepository;
import se.yrgo.auctionapi.data.BidRepository;
import se.yrgo.auctionapi.data.LotRepository;
import se.yrgo.auctionapi.domain.Auction;
import se.yrgo.auctionapi.domain.Lot;
import se.yrgo.auctionapi.domain.Bid;
import se.yrgo.auctionapi.dto.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService{

    private final AuctionRepository auctionRepository;
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository, LotRepository lotRepository, BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
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
        List<Auction> auctions = auctionRepository.findAllWithLot();
        List<AuctionLotDTO> dtos = auctions.stream().map(this::toAuctionLotDto).toList();
        return dtos;
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
        auction.setCurrentBid(updated.getCurrentBid());
        Auction saved = auctionRepository.save(auction);
        return toDto(saved);
    }

    @Override
    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void placeBid(Long auctionId, Long userId, BigDecimal bidAmount, Instant timestamp) {
        if (auctionId == null || userId == null || bidAmount == null) {
            throw new IllegalArgumentException("Invalid bid data");
        }
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        if (auction.getEndTime().isBefore(Instant.now())) {
            throw new RuntimeException("Auction has ended");
        }

        BigDecimal highestBid = bidRepository.findHighestBidAmountByAuctionId(auctionId)
                .orElse(BigDecimal.ZERO);

        if (bidAmount.compareTo(highestBid) <= 0) {
            throw new RuntimeException("Bid must be higher than current highest bid");
        }

        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setUserId(userId);
        bid.setBidAmount(bidAmount);
        bid.setTimestamp(timestamp);
        bidRepository.save(bid);
        auction.setCurrentBid(bidAmount);
        auctionRepository.save(auction);

    }

    private AuctionLotDTO toAuctionLotDto(Auction auction) {

        Lot lot = auction.getLot();

        if (lot == null) {
            System.out.println("Lot is null for auction " + auction.getId());
        }

        return new AuctionLotDTO(
                auction.getId(),
                auction.getEndTime(),
                auction.getEstimate(),
                auction.getCurrentBid(),
                lot != null ? lot.getId() : null,
                lot != null ? lot.getTitle() : null,
                lot != null ? lot.getDescription() : null,
                lot != null ? lot.getImagePath() : null
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
