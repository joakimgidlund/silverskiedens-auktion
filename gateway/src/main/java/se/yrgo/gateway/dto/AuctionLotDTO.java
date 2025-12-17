package se.yrgo.gateway.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class AuctionLotDTO {
    private Long auctionId;
    private BigDecimal estimate;
    private BigDecimal currentBid;
    private Instant endTime;
    private Long lotId;
    private String title;
    private String description;
    private String imagePath;

    public AuctionLotDTO(){}

    public AuctionLotDTO(Long id, Instant endTime, BigDecimal estimate, BigDecimal currentBid, Long lotId, String title, String description, String imagePath) {

    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public BigDecimal getEstimate() {
        return estimate;
    }

    public void setEstimate(BigDecimal estimate) {
        this.estimate = estimate;
    }

    public BigDecimal getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(BigDecimal currentBid) {
        this.currentBid = currentBid;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
