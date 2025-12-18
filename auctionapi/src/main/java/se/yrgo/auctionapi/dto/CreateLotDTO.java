package se.yrgo.auctionapi.dto;

import java.math.BigDecimal;

public class CreateLotDTO {
    private String title;
    private String description;
    private String imagePath;
    private boolean published;
    private BigDecimal estimate;

    public CreateLotDTO() {
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

    public BigDecimal getEstimate() {
        return estimate;
    }

    public void setEstimate(BigDecimal estimate) {
        this.estimate = estimate;
    }

}
