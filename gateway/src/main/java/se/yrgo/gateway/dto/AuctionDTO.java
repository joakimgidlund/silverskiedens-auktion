package se.yrgo.gateway.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class AuctionDTO {
    private Long id;
    private BigDecimal estimate;
    private Long lotId;
    private Instant endTime;


    public AuctionDTO(Long id, BigDecimal estimate, Long lotId, Instant endTime) {
        this.id = id;
        this.estimate = estimate;
        this.lotId = lotId;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getEstimate() {
        return estimate;
    }

    public void setEstimate(BigDecimal estimate) {
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
