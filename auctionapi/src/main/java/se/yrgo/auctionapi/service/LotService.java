package se.yrgo.auctionapi.service;

import se.yrgo.auctionapi.domain.Lot;
import se.yrgo.auctionapi.dto.LotDTO;

import java.util.List;
import java.util.Optional;

public interface LotService {
    List<Lot> allLots();

    Lot createLot(Lot lot);

    Optional<Lot> findLotById(Long id);

    Lot updateLot(Long id, LotDTO updated);

    void deleteLot(Long id);
}
