package com.project.decider.model;

import javax.persistence.*;

/**
 * Created by Lobster on 27.10.16.
 */

@Entity
public class Image {

    @Id
    @Column
    private String imageName;

    @ManyToOne
    @JoinColumn(name = "recordId")
    private Record record;

    public Image() {

    }

    public Image(String imageName, Record record) {
        this.imageName = imageName;
        this.record = record;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return imageName != null ? imageName.equals(image.imageName) : image.imageName == null;

    }

    @Override
    public int hashCode() {
        return imageName != null ? imageName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageName='" + imageName + '\'' +
                '}';
    }
}
