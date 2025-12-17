package se.yrgo.gateway.dto;

public class LotDTO {
    private Long id;
    private String title;
    private String description;
    private String imagePath;
    private boolean published;

    public LotDTO(){}

    public LotDTO(Long id, String title, String description, String imagePath, boolean published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.published = published;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
