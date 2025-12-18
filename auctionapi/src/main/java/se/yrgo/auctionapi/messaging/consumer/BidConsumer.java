package se.yrgo.auctionapi.messaging.consumer;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.jms.annotation.JmsListener;
import se.yrgo.auctionapi.messaging.dto.BidMessage;
import se.yrgo.auctionapi.service.AuctionService;

@Component
public class BidConsumer {
    private final AuctionService auctionService;
    private final ObjectMapper objectMapper;

    public BidConsumer(AuctionService auctionService, ObjectMapper objectMapper) {
        this.auctionService = auctionService;
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "bid.queue")
    public void consumeBid(String messageJson) throws JsonProcessingException {
        BidMessage message = objectMapper.readValue(messageJson, BidMessage.class);
        auctionService.placeBid(message.getAuctionId(), message.getUserId(), message.getBidAmount());
    }
}
