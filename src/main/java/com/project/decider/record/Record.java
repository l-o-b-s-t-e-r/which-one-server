package com.project.decider.record;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by macos on 22.05.16.
 */

@Entity
@Table(name = "record")
public class Record implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long recordId;

    @Column
    private String userName;

    @Column
    private String image;

    public Record(){

    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
