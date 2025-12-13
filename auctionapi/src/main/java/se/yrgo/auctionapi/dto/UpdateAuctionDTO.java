package se.yrgo.auctionapi.dto;

public class UpdateAuctionDTO {
    private double estimate;
    private long lotId;

    public UpdateAuctionDTO() {}

    public double getEstimate() {
        return estimate;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }

    public long getLotId() {
        return lotId;
    }

    public void setLotId(long lotId) {
        this.lotId = lotId;
    }
}
