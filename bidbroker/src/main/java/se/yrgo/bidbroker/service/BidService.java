package se.yrgo.bidbroker.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import se.yrgo.bidbroker.domain.SubmittedBid;
import se.yrgo.bidbroker.dto.BidMessage;
import se.yrgo.bidbroker.dto.SubmittedBidDTO;
import se.yrgo.bidbroker.repository.SubmittedBidRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class BidService {
    private final JmsTemplate jmsTemplate;
    private final SubmittedBidRepository submittedBidRepository;
    private final ObjectMapper objectMapper;

    public BidService(JmsTemplate jmsTemplate, SubmittedBidRepository submittedBidRepository, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.submittedBidRepository = submittedBidRepository;
        this.objectMapper = objectMapper;
    }

    public void submitBid(Long auctionId, Long userId, BigDecimal bidAmount) {
        validateBidRequest(auctionId, userId, bidAmount);

        SubmittedBid submittedBid = new SubmittedBid();
        submittedBid.setAuctionId(auctionId);
        submittedBid.setUserId(userId);
        submittedBid.setBidAmount(bidAmount);
        submittedBid.setTimestamp(Instant.now());

        submittedBidRepository.save(submittedBid);

        BidMessage message = new BidMessage(
                auctionId,
                userId,
                bidAmount,
                Instant.now()
        );

        try {
            jmsTemplate.convertAndSend("bid.queue", objectMapper.writeValueAsString(message));
        } catch (Exception e) {
            throw new RuntimeException("Failed to queue bid", e);
        }
    }

    private void validateBidRequest(Long auctionId, Long userId, BigDecimal bidAmount) {
        if (auctionId == null) {
            throw new IllegalArgumentException("Auction ID is required");
        }

        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        if (bidAmount == null) {
            throw new IllegalArgumentException("Bid amount is required");
        }

        if (bidAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Bid amount must be greater than zero");
        }
    }

    public List<SubmittedBidDTO> findBidsByAuctionId(Long auctionId) {
        if (auctionId == null) {
            throw new IllegalArgumentException("Auction ID is required");
        }

        return submittedBidRepository.findByAuctionId(auctionId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private SubmittedBidDTO toDto(SubmittedBid bid) {
        return new SubmittedBidDTO(
                bid.getId(),
                bid.getAuctionId(),
                bid.getUserId(),
                bid.getBidAmount(),
                bid.getTimestamp()
        );
    }
}
