package se.yrgo.auctionapi.service;

import org.springframework.stereotype.Service;
import se.yrgo.auctionapi.data.LotRepository;
import se.yrgo.auctionapi.domain.Lot;
import se.yrgo.auctionapi.dto.LotDTO;

import java.util.List;
import java.util.Optional;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;

    public LotServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }
    @Override
    public List<Lot> allLots() {
        return lotRepository.findAll();
    }

    @Override
    public Lot createLot(Lot lot) {
        return lotRepository.save(lot);
    }

    @Override
    public Optional<Lot> findLotById(Long id) {
        return lotRepository.findById(id);
    }

    @Override
    public Lot updateLot(Long id, LotDTO updated) {

        return lotRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setDescription(updated.getDescription());
                    existing.setImagePath(updated.getImagePath());
                    return lotRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Lot not found"));
    }

    @Override
    public void deleteLot(Long id) {
        lotRepository.deleteById(id);
    }
}
