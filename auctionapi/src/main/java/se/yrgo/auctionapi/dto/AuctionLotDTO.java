package se.yrgo.auctionapi.dto;

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
    private boolean published;

    public AuctionLotDTO() {
    }

    public AuctionLotDTO(Long auctionId, Instant endTime, BigDecimal estimate, BigDecimal currentBid, Long lotId,
            String title, String description, String imagePath, boolean published) {
        this.auctionId = auctionId;
        this.endTime = endTime;
        this.estimate = estimate;
        this.currentBid = currentBid;
        this.lotId = lotId;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.published = published;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Override
    public String toString() {
        return "AuctionLotDTO{" +
                "auctionId=" + auctionId +
                ", estimate=" + estimate +
                ", currentBid=" + currentBid +
                ", endTime=" + endTime +
                ", lotId=" + lotId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
