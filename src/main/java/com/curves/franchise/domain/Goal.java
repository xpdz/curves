package com.curves.franchise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Goal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    private Date lastModified;

    private int gYear; // 2014, 2013, ...
    private int gMonth; // Jan=0, Feb=1, ..., Dec=11

    private int newSalesRevenue;
    private int duesDraftRevenue;
    private int productsRevenue;
    private int revenue;

    private int cmPostFlyer6;
    private int cmHandFlyer6;
    private float cmHandFlyerHours6;
    private int cmOutGp6;
    private float cmOutGpHours6;
    private int cmCpBox6;
    private int cmApoTotal6;
    private int cmFaSum6;
    private int salesAch6;
    private float salesRatio6;
    private int cmOwnRefs6;
    private int cmOutAgpOut;
    private int cmInAgpOut6;

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public void setgYear(int gYear) {
        this.gYear = gYear;
    }

    public void setgMonth(int gMonth) {
        this.gMonth = gMonth;
    }

    public void setNewSalesRevenue(int newSalesRevenue) {
        this.newSalesRevenue = newSalesRevenue;
    }

    public void setDuesDraftRevenue(int duesDraftRevenue) {
        this.duesDraftRevenue = duesDraftRevenue;
    }

    public void setProductsRevenue(int productsRevenue) {
        this.productsRevenue = productsRevenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setCmPostFlyer6(int cmPostFlyer6) {
        this.cmPostFlyer6 = cmPostFlyer6;
    }

    public void setCmHandFlyer6(int cmHandFlyer6) {
        this.cmHandFlyer6 = cmHandFlyer6;
    }

    public void setCmHandFlyerHours6(float cmHandFlyerHours6) {
        this.cmHandFlyerHours6 = cmHandFlyerHours6;
    }

    public void setCmOutGp6(int cmOutGp6) {
        this.cmOutGp6 = cmOutGp6;
    }

    public void setCmOutGpHours6(float cmOutGpHours6) {
        this.cmOutGpHours6 = cmOutGpHours6;
    }

    public void setCmCpBox6(int cmCpBox6) {
        this.cmCpBox6 = cmCpBox6;
    }

    public void setCmApoTotal6(int cmApoTotal6) {
        this.cmApoTotal6 = cmApoTotal6;
    }

    public void setCmFaSum6(int cmFaSum6) {
        this.cmFaSum6 = cmFaSum6;
    }

    public void setSalesAch6(int salesAch6) {
        this.salesAch6 = salesAch6;
    }

    public void setSalesRatio6(float salesRatio6) {
        this.salesRatio6 = salesRatio6;
    }

    public void setCmOwnRefs6(int cmOwnRefs6) {
        this.cmOwnRefs6 = cmOwnRefs6;
    }

    public void setCmOutAgpOut(int cmOutAgpOut) {
        this.cmOutAgpOut = cmOutAgpOut;
    }

    public void setCmInAgpOut6(int cmInAgpOut6) {
        this.cmInAgpOut6 = cmInAgpOut6;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public int getgYear() {
        return gYear;
    }

    public int getgMonth() {
        return gMonth;
    }

    public int getNewSalesRevenue() {
        return newSalesRevenue;
    }

    public int getDuesDraftRevenue() {
        return duesDraftRevenue;
    }

    public int getProductsRevenue() {
        return productsRevenue;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getCmPostFlyer6() {
        return cmPostFlyer6;
    }

    public int getCmHandFlyer6() {
        return cmHandFlyer6;
    }

    public float getCmHandFlyerHours6() {
        return cmHandFlyerHours6;
    }

    public int getCmOutGp6() {
        return cmOutGp6;
    }

    public float getCmOutGpHours6() {
        return cmOutGpHours6;
    }

    public int getCmCpBox6() {
        return cmCpBox6;
    }

    public int getCmApoTotal6() {
        return cmApoTotal6;
    }

    public int getCmFaSum6() {
        return cmFaSum6;
    }

    public int getSalesAch6() {
        return salesAch6;
    }

    public float getSalesRatio6() {
        return salesRatio6;
    }

    public int getCmOwnRefs6() {
        return cmOwnRefs6;
    }

    public int getCmOutAgpOut() {
        return cmOutAgpOut;
    }

    public int getCmInAgpOut6() {
        return cmInAgpOut6;
    }
}
