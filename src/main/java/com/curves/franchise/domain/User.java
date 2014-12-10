package com.curves.franchise.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int username;
    private String password;
    private boolean enabled;

    public void setUsername(int username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
