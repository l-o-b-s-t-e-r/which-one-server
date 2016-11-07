package com.project.decider.dto;

import com.project.decider.model.Image;
import com.project.decider.model.Option;
import com.project.decider.model.Record;
import com.project.decider.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lobster on 28.10.16.
 */
public class RecordDto {

    private Long recordId;
    private String username;
    private String avatar;
    private String description;
    private String selectedOption;
    private List<ImageDto> images;
    private List<OptionDto> options;

    public RecordDto() {

    }

    public RecordDto(Record record, User user, String selectedOption) {
        this.recordId = record.getRecordId();
        this.username = record.getUsername();
        this.avatar = user.getAvatar();
        this.description = record.getDescription();
        this.images = new ArrayList<>();
        this.options = new ArrayList<>();

        for (Image image : record.getImages()) {
            this.images.add(new ImageDto(image));
        }

        for (Option option : record.getOptions()) {
            this.options.add(new OptionDto(option));
        }

        this.selectedOption = selectedOption;
    }

    public RecordDto(String username, String avatar, Long recordId, String description, List<ImageDto> images, List<OptionDto> options) {
        this.username = username;
        this.avatar = avatar;
        this.recordId = recordId;
        this.description = description;
        this.images = images;
        this.options = options;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
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

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    @Override
    public String toString() {
        return "RecordDto{" +
                "recordId=" + recordId +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", selectedOption='" + selectedOption + '\'' +
                ", images=" + images +
                ", options=" + options +
                '}';
    }
}
