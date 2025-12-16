package se.yrgo.bidbroker.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class BidMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long auctionId;
    private Long userId;
    private BigDecimal bidAmount;
    private Instant timestamp;

    public BidMessage(){}

    public BidMessage(Long auctionId, Long userId, BigDecimal bidAmount, Instant timestamp) {
        this.auctionId = auctionId;
        this.userId = userId;
        this.bidAmount = bidAmount;
        this.timestamp = timestamp;
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

    @Override
    public String toString() {
        return "BidMessage{" +
                "auctionId=" + auctionId +
                ", userId=" + userId +
                ", bidAmount=" + bidAmount +
                ", timestamp=" + timestamp +
                '}';
    }
}
