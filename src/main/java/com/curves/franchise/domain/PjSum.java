package com.curves.franchise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class PjSum implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    private Date lastModified;

    @OneToMany(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "pj_sum_id", referencedColumnName="id")
    @RestResource(exported = false)
    private List<Pj> pjSet = new ArrayList<>(35);

    private int clubId;
    private int year; // 2014, 2013, ...
    private int month; // Jan=0, Feb=1, ..., Dec=11

    private int newSales;
    private int shiftIn;
    private int shiftOut;
    private int increment;
    private int revenue;
    private int enrolled;
    private int leaves;
    private int valids;
    private float salesRatio;
    private float exitRatio;
    private float leaveRatio;

    private float workingDays;
    private int maxWorkOuts;
    private int newSalesRevenue;
    private int duesDraftsRevenue;
    private int productsRevenue;
    private int wheyProteinRevenue;
    private int otherRevenue;
    private int incomingCalls;
    private int incomingApo;
    private int outgoingCalls;
    private int outgoingApo;
    private int brOwnRef; // composited brOwn+brOthers into BRs in old fmt
    private int brOthersRef;
    private int brandedNewspaper;
    private int brandedTv;
    private int brandedInternet;
    private int brandedSign;
    private int brandedMate;
    private int brandedOthers;
    private int agpInDirectMail;
    private int agpInMailFlyer;
    private int agpInHandFlyer;
    private int agpInCp;
    private int agpOutApoOut;
    private int agpOutApoIn;
    private int agpOutApoBlog;
    private int agpOutApoBag;
    private int faSum;
    private int enrollAch;
    private int enrollMonthly;
    private int enrollAllPrepay;
    private int exits;

    public PjSum() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValids(int valids) {
        this.valids = valids;
    }

    public int getValids() {
        return valids;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public void setPjSet(List<Pj> pjSet) {
        this.pjSet = pjSet;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setNewSales(int newSales) {
        this.newSales = newSales;
    }

    public void setShiftIn(int shiftIn) {
        this.shiftIn = shiftIn;
    }

    public void setShiftOut(int shiftOut) {
        this.shiftOut = shiftOut;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    public void setLeaves(int leave) {
        this.leaves = leave;
    }

    public void setSalesRatio(float salesRatio) {
        this.salesRatio = salesRatio;
    }

    public void setExitRatio(float exitRatio) {
        this.exitRatio = exitRatio;
    }

    public void setLeaveRatio(float leaveRatio) {
        this.leaveRatio = leaveRatio;
    }

    public void setWorkingDays(float workingDays) {
        this.workingDays = workingDays;
    }

    public void setMaxWorkOuts(int maxWorkOuts) {
        this.maxWorkOuts = maxWorkOuts;
    }

    public void setNewSalesRevenue(int newSalesRevenue) {
        this.newSalesRevenue = newSalesRevenue;
    }

    public void setDuesDraftsRevenue(int duesDraftsRevenue) {
        this.duesDraftsRevenue = duesDraftsRevenue;
    }

    public void setProductsRevenue(int productsRevenue) {
        this.productsRevenue = productsRevenue;
    }

    public void setWheyProteinRevenue(int wheyProteinRevenue) {
        this.wheyProteinRevenue = wheyProteinRevenue;
    }

    public void setOtherRevenue(int otherRevenue) {
        this.otherRevenue = otherRevenue;
    }

    public void setIncomingCalls(int incomingCalls) {
        this.incomingCalls = incomingCalls;
    }

    public void setIncomingApo(int incomingApo) {
        this.incomingApo = incomingApo;
    }

    public void setOutgoingCalls(int outgoingCalls) {
        this.outgoingCalls = outgoingCalls;
    }

    public void setOutgoingApo(int outgoingApo) {
        this.outgoingApo = outgoingApo;
    }

    public void setBrOwnRef(int brOwnRef) {
        this.brOwnRef = brOwnRef;
    }

    public void setBrOthersRef(int brOthersRef) {
        this.brOthersRef = brOthersRef;
    }

    public void setBrandedNewspaper(int brandedNewspaper) {
        this.brandedNewspaper = brandedNewspaper;
    }

    public void setBrandedTv(int brandedTv) {
        this.brandedTv = brandedTv;
    }

    public void setBrandedInternet(int brandedInternet) {
        this.brandedInternet = brandedInternet;
    }

    public void setBrandedSign(int brandedSign) {
        this.brandedSign = brandedSign;
    }

    public void setBrandedMate(int brandedMate) {
        this.brandedMate = brandedMate;
    }

    public void setBrandedOthers(int brandedOthers) {
        this.brandedOthers = brandedOthers;
    }

    public void setAgpInDirectMail(int agpInDirectMail) {
        this.agpInDirectMail = agpInDirectMail;
    }

    public void setAgpInMailFlyer(int agpInMailFlyer) {
        this.agpInMailFlyer = agpInMailFlyer;
    }

    public void setAgpInHandFlyer(int agpInHandFlyer) {
        this.agpInHandFlyer = agpInHandFlyer;
    }

    public void setAgpInCp(int agpInCp) {
        this.agpInCp = agpInCp;
    }

    public void setAgpOutApoOut(int agpOutApoOut) {
        this.agpOutApoOut = agpOutApoOut;
    }

    public void setAgpOutApoIn(int agpOutApoIn) {
        this.agpOutApoIn = agpOutApoIn;
    }

    public void setAgpOutApoBlog(int agpOutApoBlog) {
        this.agpOutApoBlog = agpOutApoBlog;
    }

    public void setAgpOutApoBag(int agpOutApoBag) {
        this.agpOutApoBag = agpOutApoBag;
    }

    public void setFaSum(int faSum) {
        this.faSum = faSum;
    }

    public void setEnrollAch(int enrollAch) {
        this.enrollAch = enrollAch;
    }

    public void setEnrollMonthly(int enrollMonthly) {
        this.enrollMonthly = enrollMonthly;
    }

    public void setEnrollAllPrepay(int enrollAllPrepay) {
        this.enrollAllPrepay = enrollAllPrepay;
    }

    public void setExits(int exits) {
        this.exits = exits;
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

    public List<Pj> getPjSet() {
        return pjSet;
    }

    public int getClubId() {
        return clubId;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getNewSales() {
        return newSales;
    }

    public int getShiftIn() {
        return shiftIn;
    }

    public int getShiftOut() {
        return shiftOut;
    }

    public int getIncrement() {
        return increment;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public int getLeaves() {
        return leaves;
    }

    public float getSalesRatio() {
        return salesRatio;
    }

    public float getExitRatio() {
        return exitRatio;
    }

    public float getLeaveRatio() {
        return leaveRatio;
    }

    public float getWorkingDays() {
        return workingDays;
    }

    public int getMaxWorkOuts() {
        return maxWorkOuts;
    }

    public int getNewSalesRevenue() {
        return newSalesRevenue;
    }

    public int getDuesDraftsRevenue() {
        return duesDraftsRevenue;
    }

    public int getProductsRevenue() {
        return productsRevenue;
    }

    public int getWheyProteinRevenue() {
        return wheyProteinRevenue;
    }

    public int getOtherRevenue() {
        return otherRevenue;
    }

    public int getIncomingCalls() {
        return incomingCalls;
    }

    public int getIncomingApo() {
        return incomingApo;
    }

    public int getOutgoingCalls() {
        return outgoingCalls;
    }

    public int getOutgoingApo() {
        return outgoingApo;
    }

    public int getBrOwnRef() {
        return brOwnRef;
    }

    public int getBrOthersRef() {
        return brOthersRef;
    }

    public int getBrandedNewspaper() {
        return brandedNewspaper;
    }

    public int getBrandedTv() {
        return brandedTv;
    }

    public int getBrandedInternet() {
        return brandedInternet;
    }

    public int getBrandedSign() {
        return brandedSign;
    }

    public int getBrandedMate() {
        return brandedMate;
    }

    public int getBrandedOthers() {
        return brandedOthers;
    }

    public int getAgpInDirectMail() {
        return agpInDirectMail;
    }

    public int getAgpInMailFlyer() {
        return agpInMailFlyer;
    }

    public int getAgpInHandFlyer() {
        return agpInHandFlyer;
    }

    public int getAgpInCp() {
        return agpInCp;
    }

    public int getAgpOutApoOut() {
        return agpOutApoOut;
    }

    public int getAgpOutApoIn() {
        return agpOutApoIn;
    }

    public int getAgpOutApoBlog() {
        return agpOutApoBlog;
    }

    public int getAgpOutApoBag() {
        return agpOutApoBag;
    }

    public int getFaSum() {
        return faSum;
    }

    public int getEnrollAch() {
        return enrollAch;
    }

    public int getEnrollMonthly() {
        return enrollMonthly;
    }

    public int getEnrollAllPrepay() {
        return enrollAllPrepay;
    }

    public int getExits() {
        return exits;
    }
}
