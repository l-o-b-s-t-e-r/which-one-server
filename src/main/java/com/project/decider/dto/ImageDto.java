package com.project.decider.dto;

/**
 * Created by macos on 20.06.16.
 */
public class ImageDto {
    private Long recordId;
    private String image;

    public ImageDto(Long recordId, String image) {
        this.recordId = recordId;
        this.image = image;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
