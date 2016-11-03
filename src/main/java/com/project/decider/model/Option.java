package com.project.decider.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Lobster on 27.10.16.
 */

@Entity
public class Option implements Serializable {

    @EmbeddedId
    private OptionId optionId;

    @Column
    private Integer voteCount = 0;

    public Option() {

    }

    public Option(OptionId optionId) {
        this.optionId = optionId;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public OptionId getOptionId() {
        return optionId;
    }

    public void setOptionId(OptionId optionId) {
        this.optionId = optionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        return optionId != null ? optionId.equals(option.optionId) : option.optionId == null;

    }

    @Override
    public int hashCode() {
        return optionId != null ? optionId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Option{" +
                "voteCount=" + voteCount +
                ", optionId=" + optionId +
                '}';
    }
}
