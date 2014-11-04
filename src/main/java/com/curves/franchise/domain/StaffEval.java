package com.curves.franchise.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class StaffEval implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String salesRatio;
    private String achAppRatio;
    private int ach;
    private int mm;
    private int app;
    private int ns;
    private int lx;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSalesRatio(String salesRatio) {
        this.salesRatio = salesRatio;
    }

    public void setAchAppRatio(String achAppRatio) {
        this.achAppRatio = achAppRatio;
    }

    public void setAch(int ach) {
        this.ach = ach;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public void setApp(int app) {
        this.app = app;
    }

    public void setNs(int ns) {
        this.ns = ns;
    }

    public void setLx(int lx) {
        this.lx = lx;
    }

    public Long getId() {
        return id;
    }

    public String getSalesRatio() {
        return salesRatio;
    }

    public String getAchAppRatio() {
        return achAppRatio;
    }

    public int getAch() {
        return ach;
    }

    public int getMm() {
        return mm;
    }

    public int getApp() {
        return app;
    }

    public int getNs() {
        return ns;
    }

    public int getLx() {
        return lx;
    }
}
