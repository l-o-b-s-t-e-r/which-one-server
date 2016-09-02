package com.project.decider.record;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by macos on 22.05.16.
 */

@Entity
@Table(name = "vote")
@IdClass(VoteId.class)
public class Vote {

    @Id
    private Long recordId;

    @Id
    private String userName;

    private String pointName;

    public Vote(){

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

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

}
