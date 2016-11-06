package com.project.decider.dto;

import com.project.decider.model.Option;

/**
 * Created by Lobster on 28.10.16.
 */
public class OptionDto {

    private String optionName;
    private Integer voteCount;

    public OptionDto(String optionName, Integer voteCount) {
        this.optionName = optionName;
        this.voteCount = voteCount;
    }

    public OptionDto(Option option) {
        this.optionName = option.getOptionId().getOptionName();
        this.voteCount = option.getVoteCount().getVoteCount();
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "OptionDto{" +
                "optionName='" + optionName + '\'' +
                ", voteCount=" + voteCount +
                '}';
    }
}
