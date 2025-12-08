package se.yrgo.auctionapi.service;

import org.springframework.stereotype.Service;
import se.yrgo.auctionapi.data.AuctionRepository;
import se.yrgo.auctionapi.data.LotRepository;
import se.yrgo.auctionapi.domain.Auction;
import se.yrgo.auctionapi.domain.Lot;
import se.yrgo.auctionapi.dto.AuctionDTO;
import se.yrgo.auctionapi.dto.AuctionLotDTO;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionServiceImpl implements AuctionService{

    private final AuctionRepository auctionRepository;
    private final LotRepository lotRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository, LotRepository lotRepository) {
        this.auctionRepository = auctionRepository;
        this.lotRepository = lotRepository;
    }

    @Override
    public List<Auction> allAuctions() {
        return auctionRepository.findAll();
    }

    @Override
    public List<AuctionLotDTO> allAuctionsWithLotInfo() {
        List<Auction> auctions = auctionRepository.findAll();
        return auctions.stream().map(this::convertToAuctionLotDTO).toList();
    }

    @Override
    public Auction createAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public Optional<Auction> findAuctionById(Long id) {
        return auctionRepository.findById(id);
    }

    @Override
    public Auction updateAuction(Long id, AuctionDTO updated) {
        return auctionRepository.findById(id)
                .map(existing -> {
                    existing.setEstimate(updated.getEstimate());
                    existing.setEndTime(updated.getEndTime());
                    if (updated.getLotId() != null) {
                        Lot lot = lotRepository.findById(updated.getLotId())
                                .orElseThrow(() -> new RuntimeException("Lot not found"));
                        existing.setLot(lot);
                    }
                    return auctionRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Auction not found"));
    }

    @Override
    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }

    private AuctionLotDTO convertToAuctionLotDTO(Auction auction) {
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
}
