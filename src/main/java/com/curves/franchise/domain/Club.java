package com.curves.franchise.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Club implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int clubId;
    private Date openDate;
    private String name;
    private String cooperation;
    private String owner;
    private String mentor;
    private String manager;

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCooperation(String cooperation) {
        this.cooperation = cooperation;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public int getClubId() {
        return clubId;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public String getName() {
        return name;
    }

    public String getCooperation() {
        return cooperation;
    }

    public String getOwner() {
        return owner;
    }

    public String getMentor() {
        return mentor;
    }

    public String getManager() {
        return manager;
    }
}
