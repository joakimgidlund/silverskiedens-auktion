package se.yrgo.auctionapi.dto;

import java.math.BigDecimal;

public class UpdateAuctionDTO {
    private BigDecimal estimate;
    private long lotId;
    private BigDecimal currentBid;

    public UpdateAuctionDTO() {}

    public BigDecimal getEstimate() {
        return estimate;
    }

    public void setEstimate(BigDecimal estimate) {
        this.estimate = estimate;
    }

    public long getLotId() {
        return lotId;
    }

    public void setLotId(long lotId) {
        this.lotId = lotId;
    }

    public BigDecimal getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(BigDecimal currentBid) {
        this.currentBid = currentBid;
    }
}
