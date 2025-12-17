package se.yrgo.gateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.yrgo.gateway.dto.AuctionDTO;
import se.yrgo.gateway.dto.AuctionLotDTO;
import se.yrgo.gateway.dto.CreateAuctionDTO;
import se.yrgo.gateway.dto.UpdateAuctionDTO;
import se.yrgo.gateway.service.AuctionServiceImpl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/auctions")
public class AuctionController {
    private AuctionServiceImpl service;

    public AuctionController(AuctionServiceImpl auctionService) {
        this.service = auctionService;
    }

    @GetMapping
    public ResponseEntity<List<AuctionLotDTO>> getMethodName() {
        return service.allAuctions();
    }

    @GetMapping("/auction/{id}")
    public ResponseEntity<AuctionDTO> getAuction(@PathVariable Long id) {
        return service.getAuction(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionLotDTO> getAuctionLot(@PathVariable Long id) {
        return service.getAuctionLot(id);
    }

    @PostMapping("/create-auction")
    public ResponseEntity<AuctionDTO> create(@RequestBody CreateAuctionDTO dto) {
        return service.createAuction(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuctionDTO> update(@PathVariable Long id, @RequestBody UpdateAuctionDTO dto) {
        return service.updateAuction(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.deleteAuction(id);
    }
}
