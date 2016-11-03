package com.project.decider.dto;

import com.project.decider.model.Image;

/**
 * Created by Lobster on 28.10.16.
 */
public class ImageDto {

    private String image;

    public ImageDto(String image) {
        this.image = image;
    }

    public ImageDto(Image image) {
        this.image = image.getImageName();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ImageDto{" +
                "image='" + image + '\'' +
                '}';
    }
}
