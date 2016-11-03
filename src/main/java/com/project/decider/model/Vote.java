package com.project.decider.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Lobster on 27.10.16.
 */

@Entity
public class Vote implements Serializable {

    @EmbeddedId
    private VoteId voteId;

    public Vote() {

    }

    public Vote(VoteId voteId) {
        this.voteId = voteId;
    }

    public VoteId getVoteId() {
        return voteId;
    }

    public void setVoteId(VoteId voteId) {
        this.voteId = voteId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voteId=" + voteId +
                '}';
    }
}
