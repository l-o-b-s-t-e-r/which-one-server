package com.project.decider.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Lobster on 27.10.16.
 */

@Entity
public class Option implements Serializable {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "option")
    VoteCount voteCount;
    @EmbeddedId
    private OptionId optionId;

    public Option() {

    }

    public Option(OptionId optionId) {
        this.optionId = optionId;
    }

    public VoteCount getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(VoteCount voteCount) {
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
