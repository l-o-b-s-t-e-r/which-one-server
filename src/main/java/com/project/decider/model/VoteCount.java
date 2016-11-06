package com.project.decider.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Lobster on 06.11.16.
 */

@Entity
public class VoteCount implements Serializable {

    @Id
    @OneToOne
    @JoinColumns({
            @JoinColumn(referencedColumnName = "recordId"),
            @JoinColumn(referencedColumnName = "optionName")
    })
    Option option;

    Integer voteCount = 0;

    public VoteCount() {

    }

    public VoteCount(Option option) {
        this.option = option;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoteCount voteCount1 = (VoteCount) o;

        if (option != null ? !option.equals(voteCount1.option) : voteCount1.option != null) return false;
        return voteCount != null ? voteCount.equals(voteCount1.voteCount) : voteCount1.voteCount == null;

    }

    @Override
    public int hashCode() {
        int result = option != null ? option.hashCode() : 0;
        result = 31 * result + (voteCount != null ? voteCount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VoteCount{" +
                "voteCount=" + voteCount +
                '}';
    }
}
