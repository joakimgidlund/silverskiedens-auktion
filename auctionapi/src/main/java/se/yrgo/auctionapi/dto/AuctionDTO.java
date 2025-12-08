package se.yrgo.auctionapi.dto;

import se.yrgo.auctionapi.domain.Lot;

import java.time.Instant;

public class AuctionDTO {
    private Long id;
    private double estimate;
    private Long lotId;
    private Instant endTime;

    public AuctionDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }
}
