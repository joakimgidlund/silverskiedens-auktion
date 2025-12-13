package se.yrgo.auctionapi.service;

import se.yrgo.auctionapi.dto.CreateLotDTO;
import se.yrgo.auctionapi.dto.LotDTO;
import se.yrgo.auctionapi.dto.UpdateLotDTO;

import java.util.List;

public interface LotService {
    List<LotDTO> allLots();

    LotDTO createLot(CreateLotDTO dto);

    LotDTO findLotById(Long id);

    LotDTO updateLot(Long id, UpdateLotDTO updated);

    void deleteLot(Long id);
}
