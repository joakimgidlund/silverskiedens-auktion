package se.yrgo.auctionapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.auctionapi.dto.AuctionDTO;
import se.yrgo.auctionapi.dto.AuctionLotDTO;
import se.yrgo.auctionapi.dto.CreateAuctionDTO;
import se.yrgo.auctionapi.dto.UpdateAuctionDTO;
import se.yrgo.auctionapi.service.AuctionService;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionRestController {
    private final AuctionService auctionService;

    @Autowired
    public AuctionRestController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuctionLotDTO>> allAuctionsWithInfo() {
        return ResponseEntity.ok(auctionService.allAuctionsWithLotInfo());
    }

    @GetMapping("/auction/{id}")
    public ResponseEntity<AuctionDTO> findAuctionById(@PathVariable Long id) {
        return ResponseEntity.ok(auctionService.findAuctionById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionLotDTO> findFullAuctionById(@PathVariable Long id) {
        return ResponseEntity.ok(auctionService.findFullAuctionById(id));
    }

    @PostMapping("/create-auction")
    public ResponseEntity<AuctionDTO> create(@RequestBody CreateAuctionDTO dto) {
        AuctionDTO created = auctionService.createAuction(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuctionDTO> update(@PathVariable Long id, @RequestBody UpdateAuctionDTO dto) {
        AuctionDTO updated = auctionService.updateAuction(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        auctionService.deleteAuction(id);
        return ResponseEntity.noContent().build();
    }
}
