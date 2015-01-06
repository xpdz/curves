package com.curves.franchise.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Authorities implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String username;
    private String authority;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthority() {
        return authority;
    }
}
