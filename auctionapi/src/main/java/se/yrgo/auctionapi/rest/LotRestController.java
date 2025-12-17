package se.yrgo.auctionapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.auctionapi.domain.Lot;
import se.yrgo.auctionapi.dto.CreateLotDTO;
import se.yrgo.auctionapi.dto.LotDTO;
import se.yrgo.auctionapi.dto.UpdateLotDTO;
import se.yrgo.auctionapi.service.LotService;

import java.util.List;

@RestController
@RequestMapping("/lots")
public class LotRestController {
    private final LotService lotService;

    @Autowired
    public LotRestController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LotDTO>> allLots() {
        return ResponseEntity.ok(lotService.allLots());
    }

    @PostMapping("/create-lot")
    public ResponseEntity<LotDTO> create(@RequestBody CreateLotDTO dto) {
        LotDTO created = lotService.createLot(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LotDTO> findLotById(@PathVariable Long id) {
        return ResponseEntity.ok(lotService.findLotById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LotDTO> update(@PathVariable Long id, @RequestBody UpdateLotDTO dto) {
        LotDTO updated = lotService.updateLot(id, dto);
        return ResponseEntity.ok(updated);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lotService.deleteLot(id);
        return ResponseEntity.noContent().build();
    }


}
