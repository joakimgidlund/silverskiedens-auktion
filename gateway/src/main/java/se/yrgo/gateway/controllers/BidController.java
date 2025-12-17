package se.yrgo.gateway.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.yrgo.gateway.dto.BidRequest;
import se.yrgo.gateway.dto.BidResponse;
import se.yrgo.gateway.service.BidService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/bids")
public class BidController {
    
    private final BidService service;

    public BidController(BidService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BidResponse> placeBid(@RequestBody BidRequest bidRequest, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("user-id");
       return service.placeBid(bidRequest, userId);
    }
}
