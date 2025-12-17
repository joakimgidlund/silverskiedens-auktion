package se.yrgo.gateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.yrgo.gateway.dto.CreateLotDTO;
import se.yrgo.gateway.dto.LotDTO;
import se.yrgo.gateway.dto.UpdateLotDTO;
import se.yrgo.gateway.service.LotService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/lots")
public class LotController {
    private final LotService service;

    public LotController(LotService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LotDTO>> allLots() {
        return service.allLots();
    }

    @PostMapping("/create-lot")
    public ResponseEntity<LotDTO> create(@RequestBody CreateLotDTO dto) {
        return service.createLot(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LotDTO> findLotById(@PathVariable Long id) {
        return service.findLotById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LotDTO> update(@PathVariable Long id, @RequestBody UpdateLotDTO dto) {
        return service.updateLot(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.deleteLot(id);
    }

}
