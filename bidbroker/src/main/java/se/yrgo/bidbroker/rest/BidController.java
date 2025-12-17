package se.yrgo.bidbroker.rest;

import org.springframework.http.ResponseEntity;
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

    @PostMapping("/place-bid")
    public ResponseEntity<BidResponse> placeBid(@RequestHeader("X-User-Id") Long userId,
                                                @RequestBody BidRequest request) {
        try {
            bidService.submitBid(request.getAuctionId(), userId, request.getBidAmount());
            return ResponseEntity.accepted().body(new BidResponse("Bid submitted successfully", true));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new BidResponse(e.getMessage(), false));
        }
    }

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<List<SubmittedBidDTO>> bidsForAuction(@PathVariable Long auctionId) {
        return ResponseEntity.ok(bidService.findBidsByAuctionId(auctionId));
    }
}
