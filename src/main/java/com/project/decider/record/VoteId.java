package com.project.decider.record;

import java.io.Serializable;

/**
 * Created by macos on 02.08.16.
 */
public class VoteId implements Serializable {
    private Long recordId;
    private String userName;

    public VoteId() {
    }

    public VoteId(Long recordId, String userName) {
        this.recordId = recordId;
        this.userName = userName;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoteId voteId = (VoteId) o;

        if (recordId != null ? !recordId.equals(voteId.recordId) : voteId.recordId != null) return false;
        return userName != null ? userName.equals(voteId.userName) : voteId.userName == null;

    }

    @Override
    public int hashCode() {
        int result = recordId != null ? recordId.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }
}
