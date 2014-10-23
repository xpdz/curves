package com.curves.franchise.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Pj implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private Date lastModified;

    @ManyToOne
    @JoinColumn(name = "pj_sum_id")
    private PjSum pjSum;

    private Date date;

    private float workingDays;
    private int workOuts;
    private int newSalesRevenue;
    private int productsRevenue;
    private int duesDraftsRevenue;
    private int otherRevenue; // not present in old fmt
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
    private int fa;
    private int enrollAch;
    private int enrollMonthly;
    private int enrollAllPrepay;
    private int exits;

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setWorkingDays(float workingDays) {
        this.workingDays = workingDays;
    }

    public void setWorkOuts(int workOuts) {
        this.workOuts = workOuts;
    }

    public void setNewSalesRevenue(int newSalesRevenue) {
        this.newSalesRevenue = newSalesRevenue;
    }

    public void setProductsRevenue(int productsRevenue) {
        this.productsRevenue = productsRevenue;
    }

    public void setDuesDraftsRevenue(int duesDraftsRevenue) {
        this.duesDraftsRevenue = duesDraftsRevenue;
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

    public void setFa(int fa) {
        this.fa = fa;
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

    public Date getDate() {
        return date;
    }

    public float getWorkingDays() {
        return workingDays;
    }

    public int getWorkOuts() {
        return workOuts;
    }

    public int getNewSalesRevenue() {
        return newSalesRevenue;
    }

    public int getProductsRevenue() {
        return productsRevenue;
    }

    public int getDuesDraftsRevenue() {
        return duesDraftsRevenue;
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

    public int getFa() {
        return fa;
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
