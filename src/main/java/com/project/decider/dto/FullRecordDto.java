package com.project.decider.dto;

import com.project.decider.record.Quiz;
import com.project.decider.record.Record;
import com.project.decider.record.Vote;

import java.util.*;

/**
 * Created by macos on 20.06.16.
 */
public class FullRecordDto{

    private String title;
    private String avatar;
    private Long recordId;
    private List<ImageDto> images;
    private List<OptionDto> options;

    final private List<String> users = Arrays.asList("user1", "user2", "user3");

    public FullRecordDto(String title, String avatar, Long recordId, List<Record> record, Map<Quiz, List<Vote>> quiz) {
        this.title = title;
        this.avatar = avatar;
        this.recordId = recordId;

        images = new ArrayList<>();
        for (Record r: record){
            images.add(new ImageDto(recordId, r.getImage()));
        }

        options = new ArrayList<>();
        for (Map.Entry<Quiz, List<Vote>> q: quiz.entrySet()){
            options.add(new OptionDto(recordId,q.getKey().getName(), voteConverter(q.getValue())));
        }
    }

    private List<String> voteConverter(List<Vote> votesEntity){
        List<String> votes = new ArrayList<>();
        for (Vote v: votesEntity){
            votes.add(v.getUserName());
        }

        return votes;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<String> getUsers() {
        return users;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
