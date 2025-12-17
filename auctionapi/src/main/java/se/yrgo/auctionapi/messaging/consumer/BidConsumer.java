package se.yrgo.auctionapi.messaging.consumer;

import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;
import se.yrgo.auctionapi.messaging.dto.BidMessage;
import se.yrgo.auctionapi.service.AuctionService;

@Component
public class BidConsumer {
    private final AuctionService auctionService;

    public BidConsumer(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @JmsListener(destination = "bid.queue")
    public void consumeBid(BidMessage message) {
        auctionService.placeBid(
                message.getAuctionId(),
                message.getUserId(),
                message.getBidAmount());
    }
}
