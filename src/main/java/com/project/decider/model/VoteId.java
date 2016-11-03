package com.project.decider.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by macos on 02.08.16.
 */
@Embeddable
public class VoteId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(referencedColumnName = "recordId"),
            @JoinColumn(referencedColumnName = "optionName")
    })
    private Option option;

    public VoteId() {

    }

    public VoteId(User user, Option option) {
        this.user = user;
        this.option = option;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "VoteId{" +
                "user=" + user.getUsername() +
                ", option=" + option +
                '}';
    }
}
