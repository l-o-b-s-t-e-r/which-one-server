package com.project.decider.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by macos on 22.05.16.
 */

@Entity
public class Record implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @Column
    private String username;

    @Column
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "record")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Image> images;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "optionId.record")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Option> options;

    public Record() {

    }

    public Record(Long recordId) {
        this.recordId = recordId;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        return recordId != null ? recordId.equals(record.recordId) : record.recordId == null;

    }

    @Override
    public int hashCode() {
        return recordId != null ? recordId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Record{" +
                "recordId=" + recordId +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", options=" + options +
                '}';
    }
}
