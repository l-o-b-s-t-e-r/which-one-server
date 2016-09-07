package com.project.decider.user;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Lobster on 30.04.16.
 */

@Entity
@Table(name = "user_info")
public class User implements Serializable{

    @Column(columnDefinition="serial")
    @Generated(GenerationTime.ALWAYS)
    private Long id;

    @Id
    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String avatar;

    @Column
    private String background;

    @Column
    private String email;

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", background='" + background + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
