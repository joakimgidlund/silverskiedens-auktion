package se.yrgo.auctionapi.service;

import org.springframework.stereotype.Service;
import se.yrgo.auctionapi.data.LotRepository;
import se.yrgo.auctionapi.domain.Lot;
import se.yrgo.auctionapi.dto.CreateLotDTO;
import se.yrgo.auctionapi.dto.LotDTO;
import se.yrgo.auctionapi.dto.UpdateLotDTO;

import java.util.List;
import java.util.Optional;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;

    public LotServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
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
        Lot saved = lotRepository.save(lot);
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
                    return lotRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Lot not found"));
        return toDto(lot);
    }

    @Override
    public void deleteLot(Long id) {
        lotRepository.deleteById(id);
    }

    private LotDTO toDto(Lot lot) {
        return new LotDTO(
                lot.getId(),
                lot.getTitle(),
                lot.getDescription(),
                lot.getImagePath()
        );
    }
}


