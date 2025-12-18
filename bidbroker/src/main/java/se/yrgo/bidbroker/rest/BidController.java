package se.yrgo.bidbroker.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import se.yrgo.bidbroker.dto.BidRequest;
import se.yrgo.bidbroker.dto.BidResponse;
import se.yrgo.bidbroker.dto.SubmittedBidDTO;
import se.yrgo.bidbroker.service.BidService;

import java.util.List;

@RestController
@RequestMapping("/bids")
public class BidController {
    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping("/{auctionId}")
    public ResponseEntity<BidResponse> placeBid(@PathVariable Long auctionId, @RequestBody BidRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        Object claim = jwt.getClaim("user-id");
        Long userId;
        if (claim instanceof Number) {
            userId = ((Number) claim).longValue();
        } else if (claim instanceof String) {
            userId = Long.valueOf((String) claim);
        } else {
            return ResponseEntity.status(500)
                    .body(new BidResponse("Invalid JWT claim for user-id", false));
        }
        try {
            bidService.submitBid(auctionId, userId, request.getBidAmount());
            return ResponseEntity.accepted().body(new BidResponse("Bid submitted successfully", true));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new BidResponse(e.getMessage(), false));
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(new BidResponse(e.getMessage(), false));
        }
    }

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<List<SubmittedBidDTO>> bidsForAuction(@PathVariable Long auctionId) {
        return ResponseEntity.ok(bidService.findBidsByAuctionId(auctionId));
    }
}
