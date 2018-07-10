package com.drainey.bakingapp.model;

/**
 * Created by david-rainey on 7/9/18.
 */

public class RecipeStep {
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public RecipeStep() {
    }

    public RecipeStep(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public String toString() {
        return "RecipeStep{" +
                "id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeStep that = (RecipeStep) o;

        if (getId() != that.getId()) return false;
        if (getShortDescription() != null ? !getShortDescription().equals(that.getShortDescription()) : that.getShortDescription() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getVideoURL() != null ? !getVideoURL().equals(that.getVideoURL()) : that.getVideoURL() != null)
            return false;
        return getThumbnailURL() != null ? getThumbnailURL().equals(that.getThumbnailURL()) : that.getThumbnailURL() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getVideoURL() != null ? getVideoURL().hashCode() : 0);
        result = 31 * result + (getThumbnailURL() != null ? getThumbnailURL().hashCode() : 0);
        return result;
    }
}
