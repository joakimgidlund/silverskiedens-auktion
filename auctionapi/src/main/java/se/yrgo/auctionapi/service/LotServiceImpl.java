package se.yrgo.auctionapi.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import se.yrgo.auctionapi.data.AuctionRepository;
import se.yrgo.auctionapi.data.BidRepository;
import se.yrgo.auctionapi.data.LotRepository;
import se.yrgo.auctionapi.domain.Auction;
import se.yrgo.auctionapi.domain.Lot;
import se.yrgo.auctionapi.dto.CreateLotDTO;
import se.yrgo.auctionapi.dto.LotDTO;
import se.yrgo.auctionapi.dto.UpdateLotDTO;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    public LotServiceImpl(LotRepository lotRepository, AuctionRepository auctionRepository, BidRepository bidRepository) {
        this.lotRepository = lotRepository;
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }
    @Override
    public List<LotDTO> allLots() {
        return lotRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public LotDTO createLot(CreateLotDTO dto) {
        Lot lot = new Lot();
        lot.setTitle(dto.getTitle());
        lot.setDescription(dto.getDescription());
        lot.setImagePath(dto.getImagePath());
        lot.setPublished(dto.isPublished());
        Lot saved = lotRepository.save(lot);
        Auction relatedAuction = new Auction();
        relatedAuction.setLot(saved);
        relatedAuction.setEstimate(dto.getEstimate());
        relatedAuction.setCurrentBid(dto.getEstimate());
        relatedAuction.setEndTime(Instant.now().plus(30, ChronoUnit.DAYS));
        auctionRepository.save(relatedAuction);
        return toDto(saved);
    }

    @Override
    public LotDTO findLotById(Long id) {
        Lot foundLot = lotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lot not found"));

        return toDto(foundLot);
    }

    @Override
    public LotDTO updateLot(Long id, UpdateLotDTO updated) {

        Lot lot = lotRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setDescription(updated.getDescription());
                    existing.setImagePath(updated.getImagePath());
                    existing.setPublished(updated.isPublished());
                    return lotRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Lot not found"));
        return toDto(lot);
    }

    @Override
    @Transactional
    public void deleteLot(Long id) {
        var auction = auctionRepository.findByLotId(id);
        if(auction.isPresent()) {
            bidRepository.deleteByAuctionId(auction.get().getId());
            auctionRepository.deleteByLotId(id);
            lotRepository.deleteById(id);
        }
    }

    private LotDTO toDto(Lot lot) {
        return new LotDTO(
                lot.getId(),
                lot.getTitle(),
                lot.getDescription(),
                lot.getImagePath(),
                lot.isPublished()
        );
    }
}


