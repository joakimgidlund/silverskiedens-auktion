package se.yrgo.auctionapi.messaging.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class BidMessage {
    private Long auctionId;
    private Long userId;
    private BigDecimal bidAmount;
    private Instant timestamp;

    public BidMessage() {}

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
