package se.yrgo.auctionapi.dto;

public class CreateAuctionDTO {
    private double estimate;
    private Long lotId;

    public CreateAuctionDTO() {}

    public double getEstimate() {
        return estimate;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }
}
