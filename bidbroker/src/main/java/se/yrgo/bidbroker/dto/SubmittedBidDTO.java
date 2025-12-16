package se.yrgo.bidbroker.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class SubmittedBidDTO {
    private Long id;
    private Long auctionId;
    private Long userId;
    private BigDecimal bidAmount;
    private Instant timestamp;

    public SubmittedBidDTO(){}

    public SubmittedBidDTO(Long id, Long auctionId, Long userId, BigDecimal bidAmount, Instant timestamp) {
        this.id = id;
        this.auctionId = auctionId;
        this.userId = userId;
        this.bidAmount = bidAmount;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
