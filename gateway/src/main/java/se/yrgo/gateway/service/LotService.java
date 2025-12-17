package se.yrgo.gateway.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import se.yrgo.gateway.dto.CreateLotDTO;
import se.yrgo.gateway.dto.LotDTO;
import se.yrgo.gateway.dto.UpdateLotDTO;

public interface LotService {
    ResponseEntity<List<LotDTO>> allLots();

    ResponseEntity<LotDTO> createLot(CreateLotDTO dto);

    ResponseEntity<LotDTO> findLotById(Long id);

    ResponseEntity<LotDTO> updateLot(Long id, UpdateLotDTO updated);

    ResponseEntity<Void> deleteLot(Long id);
}
