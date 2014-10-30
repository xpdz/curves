package com.curves.franchise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Ca implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    private Date lastModified;

    private int clubId;
    private int year; // 2014, 2013, ...
    private int month; // Jan=0, Feb=1, ..., Dec=11

    // wk, 1, 2, 3, 4, 5, monthly
    private String col = "";

    // for all below fields, if -1 fill a star on Wk cell, if -2 don't.
    // any other values will filled to 1,2,3,4,5 and monthly cell
    private int goalsTm;
    private int goalsLastTm;
    private int goalsLastActive;
    private String goalsLastShowRatio;
    private String goalsLastSalesRatio;
    private String goalsExitsRatio;
    private int goalsNewSales;
    private int goalsAppoints;

    // service
    private int serviceTm;
    private int serviceShift;
    private int serviceHold;
    private int serviceActive;
    private String serviceHoldRatio;
    private int serviceTotalWo;
    private int serviceAvgWo;
    private int serviceMaxWo;
    private int serviceExits;
    private String serviceExitsRatio;
    private int serviceMeasure;
    private String serviceMeasureRatio;
    private int service12;
    private int service8to11;
    private int service4to7;
    private int service1to3;
    private int service0;
    private int service3More;
    private int serviceInactive;
    private int serviceFwoReview;
    private int serviceInterview;
    private int serviceThanks;
    private int service3C;
    private int serviceReward;
    private int serviceLoyal;
    // customer make
    private int cmLeadMailFlyer;
    private int cmLeadHandFlyer;
    private int cmLeadOut;
    private int cmLeadCp;
    private int cmLeadOutGot;
    private int cmLeadInGot;
    private int cmLeadBlogGot;
    private int cmLeadBagGot;
    private int cmLeadTotal;
    private int cmCallsTotal;
    private int cmCallsOutGot;
    private int cmCallsInGot;
    private int cmCallsBlogGot;
    private int cmCallsBagGot;
    private int cmOwnRefs;
    private int cmOtherRefs;
    private int cmNewspaper;
    private int cmTv;
    private int cmInternet;
    private int cmSign;
    private int cmMate;
    private int cmOthers;
    private int cmAgpInDirectMail;
    private int cmAgpInMailFlyer;
    private int cmAgpInHandFlyer;
    private int cmAgpInCp;
    private int cmAgpOutApoOut;
    private int cmAgpOutApoIn;
    private int cmAgpOutApoBlog;
    private int cmAgpOutApoBag;
    private int cmApoTotal;
    private String cmInApoRatio;
    private String cmOutApoRatio;
    private int cmMailPerApo;
    private int cmHandPerApo;
    private int cmOutPerApo;
    private String cmBrAgpRatio;
    private int cmFaSum;
    private String cmShowRatio;
    private int cmTraining;
    private int cmGot3;
    private int cmInvitation;
    // sales
    private int salesAch;
    private int salesMonthly;
    private int salesAllPrepay;
    private int salesTotal;
    private String salesRatio;
    private String salesAchAppRatio;
    private int salesFaReview;
    private int salesPriceReview;
    private int salesAck;
    private int salesTarget;
    private int salesMotivation;
    private int salesObstacle;
    // management
    private int mgmtMeeting;
    private int mgmtCa;
    private int mgmtGp;
    private int mgmtLearn;
    private int mgmtSheet;
    private int mgmtPolicy;
    private int mgmtCompiantSales;
    private int mgmtCompiantMethod;
    private int mgmtCompiantProduct;
    private int mgmtCompiantAd;
    private int mgmtTraining;
    private int mgmtReport;
    private int mgmtPlan;
    private int mgmtMaintain;
    private int mgmtFace2Face;

    private String clubSalesRatio;
    private String clubAchAppRatio;
    private int clubAch;
    private int clubMm;
    private int clubApp;
    private int clubNs;
    private int clubLx;

    @JoinColumn(name = "staff1", referencedColumnName="id")
    @RestResource(exported = false)
    private StaffEval staff1;
    @JoinColumn(name = "staff2", referencedColumnName="id")
    @RestResource(exported = false)
    private StaffEval staff2;
    @JoinColumn(name = "staff3", referencedColumnName="id")
    @RestResource(exported = false)
    private StaffEval staff3;
    @JoinColumn(name = "staff4", referencedColumnName="id")
    @RestResource(exported = false)
    private StaffEval staff4;
    @JoinColumn(name = "staff5", referencedColumnName="id")
    @RestResource(exported = false)
    private StaffEval staff5;
    @JoinColumn(name = "staff6", referencedColumnName="id")
    @RestResource(exported = false)
    private StaffEval staff6;

    private String thisPlan;
    private String nextPlan;

    public void setStaff1(StaffEval staff1) {
        this.staff1 = staff1;
    }

    public void setStaff2(StaffEval staff2) {
        this.staff2 = staff2;
    }

    public void setStaff3(StaffEval staff3) {
        this.staff3 = staff3;
    }

    public void setStaff4(StaffEval staff4) {
        this.staff4 = staff4;
    }

    public void setStaff5(StaffEval staff5) {
        this.staff5 = staff5;
    }

    public void setStaff6(StaffEval staff6) {
        this.staff6 = staff6;
    }

    public StaffEval getStaff1() {
        return staff1;
    }

    public StaffEval getStaff2() {
        return staff2;
    }

    public StaffEval getStaff3() {
        return staff3;
    }

    public StaffEval getStaff4() {
        return staff4;
    }

    public StaffEval getStaff5() {
        return staff5;
    }

    public StaffEval getStaff6() {
        return staff6;
    }

    public void setThisPlan(String thisPlan) {
        this.thisPlan = thisPlan;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public String getThisPlan() {
        return thisPlan;
    }

    public String getNextPlan() {
        return nextPlan;
    }

    public void setCmOutPerApo(int cmOutPerApo) {
        this.cmOutPerApo = cmOutPerApo;
    }

    public int getCmOutPerApo() {
        return cmOutPerApo;
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

    public void setCol(String col) {
        this.col = col;
    }

    public void setGoalsTm(int goalsTm) {
        this.goalsTm = goalsTm;
    }

    public void setGoalsLastTm(int goalsLastTm) {
        this.goalsLastTm = goalsLastTm;
    }

    public void setGoalsLastActive(int goalsLastActive) {
        this.goalsLastActive = goalsLastActive;
    }

    public void setGoalsLastShowRatio(String goalsLastShowRatio) {
        this.goalsLastShowRatio = goalsLastShowRatio;
    }

    public void setGoalsLastSalesRatio(String goalsLastSalesRatio) {
        this.goalsLastSalesRatio = goalsLastSalesRatio;
    }

    public void setGoalsExitsRatio(String goalsExitsRatio) {
        this.goalsExitsRatio = goalsExitsRatio;
    }

    public void setGoalsNewSales(int goalsNewSales) {
        this.goalsNewSales = goalsNewSales;
    }

    public void setGoalsAppoints(int goalsAppoints) {
        this.goalsAppoints = goalsAppoints;
    }

    public void setServiceTm(int serviceTm) {
        this.serviceTm = serviceTm;
    }

    public void setServiceShift(int serviceShift) {
        this.serviceShift = serviceShift;
    }

    public void setServiceHold(int serviceHold) {
        this.serviceHold = serviceHold;
    }

    public void setServiceActive(int serviceActive) {
        this.serviceActive = serviceActive;
    }

    public void setServiceHoldRatio(String serviceHoldRatio) {
        this.serviceHoldRatio = serviceHoldRatio;
    }

    public void setServiceTotalWo(int serviceTotalWo) {
        this.serviceTotalWo = serviceTotalWo;
    }

    public void setServiceAvgWo(int serviceAvgWo) {
        this.serviceAvgWo = serviceAvgWo;
    }

    public void setServiceMaxWo(int serviceMaxWo) {
        this.serviceMaxWo = serviceMaxWo;
    }

    public void setServiceExits(int serviceExits) {
        this.serviceExits = serviceExits;
    }

    public void setServiceExitsRatio(String serviceExitsRatio) {
        this.serviceExitsRatio = serviceExitsRatio;
    }

    public void setServiceMeasure(int serviceMeasure) {
        this.serviceMeasure = serviceMeasure;
    }

    public void setServiceMeasureRatio(String serviceMeasureRatio) {
        this.serviceMeasureRatio = serviceMeasureRatio;
    }

    public void setService12(int service12) {
        this.service12 = service12;
    }

    public void setService8to11(int service8to11) {
        this.service8to11 = service8to11;
    }

    public void setService4to7(int service4to7) {
        this.service4to7 = service4to7;
    }

    public void setService1to3(int service1to3) {
        this.service1to3 = service1to3;
    }

    public void setService0(int service0) {
        this.service0 = service0;
    }

    public void setService3More(int service3More) {
        this.service3More = service3More;
    }

    public void setServiceInactive(int serviceInactive) {
        this.serviceInactive = serviceInactive;
    }

    public void setServiceFwoReview(int serviceFwoReview) {
        this.serviceFwoReview = serviceFwoReview;
    }

    public void setServiceInterview(int serviceInterview) {
        this.serviceInterview = serviceInterview;
    }

    public void setServiceThanks(int serviceThanks) {
        this.serviceThanks = serviceThanks;
    }

    public void setService3C(int service3C) {
        this.service3C = service3C;
    }

    public void setServiceReward(int serviceReward) {
        this.serviceReward = serviceReward;
    }

    public void setServiceLoyal(int serviceLoyal) {
        this.serviceLoyal = serviceLoyal;
    }

    public void setCmLeadMailFlyer(int cmLeadMailFlyer) {
        this.cmLeadMailFlyer = cmLeadMailFlyer;
    }

    public void setCmLeadHandFlyer(int cmLeadHandFlyer) {
        this.cmLeadHandFlyer = cmLeadHandFlyer;
    }

    public void setCmLeadOut(int cmLeadOut) {
        this.cmLeadOut = cmLeadOut;
    }

    public void setCmLeadCp(int cmLeadCp) {
        this.cmLeadCp = cmLeadCp;
    }

    public void setCmLeadOutGot(int cmLeadOutGot) {
        this.cmLeadOutGot = cmLeadOutGot;
    }

    public void setCmLeadInGot(int cmLeadInGot) {
        this.cmLeadInGot = cmLeadInGot;
    }

    public void setCmLeadBlogGot(int cmLeadBlogGot) {
        this.cmLeadBlogGot = cmLeadBlogGot;
    }

    public void setCmLeadBagGot(int cmLeadBagGot) {
        this.cmLeadBagGot = cmLeadBagGot;
    }

    public void setCmLeadTotal(int cmLeadTotal) {
        this.cmLeadTotal = cmLeadTotal;
    }

    public void setCmCallsTotal(int cmCallsTotal) {
        this.cmCallsTotal = cmCallsTotal;
    }

    public void setCmCallsOutGot(int cmCallsOutGot) {
        this.cmCallsOutGot = cmCallsOutGot;
    }

    public void setCmCallsInGot(int cmCallsInGot) {
        this.cmCallsInGot = cmCallsInGot;
    }

    public void setCmCallsBlogGot(int cmCallsBlogGot) {
        this.cmCallsBlogGot = cmCallsBlogGot;
    }

    public void setCmCallsBagGot(int cmCallsBagGot) {
        this.cmCallsBagGot = cmCallsBagGot;
    }

    public void setCmOwnRefs(int cmOwnRefs) {
        this.cmOwnRefs = cmOwnRefs;
    }

    public void setCmOtherRefs(int cmOtherRefs) {
        this.cmOtherRefs = cmOtherRefs;
    }

    public void setCmNewspaper(int cmNewspaper) {
        this.cmNewspaper = cmNewspaper;
    }

    public void setCmTv(int cmTv) {
        this.cmTv = cmTv;
    }

    public void setCmInternet(int cmInternet) {
        this.cmInternet = cmInternet;
    }

    public void setCmSign(int cmSign) {
        this.cmSign = cmSign;
    }

    public void setCmMate(int cmMate) {
        this.cmMate = cmMate;
    }

    public void setCmOthers(int cmOthers) {
        this.cmOthers = cmOthers;
    }

    public void setCmAgpInDirectMail(int cmAgpInDirectMail) {
        this.cmAgpInDirectMail = cmAgpInDirectMail;
    }

    public void setCmAgpInMailFlyer(int cmAgpInMailFlyer) {
        this.cmAgpInMailFlyer = cmAgpInMailFlyer;
    }

    public void setCmAgpInHandFlyer(int cmAgpInHandFlyer) {
        this.cmAgpInHandFlyer = cmAgpInHandFlyer;
    }

    public void setCmAgpInCp(int cmAgpInCp) {
        this.cmAgpInCp = cmAgpInCp;
    }

    public void setCmAgpOutApoOut(int cmAgpOutApoOut) {
        this.cmAgpOutApoOut = cmAgpOutApoOut;
    }

    public void setCmAgpOutApoIn(int cmAgpOutApoIn) {
        this.cmAgpOutApoIn = cmAgpOutApoIn;
    }

    public void setCmAgpOutApoBlog(int cmAgpOutApoBlog) {
        this.cmAgpOutApoBlog = cmAgpOutApoBlog;
    }

    public void setCmAgpOutApoBag(int cmAgpOutApoBag) {
        this.cmAgpOutApoBag = cmAgpOutApoBag;
    }

    public void setCmApoTotal(int cmApoTotal) {
        this.cmApoTotal = cmApoTotal;
    }

    public void setCmInApoRatio(String cmInApoRatio) {
        this.cmInApoRatio = cmInApoRatio;
    }

    public void setCmOutApoRatio(String cmOutApoRatio) {
        this.cmOutApoRatio = cmOutApoRatio;
    }

    public void setCmMailPerApo(int cmMailPerApo) {
        this.cmMailPerApo = cmMailPerApo;
    }

    public void setCmHandPerApo(int cmHandPerApo) {
        this.cmHandPerApo = cmHandPerApo;
    }

    public void setCmBrAgpRatio(String cmBrAgpRatio) {
        this.cmBrAgpRatio = cmBrAgpRatio;
    }

    public void setCmFaSum(int cmFaSum) {
        this.cmFaSum = cmFaSum;
    }

    public void setCmShowRatio(String cmShowRatio) {
        this.cmShowRatio = cmShowRatio;
    }

    public void setCmTraining(int cmTraining) {
        this.cmTraining = cmTraining;
    }

    public void setCmGot3(int cmGot3) {
        this.cmGot3 = cmGot3;
    }

    public void setCmInvitation(int cmInvitation) {
        this.cmInvitation = cmInvitation;
    }

    public void setSalesAch(int salesAch) {
        this.salesAch = salesAch;
    }

    public void setSalesMonthly(int salesMonthly) {
        this.salesMonthly = salesMonthly;
    }

    public void setSalesAllPrepay(int salesAllPrepay) {
        this.salesAllPrepay = salesAllPrepay;
    }

    public void setSalesTotal(int salesTotal) {
        this.salesTotal = salesTotal;
    }

    public void setSalesRatio(String salesRatio) {
        this.salesRatio = salesRatio;
    }

    public void setSalesAchAppRatio(String salesAchAppRatio) {
        this.salesAchAppRatio = salesAchAppRatio;
    }

    public void setSalesFaReview(int salesFaReview) {
        this.salesFaReview = salesFaReview;
    }

    public void setSalesPriceReview(int salesPriceReview) {
        this.salesPriceReview = salesPriceReview;
    }

    public void setSalesAck(int salesAck) {
        this.salesAck = salesAck;
    }

    public void setSalesTarget(int salesTarget) {
        this.salesTarget = salesTarget;
    }

    public void setSalesMotivation(int salesMotivation) {
        this.salesMotivation = salesMotivation;
    }

    public void setSalesObstacle(int salesObstacle) {
        this.salesObstacle = salesObstacle;
    }

    public void setMgmtMeeting(int mgmtMeeting) {
        this.mgmtMeeting = mgmtMeeting;
    }

    public void setMgmtCa(int mgmtCa) {
        this.mgmtCa = mgmtCa;
    }

    public void setMgmtGp(int mgmtGp) {
        this.mgmtGp = mgmtGp;
    }

    public void setMgmtLearn(int mgmtLearn) {
        this.mgmtLearn = mgmtLearn;
    }

    public void setMgmtSheet(int mgmtSheet) {
        this.mgmtSheet = mgmtSheet;
    }

    public void setMgmtPolicy(int mgmtPolicy) {
        this.mgmtPolicy = mgmtPolicy;
    }

    public void setMgmtCompiantSales(int mgmtCompiantSales) {
        this.mgmtCompiantSales = mgmtCompiantSales;
    }

    public void setMgmtCompiantMethod(int mgmtCompiantMethod) {
        this.mgmtCompiantMethod = mgmtCompiantMethod;
    }

    public void setMgmtCompiantProduct(int mgmtCompiantProduct) {
        this.mgmtCompiantProduct = mgmtCompiantProduct;
    }

    public void setMgmtCompiantAd(int mgmtCompiantAd) {
        this.mgmtCompiantAd = mgmtCompiantAd;
    }

    public void setMgmtTraining(int mgmtTraining) {
        this.mgmtTraining = mgmtTraining;
    }

    public void setMgmtReport(int mgmtReport) {
        this.mgmtReport = mgmtReport;
    }

    public void setMgmtPlan(int mgmtPlan) {
        this.mgmtPlan = mgmtPlan;
    }

    public void setMgmtMaintain(int mgmtMaintain) {
        this.mgmtMaintain = mgmtMaintain;
    }

    public void setMgmtFace2Face(int mgmtFace2Face) {
        this.mgmtFace2Face = mgmtFace2Face;
    }

    public void setClubSalesRatio(String clubSalesRatio) {
        this.clubSalesRatio = clubSalesRatio;
    }

    public void setClubAchAppRatio(String clubAchAppRatio) {
        this.clubAchAppRatio = clubAchAppRatio;
    }

    public void setClubAch(int clubAch) {
        this.clubAch = clubAch;
    }

    public void setClubMm(int clubMm) {
        this.clubMm = clubMm;
    }

    public void setClubApp(int clubApp) {
        this.clubApp = clubApp;
    }

    public void setClubNs(int clubNs) {
        this.clubNs = clubNs;
    }

    public void setClubLx(int clubLx) {
        this.clubLx = clubLx;
    }

    public Long getId() {
        return id;
    }

    public Date getLastModified() {
        return lastModified;
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

    public String getCol() {
        return col;
    }

    public int getGoalsTm() {
        return goalsTm;
    }

    public int getGoalsLastTm() {
        return goalsLastTm;
    }

    public int getGoalsLastActive() {
        return goalsLastActive;
    }

    public String getGoalsLastShowRatio() {
        return goalsLastShowRatio;
    }

    public String getGoalsLastSalesRatio() {
        return goalsLastSalesRatio;
    }

    public String getGoalsExitsRatio() {
        return goalsExitsRatio;
    }

    public int getGoalsNewSales() {
        return goalsNewSales;
    }

    public int getGoalsAppoints() {
        return goalsAppoints;
    }

    public int getServiceTm() {
        return serviceTm;
    }

    public int getServiceShift() {
        return serviceShift;
    }

    public int getServiceHold() {
        return serviceHold;
    }

    public int getServiceActive() {
        return serviceActive;
    }

    public String getServiceHoldRatio() {
        return serviceHoldRatio;
    }

    public int getServiceTotalWo() {
        return serviceTotalWo;
    }

    public int getServiceAvgWo() {
        return serviceAvgWo;
    }

    public int getServiceMaxWo() {
        return serviceMaxWo;
    }

    public int getServiceExits() {
        return serviceExits;
    }

    public String getServiceExitsRatio() {
        return serviceExitsRatio;
    }

    public int getServiceMeasure() {
        return serviceMeasure;
    }

    public String getServiceMeasureRatio() {
        return serviceMeasureRatio;
    }

    public int getService12() {
        return service12;
    }

    public int getService8to11() {
        return service8to11;
    }

    public int getService4to7() {
        return service4to7;
    }

    public int getService1to3() {
        return service1to3;
    }

    public int getService0() {
        return service0;
    }

    public int getService3More() {
        return service3More;
    }

    public int getServiceInactive() {
        return serviceInactive;
    }

    public int getServiceFwoReview() {
        return serviceFwoReview;
    }

    public int getServiceInterview() {
        return serviceInterview;
    }

    public int getServiceThanks() {
        return serviceThanks;
    }

    public int getService3C() {
        return service3C;
    }

    public int getServiceReward() {
        return serviceReward;
    }

    public int getServiceLoyal() {
        return serviceLoyal;
    }

    public int getCmLeadMailFlyer() {
        return cmLeadMailFlyer;
    }

    public int getCmLeadHandFlyer() {
        return cmLeadHandFlyer;
    }

    public int getCmLeadOut() {
        return cmLeadOut;
    }

    public int getCmLeadCp() {
        return cmLeadCp;
    }

    public int getCmLeadOutGot() {
        return cmLeadOutGot;
    }

    public int getCmLeadInGot() {
        return cmLeadInGot;
    }

    public int getCmLeadBlogGot() {
        return cmLeadBlogGot;
    }

    public int getCmLeadBagGot() {
        return cmLeadBagGot;
    }

    public int getCmLeadTotal() {
        return cmLeadTotal;
    }

    public int getCmCallsTotal() {
        return cmCallsTotal;
    }

    public int getCmCallsOutGot() {
        return cmCallsOutGot;
    }

    public int getCmCallsInGot() {
        return cmCallsInGot;
    }

    public int getCmCallsBlogGot() {
        return cmCallsBlogGot;
    }

    public int getCmCallsBagGot() {
        return cmCallsBagGot;
    }

    public int getCmOwnRefs() {
        return cmOwnRefs;
    }

    public int getCmOtherRefs() {
        return cmOtherRefs;
    }

    public int getCmNewspaper() {
        return cmNewspaper;
    }

    public int getCmTv() {
        return cmTv;
    }

    public int getCmInternet() {
        return cmInternet;
    }

    public int getCmSign() {
        return cmSign;
    }

    public int getCmMate() {
        return cmMate;
    }

    public int getCmOthers() {
        return cmOthers;
    }

    public int getCmAgpInDirectMail() {
        return cmAgpInDirectMail;
    }

    public int getCmAgpInMailFlyer() {
        return cmAgpInMailFlyer;
    }

    public int getCmAgpInHandFlyer() {
        return cmAgpInHandFlyer;
    }

    public int getCmAgpInCp() {
        return cmAgpInCp;
    }

    public int getCmAgpOutApoOut() {
        return cmAgpOutApoOut;
    }

    public int getCmAgpOutApoIn() {
        return cmAgpOutApoIn;
    }

    public int getCmAgpOutApoBlog() {
        return cmAgpOutApoBlog;
    }

    public int getCmAgpOutApoBag() {
        return cmAgpOutApoBag;
    }

    public int getCmApoTotal() {
        return cmApoTotal;
    }

    public String getCmInApoRatio() {
        return cmInApoRatio;
    }

    public String getCmOutApoRatio() {
        return cmOutApoRatio;
    }

    public int getCmMailPerApo() {
        return cmMailPerApo;
    }

    public int getCmHandPerApo() {
        return cmHandPerApo;
    }

    public String getCmBrAgpRatio() {
        return cmBrAgpRatio;
    }

    public int getCmFaSum() {
        return cmFaSum;
    }

    public String getCmShowRatio() {
        return cmShowRatio;
    }

    public int getCmTraining() {
        return cmTraining;
    }

    public int getCmGot3() {
        return cmGot3;
    }

    public int getCmInvitation() {
        return cmInvitation;
    }

    public int getSalesAch() {
        return salesAch;
    }

    public int getSalesMonthly() {
        return salesMonthly;
    }

    public int getSalesAllPrepay() {
        return salesAllPrepay;
    }

    public int getSalesTotal() {
        return salesTotal;
    }

    public String getSalesRatio() {
        return salesRatio;
    }

    public String getSalesAchAppRatio() {
        return salesAchAppRatio;
    }

    public int getSalesFaReview() {
        return salesFaReview;
    }

    public int getSalesPriceReview() {
        return salesPriceReview;
    }

    public int getSalesAck() {
        return salesAck;
    }

    public int getSalesTarget() {
        return salesTarget;
    }

    public int getSalesMotivation() {
        return salesMotivation;
    }

    public int getSalesObstacle() {
        return salesObstacle;
    }

    public int getMgmtMeeting() {
        return mgmtMeeting;
    }

    public int getMgmtCa() {
        return mgmtCa;
    }

    public int getMgmtGp() {
        return mgmtGp;
    }

    public int getMgmtLearn() {
        return mgmtLearn;
    }

    public int getMgmtSheet() {
        return mgmtSheet;
    }

    public int getMgmtPolicy() {
        return mgmtPolicy;
    }

    public int getMgmtCompiantSales() {
        return mgmtCompiantSales;
    }

    public int getMgmtCompiantMethod() {
        return mgmtCompiantMethod;
    }

    public int getMgmtCompiantProduct() {
        return mgmtCompiantProduct;
    }

    public int getMgmtCompiantAd() {
        return mgmtCompiantAd;
    }

    public int getMgmtTraining() {
        return mgmtTraining;
    }

    public int getMgmtReport() {
        return mgmtReport;
    }

    public int getMgmtPlan() {
        return mgmtPlan;
    }

    public int getMgmtMaintain() {
        return mgmtMaintain;
    }

    public int getMgmtFace2Face() {
        return mgmtFace2Face;
    }

    public String getClubSalesRatio() {
        return clubSalesRatio;
    }

    public String getClubAchAppRatio() {
        return clubAchAppRatio;
    }

    public int getClubAch() {
        return clubAch;
    }

    public int getClubMm() {
        return clubMm;
    }

    public int getClubApp() {
        return clubApp;
    }

    public int getClubNs() {
        return clubNs;
    }

    public int getClubLx() {
        return clubLx;
    }
}
