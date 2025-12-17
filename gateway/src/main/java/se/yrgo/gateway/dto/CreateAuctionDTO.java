package se.yrgo.gateway.dto;

import java.math.BigDecimal;

public class CreateAuctionDTO {
    private BigDecimal estimate;
    private Long lotId;

    public CreateAuctionDTO() {}

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
}
