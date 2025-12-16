package se.yrgo.bidbroker.dto;

import java.math.BigDecimal;

public class BidRequest {
    private Long auctionId;
    private BigDecimal bidAmount;

    public BidRequest(){}

    public BidRequest(Long auctionId, BigDecimal bidAmount) {
        this.auctionId = auctionId;
        this.bidAmount = bidAmount;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }
}
