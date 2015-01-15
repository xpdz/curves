package com.curves.franchise.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private int caYear; // 2014, 2013, ...
    private int caMonth; // Jan=0, Feb=1, ..., Dec=11

    private int goalsTm;
    private int goalsLastTm;
    private int goalsLastActive;
    private float goalsLastShowRatio;
    private float goalsLastSalesRatio;
    private float goalsExitsRatio;
    private int goalsNewSales;
    private int goalsAppoints;

    // service
    private int svcTm1, svcTm2, svcTm3, svcTm4, svcTm5, svcTm6;
    private int svcShiftOut1, svcShiftOut2, svcShiftOut3, svcShiftOut4, svcShiftOut5, svcShiftOut6;
    private int svcShiftIn1, svcShiftIn2, svcShiftIn3, svcShiftIn4, svcShiftIn5, svcShiftIn6;
    private int svcHold1, svcHold2, svcHold3, svcHold4, svcHold5, svcHold6;
    private int svcActive1, svcActive2, svcActive3, svcActive4, svcActive5, svcActive6;
    private float svcHoldRatio1, svcHoldRatio2, svcHoldRatio3, svcHoldRatio4, svcHoldRatio5, svcHoldRatio6;
    private int svcTotalWo1, svcTotalWo2, svcTotalWo3, svcTotalWo4, svcTotalWo5, svcTotalWo6;
    private float svcAvgWo1, svcAvgWo2, svcAvgWo3, svcAvgWo4, svcAvgWo5, svcAvgWo6;
    private int svcMaxWo1, svcMaxWo2, svcMaxWo3, svcMaxWo4, svcMaxWo5;
    private float svcMaxWo6;
    private int svcExits1, svcExits2, svcExits3, svcExits4, svcExits5, svcExits6;
    private float svcExitsRatio1, svcExitsRatio2, svcExitsRatio3, svcExitsRatio4, svcExitsRatio5, svcExitsRatio6;
    private int svcMeasure1, svcMeasure2, svcMeasure3, svcMeasure4, svcMeasure5, svcMeasure6;
    private float svcMeasureRatio1, svcMeasureRatio2, svcMeasureRatio3, svcMeasureRatio4, svcMeasureRatio5, svcMeasureRatio6;
    private int svc12_5;
    private float svc12_6;
    private int svc8to11_5;
    private float svc8to11_6;
    private int svc4to7_5;
    private float svc4to7_6;
    private int svc1to3_5;
    private float svc1to3_6;
    private int svc0_5;
    private float svc0_6;
    private String svc3More1, svc3More2, svc3More3, svc3More4, svc3More5;
    private String svcInactive1, svcInactive2, svcInactive3, svcInactive4, svcInactive5;
    private String svcFwoReview1, svcFwoReview2, svcFwoReview3, svcFwoReview4, svcFwoReview5;
    private String svcInterview1, svcInterview2, svcInterview3, svcInterview4, svcInterview5;
    private String svcThanks1, svcThanks2, svcThanks3, svcThanks4, svcThanks5;
    private String svc3C1, svc3C2, svc3C3, svc3C4, svc3C5;
    private String svcReward1, svcReward2, svcReward3, svcReward4, svcReward5;
    private String svcLoyal1, svcLoyal2, svcLoyal3, svcLoyal4, svcLoyal5;
    // customer make
    private int cmPostFlyer1, cmPostFlyer2, cmPostFlyer3, cmPostFlyer4, cmPostFlyer5, cmPostFlyer6;
    private int cmHandFlyer1, cmHandFlyer2, cmHandFlyer3, cmHandFlyer4, cmHandFlyer5, cmHandFlyer6;
    private float cmHandFlyerHours1, cmHandFlyerHours2, cmHandFlyerHours3, cmHandFlyerHours4, cmHandFlyerHours5, cmHandFlyerHours6;
    private float cmOutGpHours1, cmOutGpHours2, cmOutGpHours3, cmOutGpHours4, cmOutGpHours5, cmOutGpHours6;
    private int cmOutGp1, cmOutGp2, cmOutGp3, cmOutGp4, cmOutGp5, cmOutGp6;
    private int cmCpBox1, cmCpBox2, cmCpBox3, cmCpBox4, cmCpBox5, cmCpBox6;
    private int cmOutGot1, cmOutGot2, cmOutGot3, cmOutGot4, cmOutGot5, cmOutGot6;
    private int cmInGot1, cmInGot2, cmInGot3, cmInGot4, cmInGot5, cmInGot6;
    private int cmBlogGot1, cmBlogGot2, cmBlogGot3, cmBlogGot4, cmBlogGot5, cmBlogGot6;
    private int cmBagGot1, cmBagGot2, cmBagGot3, cmBagGot4, cmBagGot5, cmBagGot6;
    private int cmTotalGot1, cmTotalGot2, cmTotalGot3, cmTotalGot4, cmTotalGot5, cmTotalGot6;
    private int cmCallIn1, cmCallIn2, cmCallIn3, cmCallIn4, cmCallIn5, cmCallIn6;
    private int cmOutGotCall1, cmOutGotCall2, cmOutGotCall3, cmOutGotCall4, cmOutGotCall5, cmOutGotCall6;
    private int cmInGotCall1, cmInGotCall2, cmInGotCall3, cmInGotCall4, cmInGotCall5, cmInGotCall6;
    private int cmBlogGotCall1, cmBlogGotCall2, cmBlogGotCall3, cmBlogGotCall4, cmBlogGotCall5, cmBlogGotCall6;
    private int cmBagGotCall1, cmBagGotCall2, cmBagGotCall3, cmBagGotCall4, cmBagGotCall5, cmBagGotCall6;
    private int cmOwnRefs1, cmOwnRefs2, cmOwnRefs3, cmOwnRefs4, cmOwnRefs5, cmOwnRefs6;
    private int cmOtherRefs1, cmOtherRefs2, cmOtherRefs3, cmOtherRefs4, cmOtherRefs5, cmOtherRefs6;
    private int cmNewspaper1, cmNewspaper2, cmNewspaper3, cmNewspaper4, cmNewspaper5, cmNewspaper6;
    private int cmTv1, cmTv2, cmTv3, cmTv4, cmTv5, cmTv6;
    private int cmInternet1, cmInternet2, cmInternet3, cmInternet4, cmInternet5, cmInternet6;
    private int cmSign1, cmSign2, cmSign3, cmSign4, cmSign5, cmSign6;
    private int cmMate1, cmMate2, cmMate3, cmMate4, cmMate5, cmMate6;
    private int cmOthers1, cmOthers2, cmOthers3, cmOthers4, cmOthers5, cmOthers6;
    private int cmMailAgpIn1, cmMailAgpIn2, cmMailAgpIn3, cmMailAgpIn4, cmMailAgpIn5, cmMailAgpIn6;
    private int cmPostFlyerAgpIn1, cmPostFlyerAgpIn2, cmPostFlyerAgpIn3, cmPostFlyerAgpIn4, cmPostFlyerAgpIn5, cmPostFlyerAgpIn6;
    private int cmHandFlyerAgpIn1, cmHandFlyerAgpIn2, cmHandFlyerAgpIn3, cmHandFlyerAgpIn4, cmHandFlyerAgpIn5, cmHandFlyerAgpIn6;
    private int cmCpAgpIn1, cmCpAgpIn2, cmCpAgpIn3, cmCpAgpIn4, cmCpAgpIn5, cmCpAgpIn6;
    private int cmOutAgpOut1, cmOutAgpOut2, cmOutAgpOut3, cmOutAgpOut4, cmOutAgpOut5, cmOutAgpOut6;
    private int cmInAgpOut1, cmInAgpOut2, cmInAgpOut3, cmInAgpOut4, cmInAgpOut5, cmInAgpOut6;
    private int cmBlogAgpOut1, cmBlogAgpOut2, cmBlogAgpOut3, cmBlogAgpOut4, cmBlogAgpOut5, cmBlogAgpOut6;
    private int cmBagAgpOut1, cmBagAgpOut2, cmBagAgpOut3, cmBagAgpOut4, cmBagAgpOut5, cmBagAgpOut6;
    private int cmApoTotal1, cmApoTotal2, cmApoTotal3, cmApoTotal4, cmApoTotal5, cmApoTotal6;
    private float cmInApptRatio1, cmInApptRatio2, cmInApptRatio3, cmInApptRatio4, cmInApptRatio5, cmInApptRatio6;
    private float cmOutApptRatio1, cmOutApptRatio2, cmOutApptRatio3, cmOutApptRatio4, cmOutApptRatio5, cmOutApptRatio6;
    private int cmPostPerApo6;
    private int cmHandPerApo6;
    private float cmHandHoursPerApo6;
    private float cmOutGpPerApo6;
    private float cmOutGpHoursPerApo6;
    private float cmBrAgpRatio1, cmBrAgpRatio2, cmBrAgpRatio3, cmBrAgpRatio4, cmBrAgpRatio5, cmBrAgpRatio6;
    private int cmFaSum1, cmFaSum2, cmFaSum3, cmFaSum4, cmFaSum5, cmFaSum6;
    private float cmShowRatio1, cmShowRatio2, cmShowRatio3, cmShowRatio4, cmShowRatio5, cmShowRatio6;
    private String cmTraining1, cmTraining2, cmTraining3, cmTraining4, cmTraining5;
    private String cmGot3_1, cmGot3_2, cmGot3_3, cmGot3_4, cmGot3_5;
    private String cmInvitation1, cmInvitation2, cmInvitation3, cmInvitation4, cmInvitation5;
    // sales
    private int salesAch1, salesAch2, salesAch3, salesAch4, salesAch5, salesAch6;
    private int salesMonthly1, salesMonthly2, salesMonthly3, salesMonthly4, salesMonthly5, salesMonthly6;
    private int salesAllPrepay1, salesAllPrepay2, salesAllPrepay3, salesAllPrepay4, salesAllPrepay5, salesAllPrepay6;
    private int salesTotal1, salesTotal2, salesTotal3, salesTotal4, salesTotal5, salesTotal6;
    private float salesRatio1, salesRatio2, salesRatio3, salesRatio4, salesRatio5, salesRatio6;
    private float salesAchAppRatio1, salesAchAppRatio2, salesAchAppRatio3, salesAchAppRatio4, salesAchAppRatio5, salesAchAppRatio6;
    private String salesFaReview1, salesFaReview2, salesFaReview3, salesFaReview4, salesFaReview5;
    private String salesPriceReview1, salesPriceReview2, salesPriceReview3, salesPriceReview4, salesPriceReview5;
    private String salesAck1, salesAck2, salesAck3, salesAck4, salesAck5;
    private String salesTarget1, salesTarget2, salesTarget3, salesTarget4, salesTarget5;
    private String salesMotivation1, salesMotivation2, salesMotivation3, salesMotivation4, salesMotivation5;
    private String salesObstacle1, salesObstacle2, salesObstacle3, salesObstacle4, salesObstacle5;
    // management
    private String mgmtMeeting1, mgmtMeeting2, mgmtMeeting3, mgmtMeeting4, mgmtMeeting5;
    private String mgmtCa1, mgmtCa2, mgmtCa3, mgmtCa4, mgmtCa5;
    private String mgmtGp1, mgmtGp2, mgmtGp3, mgmtGp4, mgmtGp5;
    private String mgmtLearn1, mgmtLearn2, mgmtLearn3, mgmtLearn4, mgmtLearn5;
    private String mgmtSheet1, mgmtSheet2, mgmtSheet3, mgmtSheet4, mgmtSheet5;
    private String mgmtPolicy1, mgmtPolicy2, mgmtPolicy3, mgmtPolicy4, mgmtPolicy5;
    private String mgmtCompiantSales1, mgmtCompiantSales2, mgmtCompiantSales3, mgmtCompiantSales4, mgmtCompiantSales5;
    private String mgmtCompiantMethod1, mgmtCompiantMethod2, mgmtCompiantMethod3, mgmtCompiantMethod4, mgmtCompiantMethod5;
    private String mgmtCompiantProduct1, mgmtCompiantProduct2, mgmtCompiantProduct3, mgmtCompiantProduct4, mgmtCompiantProduct5;
    private String mgmtCompiantAd1, mgmtCompiantAd2, mgmtCompiantAd3, mgmtCompiantAd4, mgmtCompiantAd5;
    private String mgmtTraining1, mgmtTraining2, mgmtTraining3, mgmtTraining4, mgmtTraining5;
    private String mgmtReport1, mgmtReport2, mgmtReport3, mgmtReport4, mgmtReport5;
    private String mgmtPlan1, mgmtPlan2, mgmtPlan3, mgmtPlan4, mgmtPlan5;
    private String mgmtMaintain1, mgmtMaintain2, mgmtMaintain3, mgmtMaintain4, mgmtMaintain5;
    private String mgmtFace2Face1, mgmtFace2Face2, mgmtFace2Face3, mgmtFace2Face4, mgmtFace2Face5;

    private float clubSalesRatio;
    private float clubAchAppRatio;
    private int clubAch1, clubAch2, clubAch3, clubAch4, clubAch5, clubAch6;
    private int clubMm1, clubMm2, clubMm3, clubMm4, clubMm5, clubMm6;
    private int clubApp1, clubApp2, clubApp3, clubApp4, clubApp5, clubApp6;
    private int clubNs1, clubNs2, clubNs3, clubNs4, clubNs5, clubNs6;
    private int clubLx1, clubLx2, clubLx3, clubLx4, clubLx5, clubLx6;

    private String staff1Name;
    private float staff1SalesRatio;
    private float staff1AchAppRatio;
    private int staff1Ach1, staff1Ach2, staff1Ach3, staff1Ach4, staff1Ach5, staff1Ach6;
    private int staff1Mm1, staff1Mm2, staff1Mm3, staff1Mm4, staff1Mm5, staff1Mm6;
    private int staff1App1, staff1App2, staff1App3, staff1App4, staff1App5, staff1App6;
    private int staff1Ns1, staff1Ns2, staff1Ns3, staff1Ns4, staff1Ns5, staff1Ns6;
    private int staff1Lx1, staff1Lx2, staff1Lx3, staff1Lx4, staff1Lx5, staff1Lx6;

    private String staff2Name;
    private float staff2SalesRatio;
    private float staff2AchAppRatio;
    private int staff2Ach1, staff2Ach2, staff2Ach3, staff2Ach4, staff2Ach5, staff2Ach6;
    private int staff2Mm1, staff2Mm2, staff2Mm3, staff2Mm4, staff2Mm5, staff2Mm6;
    private int staff2App1, staff2App2, staff2App3, staff2App4, staff2App5, staff2App6;
    private int staff2Ns1, staff2Ns2, staff2Ns3, staff2Ns4, staff2Ns5, staff2Ns6;
    private int staff2Lx1, staff2Lx2, staff2Lx3, staff2Lx4, staff2Lx5, staff2Lx6;

    private String staff3Name;
    private float staff3SalesRatio;
    private float staff3AchAppRatio;
    private int staff3Ach1, staff3Ach2, staff3Ach3, staff3Ach4, staff3Ach5, staff3Ach6;
    private int staff3Mm1, staff3Mm2, staff3Mm3, staff3Mm4, staff3Mm5, staff3Mm6;
    private int staff3App1, staff3App2, staff3App3, staff3App4, staff3App5, staff3App6;
    private int staff3Ns1, staff3Ns2, staff3Ns3, staff3Ns4, staff3Ns5, staff3Ns6;
    private int staff3Lx1, staff3Lx2, staff3Lx3, staff3Lx4, staff3Lx5, staff3Lx6;

    private String staff4Name;
    private float staff4SalesRatio;
    private float staff4AchAppRatio;
    private int staff4Ach1, staff4Ach2, staff4Ach3, staff4Ach4, staff4Ach5, staff4Ach6;
    private int staff4Mm1, staff4Mm2, staff4Mm3, staff4Mm4, staff4Mm5, staff4Mm6;
    private int staff4App1, staff4App2, staff4App3, staff4App4, staff4App5, staff4App6;
    private int staff4Ns1, staff4Ns2, staff4Ns3, staff4Ns4, staff4Ns5, staff4Ns6;
    private int staff4Lx1, staff4Lx2, staff4Lx3, staff4Lx4, staff4Lx5, staff4Lx6;

    private String staff5Name;
    private float staff5SalesRatio;
    private float staff5AchAppRatio;
    private int staff5Ach1, staff5Ach2, staff5Ach3, staff5Ach4, staff5Ach5, staff5Ach6;
    private int staff5Mm1, staff5Mm2, staff5Mm3, staff5Mm4, staff5Mm5, staff5Mm6;
    private int staff5App1, staff5App2, staff5App3, staff5App4, staff5App5, staff5App6;
    private int staff5Ns1, staff5Ns2, staff5Ns3, staff5Ns4, staff5Ns5, staff5Ns6;
    private int staff5Lx1, staff5Lx2, staff5Lx3, staff5Lx4, staff5Lx5, staff5Lx6;

    private String staff6Name;
    private float staff6SalesRatio;
    private float staff6AchAppRatio;
    private int staff6Ach1, staff6Ach2, staff6Ach3, staff6Ach4, staff6Ach5, staff6Ach6;
    private int staff6Mm1, staff6Mm2, staff6Mm3, staff6Mm4, staff6Mm5, staff6Mm6;
    private int staff6App1, staff6App2, staff6App3, staff6App4, staff6App5, staff6App6;
    private int staff6Ns1, staff6Ns2, staff6Ns3, staff6Ns4, staff6Ns5, staff6Ns6;
    private int staff6Lx1, staff6Lx2, staff6Lx3, staff6Lx4, staff6Lx5, staff6Lx6;

    private String thisPlan;
    private String nextPlan;

    public Ca() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public void setCaYear(int caYear) {
        this.caYear = caYear;
    }

    public void setCaMonth(int caMonth) {
        this.caMonth = caMonth;
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

    public void setGoalsLastShowRatio(float goalsLastShowRatio) {
        this.goalsLastShowRatio = goalsLastShowRatio;
    }

    public void setGoalsLastSalesRatio(float goalsLastSalesRatio) {
        this.goalsLastSalesRatio = goalsLastSalesRatio;
    }

    public void setGoalsExitsRatio(float goalsExitsRatio) {
        this.goalsExitsRatio = goalsExitsRatio;
    }

    public void setGoalsNewSales(int goalsNewSales) {
        this.goalsNewSales = goalsNewSales;
    }

    public void setGoalsAppoints(int goalsAppoints) {
        this.goalsAppoints = goalsAppoints;
    }

    public void setSvcTm1(int svcTm1) {
        this.svcTm1 = svcTm1;
    }

    public void setSvcTm2(int svcTm2) {
        this.svcTm2 = svcTm2;
    }

    public void setSvcTm3(int svcTm3) {
        this.svcTm3 = svcTm3;
    }

    public void setSvcTm4(int svcTm4) {
        this.svcTm4 = svcTm4;
    }

    public void setSvcTm5(int svcTm5) {
        this.svcTm5 = svcTm5;
    }

    public void setSvcTm6(int svcTm6) {
        this.svcTm6 = svcTm6;
    }

    public void setSvcShiftOut1(int svcShiftOut1) {
        this.svcShiftOut1 = svcShiftOut1;
    }

    public void setSvcShiftOut2(int svcShiftOut2) {
        this.svcShiftOut2 = svcShiftOut2;
    }

    public void setSvcShiftOut3(int svcShiftOut3) {
        this.svcShiftOut3 = svcShiftOut3;
    }

    public void setSvcShiftOut4(int svcShiftOut4) {
        this.svcShiftOut4 = svcShiftOut4;
    }

    public void setSvcShiftOut5(int svcShiftOut5) {
        this.svcShiftOut5 = svcShiftOut5;
    }

    public void setSvcShiftOut6(int svcShiftOut6) {
        this.svcShiftOut6 = svcShiftOut6;
    }

    public void setSvcShiftIn1(int svcShiftIn1) {
        this.svcShiftIn1 = svcShiftIn1;
    }

    public void setSvcShiftIn2(int svcShiftIn2) {
        this.svcShiftIn2 = svcShiftIn2;
    }

    public void setSvcShiftIn3(int svcShiftIn3) {
        this.svcShiftIn3 = svcShiftIn3;
    }

    public void setSvcShiftIn4(int svcShiftIn4) {
        this.svcShiftIn4 = svcShiftIn4;
    }

    public void setSvcShiftIn5(int svcShiftIn5) {
        this.svcShiftIn5 = svcShiftIn5;
    }

    public void setSvcShiftIn6(int svcShiftIn6) {
        this.svcShiftIn6 = svcShiftIn6;
    }

    public void setSvcHold1(int svcHold1) {
        this.svcHold1 = svcHold1;
    }

    public void setSvcHold2(int svcHold2) {
        this.svcHold2 = svcHold2;
    }

    public void setSvcHold3(int svcHold3) {
        this.svcHold3 = svcHold3;
    }

    public void setSvcHold4(int svcHold4) {
        this.svcHold4 = svcHold4;
    }

    public void setSvcHold5(int svcHold5) {
        this.svcHold5 = svcHold5;
    }

    public void setSvcHold6(int svcHold6) {
        this.svcHold6 = svcHold6;
    }

    public void setSvcActive1(int svcActive1) {
        this.svcActive1 = svcActive1;
    }

    public void setSvcActive2(int svcActive2) {
        this.svcActive2 = svcActive2;
    }

    public void setSvcActive3(int svcActive3) {
        this.svcActive3 = svcActive3;
    }

    public void setSvcActive4(int svcActive4) {
        this.svcActive4 = svcActive4;
    }

    public void setSvcActive5(int svcActive5) {
        this.svcActive5 = svcActive5;
    }

    public void setSvcActive6(int svcActive6) {
        this.svcActive6 = svcActive6;
    }

    public void setSvcHoldRatio1(float svcHoldRatio1) {
        this.svcHoldRatio1 = svcHoldRatio1;
    }

    public void setSvcHoldRatio2(float svcHoldRatio2) {
        this.svcHoldRatio2 = svcHoldRatio2;
    }

    public void setSvcHoldRatio3(float svcHoldRatio3) {
        this.svcHoldRatio3 = svcHoldRatio3;
    }

    public void setSvcHoldRatio4(float svcHoldRatio4) {
        this.svcHoldRatio4 = svcHoldRatio4;
    }

    public void setSvcHoldRatio5(float svcHoldRatio5) {
        this.svcHoldRatio5 = svcHoldRatio5;
    }

    public void setSvcHoldRatio6(float svcHoldRatio6) {
        this.svcHoldRatio6 = svcHoldRatio6;
    }

    public void setSvcTotalWo1(int svcTotalWo1) {
        this.svcTotalWo1 = svcTotalWo1;
    }

    public void setSvcTotalWo2(int svcTotalWo2) {
        this.svcTotalWo2 = svcTotalWo2;
    }

    public void setSvcTotalWo3(int svcTotalWo3) {
        this.svcTotalWo3 = svcTotalWo3;
    }

    public void setSvcTotalWo4(int svcTotalWo4) {
        this.svcTotalWo4 = svcTotalWo4;
    }

    public void setSvcTotalWo5(int svcTotalWo5) {
        this.svcTotalWo5 = svcTotalWo5;
    }

    public void setSvcTotalWo6(int svcTotalWo6) {
        this.svcTotalWo6 = svcTotalWo6;
    }

    public void setSvcAvgWo1(float svcAvgWo1) {
        this.svcAvgWo1 = svcAvgWo1;
    }

    public void setSvcAvgWo2(float svcAvgWo2) {
        this.svcAvgWo2 = svcAvgWo2;
    }

    public void setSvcAvgWo3(float svcAvgWo3) {
        this.svcAvgWo3 = svcAvgWo3;
    }

    public void setSvcAvgWo4(float svcAvgWo4) {
        this.svcAvgWo4 = svcAvgWo4;
    }

    public void setSvcAvgWo5(float svcAvgWo5) {
        this.svcAvgWo5 = svcAvgWo5;
    }

    public void setSvcAvgWo6(float svcAvgWo6) {
        this.svcAvgWo6 = svcAvgWo6;
    }

    public void setSvcMaxWo1(int svcMaxWo1) {
        this.svcMaxWo1 = svcMaxWo1;
    }

    public void setSvcMaxWo2(int svcMaxWo2) {
        this.svcMaxWo2 = svcMaxWo2;
    }

    public void setSvcMaxWo3(int svcMaxWo3) {
        this.svcMaxWo3 = svcMaxWo3;
    }

    public void setSvcMaxWo4(int svcMaxWo4) {
        this.svcMaxWo4 = svcMaxWo4;
    }

    public void setSvcMaxWo5(int svcMaxWo5) {
        this.svcMaxWo5 = svcMaxWo5;
    }

    public void setSvcMaxWo6(float svcMaxWo6) {
        this.svcMaxWo6 = svcMaxWo6;
    }

    public void setSvcExits1(int svcExits1) {
        this.svcExits1 = svcExits1;
    }

    public void setSvcExits2(int svcExits2) {
        this.svcExits2 = svcExits2;
    }

    public void setSvcExits3(int svcExits3) {
        this.svcExits3 = svcExits3;
    }

    public void setSvcExits4(int svcExits4) {
        this.svcExits4 = svcExits4;
    }

    public void setSvcExits5(int svcExits5) {
        this.svcExits5 = svcExits5;
    }

    public void setSvcExits6(int svcExits6) {
        this.svcExits6 = svcExits6;
    }

    public void setSvcExitsRatio1(float svcExitsRatio1) {
        this.svcExitsRatio1 = svcExitsRatio1;
    }

    public void setSvcExitsRatio2(float svcExitsRatio2) {
        this.svcExitsRatio2 = svcExitsRatio2;
    }

    public void setSvcExitsRatio3(float svcExitsRatio3) {
        this.svcExitsRatio3 = svcExitsRatio3;
    }

    public void setSvcExitsRatio4(float svcExitsRatio4) {
        this.svcExitsRatio4 = svcExitsRatio4;
    }

    public void setSvcExitsRatio5(float svcExitsRatio5) {
        this.svcExitsRatio5 = svcExitsRatio5;
    }

    public void setSvcExitsRatio6(float svcExitsRatio6) {
        this.svcExitsRatio6 = svcExitsRatio6;
    }

    public void setSvcMeasure1(int svcMeasure1) {
        this.svcMeasure1 = svcMeasure1;
    }

    public void setSvcMeasure2(int svcMeasure2) {
        this.svcMeasure2 = svcMeasure2;
    }

    public void setSvcMeasure3(int svcMeasure3) {
        this.svcMeasure3 = svcMeasure3;
    }

    public void setSvcMeasure4(int svcMeasure4) {
        this.svcMeasure4 = svcMeasure4;
    }

    public void setSvcMeasure5(int svcMeasure5) {
        this.svcMeasure5 = svcMeasure5;
    }

    public void setSvcMeasure6(int svcMeasure6) {
        this.svcMeasure6 = svcMeasure6;
    }

    public void setSvcMeasureRatio1(float svcMeasureRatio1) {
        this.svcMeasureRatio1 = svcMeasureRatio1;
    }

    public void setSvcMeasureRatio2(float svcMeasureRatio2) {
        this.svcMeasureRatio2 = svcMeasureRatio2;
    }

    public void setSvcMeasureRatio3(float svcMeasureRatio3) {
        this.svcMeasureRatio3 = svcMeasureRatio3;
    }

    public void setSvcMeasureRatio4(float svcMeasureRatio4) {
        this.svcMeasureRatio4 = svcMeasureRatio4;
    }

    public void setSvcMeasureRatio5(float svcMeasureRatio5) {
        this.svcMeasureRatio5 = svcMeasureRatio5;
    }

    public void setSvcMeasureRatio6(float svcMeasureRatio6) {
        this.svcMeasureRatio6 = svcMeasureRatio6;
    }

    public void setSvc12_5(int svc12_5) {
        this.svc12_5 = svc12_5;
    }

    public void setSvc12_6(float svc12_6) {
        this.svc12_6 = svc12_6;
    }

    public void setSvc8to11_5(int svc8to11_5) {
        this.svc8to11_5 = svc8to11_5;
    }

    public void setSvc8to11_6(float svc8to11_6) {
        this.svc8to11_6 = svc8to11_6;
    }

    public void setSvc4to7_5(int svc4to7_5) {
        this.svc4to7_5 = svc4to7_5;
    }

    public void setSvc4to7_6(float svc4to7_6) {
        this.svc4to7_6 = svc4to7_6;
    }

    public void setSvc1to3_5(int svc1to3_5) {
        this.svc1to3_5 = svc1to3_5;
    }

    public void setSvc1to3_6(float svc1to3_6) {
        this.svc1to3_6 = svc1to3_6;
    }

    public void setSvc0_5(int svc0_5) {
        this.svc0_5 = svc0_5;
    }

    public void setSvc0_6(float svc0_6) {
        this.svc0_6 = svc0_6;
    }

    public void setSvc3More1(String svc3More1) {
        this.svc3More1 = svc3More1;
    }

    public void setSvc3More2(String svc3More2) {
        this.svc3More2 = svc3More2;
    }

    public void setSvc3More3(String svc3More3) {
        this.svc3More3 = svc3More3;
    }

    public void setSvc3More4(String svc3More4) {
        this.svc3More4 = svc3More4;
    }

    public void setSvc3More5(String svc3More5) {
        this.svc3More5 = svc3More5;
    }

    public void setSvcInactive1(String svcInactive1) {
        this.svcInactive1 = svcInactive1;
    }

    public void setSvcInactive2(String svcInactive2) {
        this.svcInactive2 = svcInactive2;
    }

    public void setSvcInactive3(String svcInactive3) {
        this.svcInactive3 = svcInactive3;
    }

    public void setSvcInactive4(String svcInactive4) {
        this.svcInactive4 = svcInactive4;
    }

    public void setSvcInactive5(String svcInactive5) {
        this.svcInactive5 = svcInactive5;
    }

    public void setSvcFwoReview1(String svcFwoReview1) {
        this.svcFwoReview1 = svcFwoReview1;
    }

    public void setSvcFwoReview2(String svcFwoReview2) {
        this.svcFwoReview2 = svcFwoReview2;
    }

    public void setSvcFwoReview3(String svcFwoReview3) {
        this.svcFwoReview3 = svcFwoReview3;
    }

    public void setSvcFwoReview4(String svcFwoReview4) {
        this.svcFwoReview4 = svcFwoReview4;
    }

    public void setSvcFwoReview5(String svcFwoReview5) {
        this.svcFwoReview5 = svcFwoReview5;
    }

    public void setSvcInterview1(String svcInterview1) {
        this.svcInterview1 = svcInterview1;
    }

    public void setSvcInterview2(String svcInterview2) {
        this.svcInterview2 = svcInterview2;
    }

    public void setSvcInterview3(String svcInterview3) {
        this.svcInterview3 = svcInterview3;
    }

    public void setSvcInterview4(String svcInterview4) {
        this.svcInterview4 = svcInterview4;
    }

    public void setSvcInterview5(String svcInterview5) {
        this.svcInterview5 = svcInterview5;
    }

    public void setSvcThanks1(String svcThanks1) {
        this.svcThanks1 = svcThanks1;
    }

    public void setSvcThanks2(String svcThanks2) {
        this.svcThanks2 = svcThanks2;
    }

    public void setSvcThanks3(String svcThanks3) {
        this.svcThanks3 = svcThanks3;
    }

    public void setSvcThanks4(String svcThanks4) {
        this.svcThanks4 = svcThanks4;
    }

    public void setSvcThanks5(String svcThanks5) {
        this.svcThanks5 = svcThanks5;
    }

    public void setSvc3C1(String svc3C1) {
        this.svc3C1 = svc3C1;
    }

    public void setSvc3C2(String svc3C2) {
        this.svc3C2 = svc3C2;
    }

    public void setSvc3C3(String svc3C3) {
        this.svc3C3 = svc3C3;
    }

    public void setSvc3C4(String svc3C4) {
        this.svc3C4 = svc3C4;
    }

    public void setSvc3C5(String svc3C5) {
        this.svc3C5 = svc3C5;
    }

    public void setSvcReward1(String svcReward1) {
        this.svcReward1 = svcReward1;
    }

    public void setSvcReward2(String svcReward2) {
        this.svcReward2 = svcReward2;
    }

    public void setSvcReward3(String svcReward3) {
        this.svcReward3 = svcReward3;
    }

    public void setSvcReward4(String svcReward4) {
        this.svcReward4 = svcReward4;
    }

    public void setSvcReward5(String svcReward5) {
        this.svcReward5 = svcReward5;
    }

    public void setSvcLoyal1(String svcLoyal1) {
        this.svcLoyal1 = svcLoyal1;
    }

    public void setSvcLoyal2(String svcLoyal2) {
        this.svcLoyal2 = svcLoyal2;
    }

    public void setSvcLoyal3(String svcLoyal3) {
        this.svcLoyal3 = svcLoyal3;
    }

    public void setSvcLoyal4(String svcLoyal4) {
        this.svcLoyal4 = svcLoyal4;
    }

    public void setSvcLoyal5(String svcLoyal5) {
        this.svcLoyal5 = svcLoyal5;
    }

    public void setCmPostFlyer1(int cmPostFlyer1) {
        this.cmPostFlyer1 = cmPostFlyer1;
    }

    public void setCmPostFlyer2(int cmPostFlyer2) {
        this.cmPostFlyer2 = cmPostFlyer2;
    }

    public void setCmPostFlyer3(int cmPostFlyer3) {
        this.cmPostFlyer3 = cmPostFlyer3;
    }

    public void setCmPostFlyer4(int cmPostFlyer4) {
        this.cmPostFlyer4 = cmPostFlyer4;
    }

    public void setCmPostFlyer5(int cmPostFlyer5) {
        this.cmPostFlyer5 = cmPostFlyer5;
    }

    public void setCmPostFlyer6(int cmPostFlyer6) {
        this.cmPostFlyer6 = cmPostFlyer6;
    }

    public void setCmHandFlyer1(int cmHandFlyer1) {
        this.cmHandFlyer1 = cmHandFlyer1;
    }

    public void setCmHandFlyer2(int cmHandFlyer2) {
        this.cmHandFlyer2 = cmHandFlyer2;
    }

    public void setCmHandFlyer3(int cmHandFlyer3) {
        this.cmHandFlyer3 = cmHandFlyer3;
    }

    public void setCmHandFlyer4(int cmHandFlyer4) {
        this.cmHandFlyer4 = cmHandFlyer4;
    }

    public void setCmHandFlyer5(int cmHandFlyer5) {
        this.cmHandFlyer5 = cmHandFlyer5;
    }

    public void setCmHandFlyer6(int cmHandFlyer6) {
        this.cmHandFlyer6 = cmHandFlyer6;
    }

    public void setCmHandFlyerHours1(float cmHandFlyerHours1) {
        this.cmHandFlyerHours1 = cmHandFlyerHours1;
    }

    public void setCmHandFlyerHours2(float cmHandFlyerHours2) {
        this.cmHandFlyerHours2 = cmHandFlyerHours2;
    }

    public void setCmHandFlyerHours3(float cmHandFlyerHours3) {
        this.cmHandFlyerHours3 = cmHandFlyerHours3;
    }

    public void setCmHandFlyerHours4(float cmHandFlyerHours4) {
        this.cmHandFlyerHours4 = cmHandFlyerHours4;
    }

    public void setCmHandFlyerHours5(float cmHandFlyerHours5) {
        this.cmHandFlyerHours5 = cmHandFlyerHours5;
    }

    public void setCmHandFlyerHours6(float cmHandFlyerHours6) {
        this.cmHandFlyerHours6 = cmHandFlyerHours6;
    }

    public void setCmOutGpHours1(float cmOutGpHours1) {
        this.cmOutGpHours1 = cmOutGpHours1;
    }

    public void setCmOutGpHours2(float cmOutGpHours2) {
        this.cmOutGpHours2 = cmOutGpHours2;
    }

    public void setCmOutGpHours3(float cmOutGpHours3) {
        this.cmOutGpHours3 = cmOutGpHours3;
    }

    public void setCmOutGpHours4(float cmOutGpHours4) {
        this.cmOutGpHours4 = cmOutGpHours4;
    }

    public void setCmOutGpHours5(float cmOutGpHours5) {
        this.cmOutGpHours5 = cmOutGpHours5;
    }

    public void setCmOutGpHours6(float cmOutGpHours6) {
        this.cmOutGpHours6 = cmOutGpHours6;
    }

    public void setCmOutGp1(int cmOutGp1) {
        this.cmOutGp1 = cmOutGp1;
    }

    public void setCmOutGp2(int cmOutGp2) {
        this.cmOutGp2 = cmOutGp2;
    }

    public void setCmOutGp3(int cmOutGp3) {
        this.cmOutGp3 = cmOutGp3;
    }

    public void setCmOutGp4(int cmOutGp4) {
        this.cmOutGp4 = cmOutGp4;
    }

    public void setCmOutGp5(int cmOutGp5) {
        this.cmOutGp5 = cmOutGp5;
    }

    public void setCmOutGp6(int cmOutGp6) {
        this.cmOutGp6 = cmOutGp6;
    }

    public void setCmCpBox1(int cmCpBox1) {
        this.cmCpBox1 = cmCpBox1;
    }

    public void setCmCpBox2(int cmCpBox2) {
        this.cmCpBox2 = cmCpBox2;
    }

    public void setCmCpBox3(int cmCpBox3) {
        this.cmCpBox3 = cmCpBox3;
    }

    public void setCmCpBox4(int cmCpBox4) {
        this.cmCpBox4 = cmCpBox4;
    }

    public void setCmCpBox5(int cmCpBox5) {
        this.cmCpBox5 = cmCpBox5;
    }

    public void setCmCpBox6(int cmCpBox6) {
        this.cmCpBox6 = cmCpBox6;
    }

    public void setCmOutGot1(int cmOutGot1) {
        this.cmOutGot1 = cmOutGot1;
    }

    public void setCmOutGot2(int cmOutGot2) {
        this.cmOutGot2 = cmOutGot2;
    }

    public void setCmOutGot3(int cmOutGot3) {
        this.cmOutGot3 = cmOutGot3;
    }

    public void setCmOutGot4(int cmOutGot4) {
        this.cmOutGot4 = cmOutGot4;
    }

    public void setCmOutGot5(int cmOutGot5) {
        this.cmOutGot5 = cmOutGot5;
    }

    public void setCmOutGot6(int cmOutGot6) {
        this.cmOutGot6 = cmOutGot6;
    }

    public void setCmInGot1(int cmInGot1) {
        this.cmInGot1 = cmInGot1;
    }

    public void setCmInGot2(int cmInGot2) {
        this.cmInGot2 = cmInGot2;
    }

    public void setCmInGot3(int cmInGot3) {
        this.cmInGot3 = cmInGot3;
    }

    public void setCmInGot4(int cmInGot4) {
        this.cmInGot4 = cmInGot4;
    }

    public void setCmInGot5(int cmInGot5) {
        this.cmInGot5 = cmInGot5;
    }

    public void setCmInGot6(int cmInGot6) {
        this.cmInGot6 = cmInGot6;
    }

    public void setCmBlogGot1(int cmBlogGot1) {
        this.cmBlogGot1 = cmBlogGot1;
    }

    public void setCmBlogGot2(int cmBlogGot2) {
        this.cmBlogGot2 = cmBlogGot2;
    }

    public void setCmBlogGot3(int cmBlogGot3) {
        this.cmBlogGot3 = cmBlogGot3;
    }

    public void setCmBlogGot4(int cmBlogGot4) {
        this.cmBlogGot4 = cmBlogGot4;
    }

    public void setCmBlogGot5(int cmBlogGot5) {
        this.cmBlogGot5 = cmBlogGot5;
    }

    public void setCmBlogGot6(int cmBlogGot6) {
        this.cmBlogGot6 = cmBlogGot6;
    }

    public void setCmBagGot1(int cmBagGot1) {
        this.cmBagGot1 = cmBagGot1;
    }

    public void setCmBagGot2(int cmBagGot2) {
        this.cmBagGot2 = cmBagGot2;
    }

    public void setCmBagGot3(int cmBagGot3) {
        this.cmBagGot3 = cmBagGot3;
    }

    public void setCmBagGot4(int cmBagGot4) {
        this.cmBagGot4 = cmBagGot4;
    }

    public void setCmBagGot5(int cmBagGot5) {
        this.cmBagGot5 = cmBagGot5;
    }

    public void setCmBagGot6(int cmBagGot6) {
        this.cmBagGot6 = cmBagGot6;
    }

    public void setCmTotalGot1(int cmTotalGot1) {
        this.cmTotalGot1 = cmTotalGot1;
    }

    public void setCmTotalGot2(int cmTotalGot2) {
        this.cmTotalGot2 = cmTotalGot2;
    }

    public void setCmTotalGot3(int cmTotalGot3) {
        this.cmTotalGot3 = cmTotalGot3;
    }

    public void setCmTotalGot4(int cmTotalGot4) {
        this.cmTotalGot4 = cmTotalGot4;
    }

    public void setCmTotalGot5(int cmTotalGot5) {
        this.cmTotalGot5 = cmTotalGot5;
    }

    public void setCmTotalGot6(int cmTotalGot6) {
        this.cmTotalGot6 = cmTotalGot6;
    }

    public void setCmCallIn1(int cmCallIn1) {
        this.cmCallIn1 = cmCallIn1;
    }

    public void setCmCallIn2(int cmCallIn2) {
        this.cmCallIn2 = cmCallIn2;
    }

    public void setCmCallIn3(int cmCallIn3) {
        this.cmCallIn3 = cmCallIn3;
    }

    public void setCmCallIn4(int cmCallIn4) {
        this.cmCallIn4 = cmCallIn4;
    }

    public void setCmCallIn5(int cmCallIn5) {
        this.cmCallIn5 = cmCallIn5;
    }

    public void setCmCallIn6(int cmCallIn6) {
        this.cmCallIn6 = cmCallIn6;
    }

    public void setCmOutGotCall1(int cmOutGotCall1) {
        this.cmOutGotCall1 = cmOutGotCall1;
    }

    public void setCmOutGotCall2(int cmOutGotCall2) {
        this.cmOutGotCall2 = cmOutGotCall2;
    }

    public void setCmOutGotCall3(int cmOutGotCall3) {
        this.cmOutGotCall3 = cmOutGotCall3;
    }

    public void setCmOutGotCall4(int cmOutGotCall4) {
        this.cmOutGotCall4 = cmOutGotCall4;
    }

    public void setCmOutGotCall5(int cmOutGotCall5) {
        this.cmOutGotCall5 = cmOutGotCall5;
    }

    public void setCmOutGotCall6(int cmOutGotCall6) {
        this.cmOutGotCall6 = cmOutGotCall6;
    }

    public void setCmInGotCall1(int cmInGotCall1) {
        this.cmInGotCall1 = cmInGotCall1;
    }

    public void setCmInGotCall2(int cmInGotCall2) {
        this.cmInGotCall2 = cmInGotCall2;
    }

    public void setCmInGotCall3(int cmInGotCall3) {
        this.cmInGotCall3 = cmInGotCall3;
    }

    public void setCmInGotCall4(int cmInGotCall4) {
        this.cmInGotCall4 = cmInGotCall4;
    }

    public void setCmInGotCall5(int cmInGotCall5) {
        this.cmInGotCall5 = cmInGotCall5;
    }

    public void setCmInGotCall6(int cmInGotCall6) {
        this.cmInGotCall6 = cmInGotCall6;
    }

    public void setCmBlogGotCall1(int cmBlogGotCall1) {
        this.cmBlogGotCall1 = cmBlogGotCall1;
    }

    public void setCmBlogGotCall2(int cmBlogGotCall2) {
        this.cmBlogGotCall2 = cmBlogGotCall2;
    }

    public void setCmBlogGotCall3(int cmBlogGotCall3) {
        this.cmBlogGotCall3 = cmBlogGotCall3;
    }

    public void setCmBlogGotCall4(int cmBlogGotCall4) {
        this.cmBlogGotCall4 = cmBlogGotCall4;
    }

    public void setCmBlogGotCall5(int cmBlogGotCall5) {
        this.cmBlogGotCall5 = cmBlogGotCall5;
    }

    public void setCmBlogGotCall6(int cmBlogGotCall6) {
        this.cmBlogGotCall6 = cmBlogGotCall6;
    }

    public void setCmBagGotCall1(int cmBagGotCall1) {
        this.cmBagGotCall1 = cmBagGotCall1;
    }

    public void setCmBagGotCall2(int cmBagGotCall2) {
        this.cmBagGotCall2 = cmBagGotCall2;
    }

    public void setCmBagGotCall3(int cmBagGotCall3) {
        this.cmBagGotCall3 = cmBagGotCall3;
    }

    public void setCmBagGotCall4(int cmBagGotCall4) {
        this.cmBagGotCall4 = cmBagGotCall4;
    }

    public void setCmBagGotCall5(int cmBagGotCall5) {
        this.cmBagGotCall5 = cmBagGotCall5;
    }

    public void setCmBagGotCall6(int cmBagGotCall6) {
        this.cmBagGotCall6 = cmBagGotCall6;
    }

    public void setCmOwnRefs1(int cmOwnRefs1) {
        this.cmOwnRefs1 = cmOwnRefs1;
    }

    public void setCmOwnRefs2(int cmOwnRefs2) {
        this.cmOwnRefs2 = cmOwnRefs2;
    }

    public void setCmOwnRefs3(int cmOwnRefs3) {
        this.cmOwnRefs3 = cmOwnRefs3;
    }

    public void setCmOwnRefs4(int cmOwnRefs4) {
        this.cmOwnRefs4 = cmOwnRefs4;
    }

    public void setCmOwnRefs5(int cmOwnRefs5) {
        this.cmOwnRefs5 = cmOwnRefs5;
    }

    public void setCmOwnRefs6(int cmOwnRefs6) {
        this.cmOwnRefs6 = cmOwnRefs6;
    }

    public void setCmOtherRefs1(int cmOtherRefs1) {
        this.cmOtherRefs1 = cmOtherRefs1;
    }

    public void setCmOtherRefs2(int cmOtherRefs2) {
        this.cmOtherRefs2 = cmOtherRefs2;
    }

    public void setCmOtherRefs3(int cmOtherRefs3) {
        this.cmOtherRefs3 = cmOtherRefs3;
    }

    public void setCmOtherRefs4(int cmOtherRefs4) {
        this.cmOtherRefs4 = cmOtherRefs4;
    }

    public void setCmOtherRefs5(int cmOtherRefs5) {
        this.cmOtherRefs5 = cmOtherRefs5;
    }

    public void setCmOtherRefs6(int cmOtherRefs6) {
        this.cmOtherRefs6 = cmOtherRefs6;
    }

    public void setCmNewspaper1(int cmNewspaper1) {
        this.cmNewspaper1 = cmNewspaper1;
    }

    public void setCmNewspaper2(int cmNewspaper2) {
        this.cmNewspaper2 = cmNewspaper2;
    }

    public void setCmNewspaper3(int cmNewspaper3) {
        this.cmNewspaper3 = cmNewspaper3;
    }

    public void setCmNewspaper4(int cmNewspaper4) {
        this.cmNewspaper4 = cmNewspaper4;
    }

    public void setCmNewspaper5(int cmNewspaper5) {
        this.cmNewspaper5 = cmNewspaper5;
    }

    public void setCmNewspaper6(int cmNewspaper6) {
        this.cmNewspaper6 = cmNewspaper6;
    }

    public void setCmTv1(int cmTv1) {
        this.cmTv1 = cmTv1;
    }

    public void setCmTv2(int cmTv2) {
        this.cmTv2 = cmTv2;
    }

    public void setCmTv3(int cmTv3) {
        this.cmTv3 = cmTv3;
    }

    public void setCmTv4(int cmTv4) {
        this.cmTv4 = cmTv4;
    }

    public void setCmTv5(int cmTv5) {
        this.cmTv5 = cmTv5;
    }

    public void setCmTv6(int cmTv6) {
        this.cmTv6 = cmTv6;
    }

    public void setCmInternet1(int cmInternet1) {
        this.cmInternet1 = cmInternet1;
    }

    public void setCmInternet2(int cmInternet2) {
        this.cmInternet2 = cmInternet2;
    }

    public void setCmInternet3(int cmInternet3) {
        this.cmInternet3 = cmInternet3;
    }

    public void setCmInternet4(int cmInternet4) {
        this.cmInternet4 = cmInternet4;
    }

    public void setCmInternet5(int cmInternet5) {
        this.cmInternet5 = cmInternet5;
    }

    public void setCmInternet6(int cmInternet6) {
        this.cmInternet6 = cmInternet6;
    }

    public void setCmSign1(int cmSign1) {
        this.cmSign1 = cmSign1;
    }

    public void setCmSign2(int cmSign2) {
        this.cmSign2 = cmSign2;
    }

    public void setCmSign3(int cmSign3) {
        this.cmSign3 = cmSign3;
    }

    public void setCmSign4(int cmSign4) {
        this.cmSign4 = cmSign4;
    }

    public void setCmSign5(int cmSign5) {
        this.cmSign5 = cmSign5;
    }

    public void setCmSign6(int cmSign6) {
        this.cmSign6 = cmSign6;
    }

    public void setCmMate1(int cmMate1) {
        this.cmMate1 = cmMate1;
    }

    public void setCmMate2(int cmMate2) {
        this.cmMate2 = cmMate2;
    }

    public void setCmMate3(int cmMate3) {
        this.cmMate3 = cmMate3;
    }

    public void setCmMate4(int cmMate4) {
        this.cmMate4 = cmMate4;
    }

    public void setCmMate5(int cmMate5) {
        this.cmMate5 = cmMate5;
    }

    public void setCmMate6(int cmMate6) {
        this.cmMate6 = cmMate6;
    }

    public void setCmOthers1(int cmOthers1) {
        this.cmOthers1 = cmOthers1;
    }

    public void setCmOthers2(int cmOthers2) {
        this.cmOthers2 = cmOthers2;
    }

    public void setCmOthers3(int cmOthers3) {
        this.cmOthers3 = cmOthers3;
    }

    public void setCmOthers4(int cmOthers4) {
        this.cmOthers4 = cmOthers4;
    }

    public void setCmOthers5(int cmOthers5) {
        this.cmOthers5 = cmOthers5;
    }

    public void setCmOthers6(int cmOthers6) {
        this.cmOthers6 = cmOthers6;
    }

    public void setCmMailAgpIn1(int cmMailAgpIn1) {
        this.cmMailAgpIn1 = cmMailAgpIn1;
    }

    public void setCmMailAgpIn2(int cmMailAgpIn2) {
        this.cmMailAgpIn2 = cmMailAgpIn2;
    }

    public void setCmMailAgpIn3(int cmMailAgpIn3) {
        this.cmMailAgpIn3 = cmMailAgpIn3;
    }

    public void setCmMailAgpIn4(int cmMailAgpIn4) {
        this.cmMailAgpIn4 = cmMailAgpIn4;
    }

    public void setCmMailAgpIn5(int cmMailAgpIn5) {
        this.cmMailAgpIn5 = cmMailAgpIn5;
    }

    public void setCmMailAgpIn6(int cmMailAgpIn6) {
        this.cmMailAgpIn6 = cmMailAgpIn6;
    }

    public void setCmPostFlyerAgpIn1(int cmPostFlyerAgpIn1) {
        this.cmPostFlyerAgpIn1 = cmPostFlyerAgpIn1;
    }

    public void setCmPostFlyerAgpIn2(int cmPostFlyerAgpIn2) {
        this.cmPostFlyerAgpIn2 = cmPostFlyerAgpIn2;
    }

    public void setCmPostFlyerAgpIn3(int cmPostFlyerAgpIn3) {
        this.cmPostFlyerAgpIn3 = cmPostFlyerAgpIn3;
    }

    public void setCmPostFlyerAgpIn4(int cmPostFlyerAgpIn4) {
        this.cmPostFlyerAgpIn4 = cmPostFlyerAgpIn4;
    }

    public void setCmPostFlyerAgpIn5(int cmPostFlyerAgpIn5) {
        this.cmPostFlyerAgpIn5 = cmPostFlyerAgpIn5;
    }

    public void setCmPostFlyerAgpIn6(int cmPostFlyerAgpIn6) {
        this.cmPostFlyerAgpIn6 = cmPostFlyerAgpIn6;
    }

    public void setCmHandFlyerAgpIn1(int cmHandFlyerAgpIn1) {
        this.cmHandFlyerAgpIn1 = cmHandFlyerAgpIn1;
    }

    public void setCmHandFlyerAgpIn2(int cmHandFlyerAgpIn2) {
        this.cmHandFlyerAgpIn2 = cmHandFlyerAgpIn2;
    }

    public void setCmHandFlyerAgpIn3(int cmHandFlyerAgpIn3) {
        this.cmHandFlyerAgpIn3 = cmHandFlyerAgpIn3;
    }

    public void setCmHandFlyerAgpIn4(int cmHandFlyerAgpIn4) {
        this.cmHandFlyerAgpIn4 = cmHandFlyerAgpIn4;
    }

    public void setCmHandFlyerAgpIn5(int cmHandFlyerAgpIn5) {
        this.cmHandFlyerAgpIn5 = cmHandFlyerAgpIn5;
    }

    public void setCmHandFlyerAgpIn6(int cmHandFlyerAgpIn6) {
        this.cmHandFlyerAgpIn6 = cmHandFlyerAgpIn6;
    }

    public void setCmCpAgpIn1(int cmCpAgpIn1) {
        this.cmCpAgpIn1 = cmCpAgpIn1;
    }

    public void setCmCpAgpIn2(int cmCpAgpIn2) {
        this.cmCpAgpIn2 = cmCpAgpIn2;
    }

    public void setCmCpAgpIn3(int cmCpAgpIn3) {
        this.cmCpAgpIn3 = cmCpAgpIn3;
    }

    public void setCmCpAgpIn4(int cmCpAgpIn4) {
        this.cmCpAgpIn4 = cmCpAgpIn4;
    }

    public void setCmCpAgpIn5(int cmCpAgpIn5) {
        this.cmCpAgpIn5 = cmCpAgpIn5;
    }

    public void setCmCpAgpIn6(int cmCpAgpIn6) {
        this.cmCpAgpIn6 = cmCpAgpIn6;
    }

    public void setCmOutAgpOut1(int cmOutAgpOut1) {
        this.cmOutAgpOut1 = cmOutAgpOut1;
    }

    public void setCmOutAgpOut2(int cmOutAgpOut2) {
        this.cmOutAgpOut2 = cmOutAgpOut2;
    }

    public void setCmOutAgpOut3(int cmOutAgpOut3) {
        this.cmOutAgpOut3 = cmOutAgpOut3;
    }

    public void setCmOutAgpOut4(int cmOutAgpOut4) {
        this.cmOutAgpOut4 = cmOutAgpOut4;
    }

    public void setCmOutAgpOut5(int cmOutAgpOut5) {
        this.cmOutAgpOut5 = cmOutAgpOut5;
    }

    public void setCmOutAgpOut6(int cmOutAgpOut6) {
        this.cmOutAgpOut6 = cmOutAgpOut6;
    }

    public void setCmInAgpOut1(int cmInAgpOut1) {
        this.cmInAgpOut1 = cmInAgpOut1;
    }

    public void setCmInAgpOut2(int cmInAgpOut2) {
        this.cmInAgpOut2 = cmInAgpOut2;
    }

    public void setCmInAgpOut3(int cmInAgpOut3) {
        this.cmInAgpOut3 = cmInAgpOut3;
    }

    public void setCmInAgpOut4(int cmInAgpOut4) {
        this.cmInAgpOut4 = cmInAgpOut4;
    }

    public void setCmInAgpOut5(int cmInAgpOut5) {
        this.cmInAgpOut5 = cmInAgpOut5;
    }

    public void setCmInAgpOut6(int cmInAgpOut6) {
        this.cmInAgpOut6 = cmInAgpOut6;
    }

    public void setCmBlogAgpOut1(int cmBlogAgpOut1) {
        this.cmBlogAgpOut1 = cmBlogAgpOut1;
    }

    public void setCmBlogAgpOut2(int cmBlogAgpOut2) {
        this.cmBlogAgpOut2 = cmBlogAgpOut2;
    }

    public void setCmBlogAgpOut3(int cmBlogAgpOut3) {
        this.cmBlogAgpOut3 = cmBlogAgpOut3;
    }

    public void setCmBlogAgpOut4(int cmBlogAgpOut4) {
        this.cmBlogAgpOut4 = cmBlogAgpOut4;
    }

    public void setCmBlogAgpOut5(int cmBlogAgpOut5) {
        this.cmBlogAgpOut5 = cmBlogAgpOut5;
    }

    public void setCmBlogAgpOut6(int cmBlogAgpOut6) {
        this.cmBlogAgpOut6 = cmBlogAgpOut6;
    }

    public void setCmBagAgpOut1(int cmBagAgpOut1) {
        this.cmBagAgpOut1 = cmBagAgpOut1;
    }

    public void setCmBagAgpOut2(int cmBagAgpOut2) {
        this.cmBagAgpOut2 = cmBagAgpOut2;
    }

    public void setCmBagAgpOut3(int cmBagAgpOut3) {
        this.cmBagAgpOut3 = cmBagAgpOut3;
    }

    public void setCmBagAgpOut4(int cmBagAgpOut4) {
        this.cmBagAgpOut4 = cmBagAgpOut4;
    }

    public void setCmBagAgpOut5(int cmBagAgpOut5) {
        this.cmBagAgpOut5 = cmBagAgpOut5;
    }

    public void setCmBagAgpOut6(int cmBagAgpOut6) {
        this.cmBagAgpOut6 = cmBagAgpOut6;
    }

    public void setCmApoTotal1(int cmApoTotal1) {
        this.cmApoTotal1 = cmApoTotal1;
    }

    public void setCmApoTotal2(int cmApoTotal2) {
        this.cmApoTotal2 = cmApoTotal2;
    }

    public void setCmApoTotal3(int cmApoTotal3) {
        this.cmApoTotal3 = cmApoTotal3;
    }

    public void setCmApoTotal4(int cmApoTotal4) {
        this.cmApoTotal4 = cmApoTotal4;
    }

    public void setCmApoTotal5(int cmApoTotal5) {
        this.cmApoTotal5 = cmApoTotal5;
    }

    public void setCmApoTotal6(int cmApoTotal6) {
        this.cmApoTotal6 = cmApoTotal6;
    }

    public void setCmInApptRatio1(float cmInApptRatio1) {
        this.cmInApptRatio1 = cmInApptRatio1;
    }

    public void setCmInApptRatio2(float cmInApptRatio2) {
        this.cmInApptRatio2 = cmInApptRatio2;
    }

    public void setCmInApptRatio3(float cmInApptRatio3) {
        this.cmInApptRatio3 = cmInApptRatio3;
    }

    public void setCmInApptRatio4(float cmInApptRatio4) {
        this.cmInApptRatio4 = cmInApptRatio4;
    }

    public void setCmInApptRatio5(float cmInApptRatio5) {
        this.cmInApptRatio5 = cmInApptRatio5;
    }

    public void setCmInApptRatio6(float cmInApptRatio6) {
        this.cmInApptRatio6 = cmInApptRatio6;
    }

    public void setCmOutApptRatio1(float cmOutApptRatio1) {
        this.cmOutApptRatio1 = cmOutApptRatio1;
    }

    public void setCmOutApptRatio2(float cmOutApptRatio2) {
        this.cmOutApptRatio2 = cmOutApptRatio2;
    }

    public void setCmOutApptRatio3(float cmOutApptRatio3) {
        this.cmOutApptRatio3 = cmOutApptRatio3;
    }

    public void setCmOutApptRatio4(float cmOutApptRatio4) {
        this.cmOutApptRatio4 = cmOutApptRatio4;
    }

    public void setCmOutApptRatio5(float cmOutApptRatio5) {
        this.cmOutApptRatio5 = cmOutApptRatio5;
    }

    public void setCmOutApptRatio6(float cmOutApptRatio6) {
        this.cmOutApptRatio6 = cmOutApptRatio6;
    }

    public void setCmPostPerApo6(int cmPostPerApo6) {
        this.cmPostPerApo6 = cmPostPerApo6;
    }

    public void setCmHandPerApo6(int cmHandPerApo6) {
        this.cmHandPerApo6 = cmHandPerApo6;
    }

    public void setCmHandHoursPerApo6(float cmHandHoursPerApo6) {
        this.cmHandHoursPerApo6 = cmHandHoursPerApo6;
    }

    public void setCmOutGpPerApo6(float cmOutGpPerApo6) {
        this.cmOutGpPerApo6 = cmOutGpPerApo6;
    }

    public void setCmOutGpHoursPerApo6(float cmOutGpHoursPerApo6) {
        this.cmOutGpHoursPerApo6 = cmOutGpHoursPerApo6;
    }

    public void setCmBrAgpRatio1(float cmBrAgpRatio1) {
        this.cmBrAgpRatio1 = cmBrAgpRatio1;
    }

    public void setCmBrAgpRatio2(float cmBrAgpRatio2) {
        this.cmBrAgpRatio2 = cmBrAgpRatio2;
    }

    public void setCmBrAgpRatio3(float cmBrAgpRatio3) {
        this.cmBrAgpRatio3 = cmBrAgpRatio3;
    }

    public void setCmBrAgpRatio4(float cmBrAgpRatio4) {
        this.cmBrAgpRatio4 = cmBrAgpRatio4;
    }

    public void setCmBrAgpRatio5(float cmBrAgpRatio5) {
        this.cmBrAgpRatio5 = cmBrAgpRatio5;
    }

    public void setCmBrAgpRatio6(float cmBrAgpRatio6) {
        this.cmBrAgpRatio6 = cmBrAgpRatio6;
    }

    public void setCmFaSum1(int cmFaSum1) {
        this.cmFaSum1 = cmFaSum1;
    }

    public void setCmFaSum2(int cmFaSum2) {
        this.cmFaSum2 = cmFaSum2;
    }

    public void setCmFaSum3(int cmFaSum3) {
        this.cmFaSum3 = cmFaSum3;
    }

    public void setCmFaSum4(int cmFaSum4) {
        this.cmFaSum4 = cmFaSum4;
    }

    public void setCmFaSum5(int cmFaSum5) {
        this.cmFaSum5 = cmFaSum5;
    }

    public void setCmFaSum6(int cmFaSum6) {
        this.cmFaSum6 = cmFaSum6;
    }

    public void setCmShowRatio1(float cmShowRatio1) {
        this.cmShowRatio1 = cmShowRatio1;
    }

    public void setCmShowRatio2(float cmShowRatio2) {
        this.cmShowRatio2 = cmShowRatio2;
    }

    public void setCmShowRatio3(float cmShowRatio3) {
        this.cmShowRatio3 = cmShowRatio3;
    }

    public void setCmShowRatio4(float cmShowRatio4) {
        this.cmShowRatio4 = cmShowRatio4;
    }

    public void setCmShowRatio5(float cmShowRatio5) {
        this.cmShowRatio5 = cmShowRatio5;
    }

    public void setCmShowRatio6(float cmShowRatio6) {
        this.cmShowRatio6 = cmShowRatio6;
    }

    public void setCmTraining1(String cmTraining1) {
        this.cmTraining1 = cmTraining1;
    }

    public void setCmTraining2(String cmTraining2) {
        this.cmTraining2 = cmTraining2;
    }

    public void setCmTraining3(String cmTraining3) {
        this.cmTraining3 = cmTraining3;
    }

    public void setCmTraining4(String cmTraining4) {
        this.cmTraining4 = cmTraining4;
    }

    public void setCmTraining5(String cmTraining5) {
        this.cmTraining5 = cmTraining5;
    }

    public void setCmGot3_1(String cmGot3_1) {
        this.cmGot3_1 = cmGot3_1;
    }

    public void setCmGot3_2(String cmGot3_2) {
        this.cmGot3_2 = cmGot3_2;
    }

    public void setCmGot3_3(String cmGot3_3) {
        this.cmGot3_3 = cmGot3_3;
    }

    public void setCmGot3_4(String cmGot3_4) {
        this.cmGot3_4 = cmGot3_4;
    }

    public void setCmGot3_5(String cmGot3_5) {
        this.cmGot3_5 = cmGot3_5;
    }

    public void setCmInvitation1(String cmInvitation1) {
        this.cmInvitation1 = cmInvitation1;
    }

    public void setCmInvitation2(String cmInvitation2) {
        this.cmInvitation2 = cmInvitation2;
    }

    public void setCmInvitation3(String cmInvitation3) {
        this.cmInvitation3 = cmInvitation3;
    }

    public void setCmInvitation4(String cmInvitation4) {
        this.cmInvitation4 = cmInvitation4;
    }

    public void setCmInvitation5(String cmInvitation5) {
        this.cmInvitation5 = cmInvitation5;
    }

    public void setSalesAch1(int salesAch1) {
        this.salesAch1 = salesAch1;
    }

    public void setSalesAch2(int salesAch2) {
        this.salesAch2 = salesAch2;
    }

    public void setSalesAch3(int salesAch3) {
        this.salesAch3 = salesAch3;
    }

    public void setSalesAch4(int salesAch4) {
        this.salesAch4 = salesAch4;
    }

    public void setSalesAch5(int salesAch5) {
        this.salesAch5 = salesAch5;
    }

    public void setSalesAch6(int salesAch6) {
        this.salesAch6 = salesAch6;
    }

    public void setSalesMonthly1(int salesMonthly1) {
        this.salesMonthly1 = salesMonthly1;
    }

    public void setSalesMonthly2(int salesMonthly2) {
        this.salesMonthly2 = salesMonthly2;
    }

    public void setSalesMonthly3(int salesMonthly3) {
        this.salesMonthly3 = salesMonthly3;
    }

    public void setSalesMonthly4(int salesMonthly4) {
        this.salesMonthly4 = salesMonthly4;
    }

    public void setSalesMonthly5(int salesMonthly5) {
        this.salesMonthly5 = salesMonthly5;
    }

    public void setSalesMonthly6(int salesMonthly6) {
        this.salesMonthly6 = salesMonthly6;
    }

    public void setSalesAllPrepay1(int salesAllPrepay1) {
        this.salesAllPrepay1 = salesAllPrepay1;
    }

    public void setSalesAllPrepay2(int salesAllPrepay2) {
        this.salesAllPrepay2 = salesAllPrepay2;
    }

    public void setSalesAllPrepay3(int salesAllPrepay3) {
        this.salesAllPrepay3 = salesAllPrepay3;
    }

    public void setSalesAllPrepay4(int salesAllPrepay4) {
        this.salesAllPrepay4 = salesAllPrepay4;
    }

    public void setSalesAllPrepay5(int salesAllPrepay5) {
        this.salesAllPrepay5 = salesAllPrepay5;
    }

    public void setSalesAllPrepay6(int salesAllPrepay6) {
        this.salesAllPrepay6 = salesAllPrepay6;
    }

    public void setSalesTotal1(int salesTotal1) {
        this.salesTotal1 = salesTotal1;
    }

    public void setSalesTotal2(int salesTotal2) {
        this.salesTotal2 = salesTotal2;
    }

    public void setSalesTotal3(int salesTotal3) {
        this.salesTotal3 = salesTotal3;
    }

    public void setSalesTotal4(int salesTotal4) {
        this.salesTotal4 = salesTotal4;
    }

    public void setSalesTotal5(int salesTotal5) {
        this.salesTotal5 = salesTotal5;
    }

    public void setSalesTotal6(int salesTotal6) {
        this.salesTotal6 = salesTotal6;
    }

    public void setSalesRatio1(float salesRatio1) {
        this.salesRatio1 = salesRatio1;
    }

    public void setSalesRatio2(float salesRatio2) {
        this.salesRatio2 = salesRatio2;
    }

    public void setSalesRatio3(float salesRatio3) {
        this.salesRatio3 = salesRatio3;
    }

    public void setSalesRatio4(float salesRatio4) {
        this.salesRatio4 = salesRatio4;
    }

    public void setSalesRatio5(float salesRatio5) {
        this.salesRatio5 = salesRatio5;
    }

    public void setSalesRatio6(float salesRatio6) {
        this.salesRatio6 = salesRatio6;
    }

    public void setSalesAchAppRatio1(float salesAchAppRatio1) {
        this.salesAchAppRatio1 = salesAchAppRatio1;
    }

    public void setSalesAchAppRatio2(float salesAchAppRatio2) {
        this.salesAchAppRatio2 = salesAchAppRatio2;
    }

    public void setSalesAchAppRatio3(float salesAchAppRatio3) {
        this.salesAchAppRatio3 = salesAchAppRatio3;
    }

    public void setSalesAchAppRatio4(float salesAchAppRatio4) {
        this.salesAchAppRatio4 = salesAchAppRatio4;
    }

    public void setSalesAchAppRatio5(float salesAchAppRatio5) {
        this.salesAchAppRatio5 = salesAchAppRatio5;
    }

    public void setSalesAchAppRatio6(float salesAchAppRatio6) {
        this.salesAchAppRatio6 = salesAchAppRatio6;
    }

    public void setSalesFaReview1(String salesFaReview1) {
        this.salesFaReview1 = salesFaReview1;
    }

    public void setSalesFaReview2(String salesFaReview2) {
        this.salesFaReview2 = salesFaReview2;
    }

    public void setSalesFaReview3(String salesFaReview3) {
        this.salesFaReview3 = salesFaReview3;
    }

    public void setSalesFaReview4(String salesFaReview4) {
        this.salesFaReview4 = salesFaReview4;
    }

    public void setSalesFaReview5(String salesFaReview5) {
        this.salesFaReview5 = salesFaReview5;
    }

    public void setSalesPriceReview1(String salesPriceReview1) {
        this.salesPriceReview1 = salesPriceReview1;
    }

    public void setSalesPriceReview2(String salesPriceReview2) {
        this.salesPriceReview2 = salesPriceReview2;
    }

    public void setSalesPriceReview3(String salesPriceReview3) {
        this.salesPriceReview3 = salesPriceReview3;
    }

    public void setSalesPriceReview4(String salesPriceReview4) {
        this.salesPriceReview4 = salesPriceReview4;
    }

    public void setSalesPriceReview5(String salesPriceReview5) {
        this.salesPriceReview5 = salesPriceReview5;
    }

    public void setSalesAck1(String salesAck1) {
        this.salesAck1 = salesAck1;
    }

    public void setSalesAck2(String salesAck2) {
        this.salesAck2 = salesAck2;
    }

    public void setSalesAck3(String salesAck3) {
        this.salesAck3 = salesAck3;
    }

    public void setSalesAck4(String salesAck4) {
        this.salesAck4 = salesAck4;
    }

    public void setSalesAck5(String salesAck5) {
        this.salesAck5 = salesAck5;
    }

    public void setSalesTarget1(String salesTarget1) {
        this.salesTarget1 = salesTarget1;
    }

    public void setSalesTarget2(String salesTarget2) {
        this.salesTarget2 = salesTarget2;
    }

    public void setSalesTarget3(String salesTarget3) {
        this.salesTarget3 = salesTarget3;
    }

    public void setSalesTarget4(String salesTarget4) {
        this.salesTarget4 = salesTarget4;
    }

    public void setSalesTarget5(String salesTarget5) {
        this.salesTarget5 = salesTarget5;
    }

    public void setSalesMotivation1(String salesMotivation1) {
        this.salesMotivation1 = salesMotivation1;
    }

    public void setSalesMotivation2(String salesMotivation2) {
        this.salesMotivation2 = salesMotivation2;
    }

    public void setSalesMotivation3(String salesMotivation3) {
        this.salesMotivation3 = salesMotivation3;
    }

    public void setSalesMotivation4(String salesMotivation4) {
        this.salesMotivation4 = salesMotivation4;
    }

    public void setSalesMotivation5(String salesMotivation5) {
        this.salesMotivation5 = salesMotivation5;
    }

    public void setSalesObstacle1(String salesObstacle1) {
        this.salesObstacle1 = salesObstacle1;
    }

    public void setSalesObstacle2(String salesObstacle2) {
        this.salesObstacle2 = salesObstacle2;
    }

    public void setSalesObstacle3(String salesObstacle3) {
        this.salesObstacle3 = salesObstacle3;
    }

    public void setSalesObstacle4(String salesObstacle4) {
        this.salesObstacle4 = salesObstacle4;
    }

    public void setSalesObstacle5(String salesObstacle5) {
        this.salesObstacle5 = salesObstacle5;
    }

    public void setMgmtMeeting1(String mgmtMeeting1) {
        this.mgmtMeeting1 = mgmtMeeting1;
    }

    public void setMgmtMeeting2(String mgmtMeeting2) {
        this.mgmtMeeting2 = mgmtMeeting2;
    }

    public void setMgmtMeeting3(String mgmtMeeting3) {
        this.mgmtMeeting3 = mgmtMeeting3;
    }

    public void setMgmtMeeting4(String mgmtMeeting4) {
        this.mgmtMeeting4 = mgmtMeeting4;
    }

    public void setMgmtMeeting5(String mgmtMeeting5) {
        this.mgmtMeeting5 = mgmtMeeting5;
    }

    public void setMgmtCa1(String mgmtCa1) {
        this.mgmtCa1 = mgmtCa1;
    }

    public void setMgmtCa2(String mgmtCa2) {
        this.mgmtCa2 = mgmtCa2;
    }

    public void setMgmtCa3(String mgmtCa3) {
        this.mgmtCa3 = mgmtCa3;
    }

    public void setMgmtCa4(String mgmtCa4) {
        this.mgmtCa4 = mgmtCa4;
    }

    public void setMgmtCa5(String mgmtCa5) {
        this.mgmtCa5 = mgmtCa5;
    }

    public void setMgmtGp1(String mgmtGp1) {
        this.mgmtGp1 = mgmtGp1;
    }

    public void setMgmtGp2(String mgmtGp2) {
        this.mgmtGp2 = mgmtGp2;
    }

    public void setMgmtGp3(String mgmtGp3) {
        this.mgmtGp3 = mgmtGp3;
    }

    public void setMgmtGp4(String mgmtGp4) {
        this.mgmtGp4 = mgmtGp4;
    }

    public void setMgmtGp5(String mgmtGp5) {
        this.mgmtGp5 = mgmtGp5;
    }

    public void setMgmtLearn1(String mgmtLearn1) {
        this.mgmtLearn1 = mgmtLearn1;
    }

    public void setMgmtLearn2(String mgmtLearn2) {
        this.mgmtLearn2 = mgmtLearn2;
    }

    public void setMgmtLearn3(String mgmtLearn3) {
        this.mgmtLearn3 = mgmtLearn3;
    }

    public void setMgmtLearn4(String mgmtLearn4) {
        this.mgmtLearn4 = mgmtLearn4;
    }

    public void setMgmtLearn5(String mgmtLearn5) {
        this.mgmtLearn5 = mgmtLearn5;
    }

    public void setMgmtSheet1(String mgmtSheet1) {
        this.mgmtSheet1 = mgmtSheet1;
    }

    public void setMgmtSheet2(String mgmtSheet2) {
        this.mgmtSheet2 = mgmtSheet2;
    }

    public void setMgmtSheet3(String mgmtSheet3) {
        this.mgmtSheet3 = mgmtSheet3;
    }

    public void setMgmtSheet4(String mgmtSheet4) {
        this.mgmtSheet4 = mgmtSheet4;
    }

    public void setMgmtSheet5(String mgmtSheet5) {
        this.mgmtSheet5 = mgmtSheet5;
    }

    public void setMgmtPolicy1(String mgmtPolicy1) {
        this.mgmtPolicy1 = mgmtPolicy1;
    }

    public void setMgmtPolicy2(String mgmtPolicy2) {
        this.mgmtPolicy2 = mgmtPolicy2;
    }

    public void setMgmtPolicy3(String mgmtPolicy3) {
        this.mgmtPolicy3 = mgmtPolicy3;
    }

    public void setMgmtPolicy4(String mgmtPolicy4) {
        this.mgmtPolicy4 = mgmtPolicy4;
    }

    public void setMgmtPolicy5(String mgmtPolicy5) {
        this.mgmtPolicy5 = mgmtPolicy5;
    }

    public void setMgmtCompiantSales1(String mgmtCompiantSales1) {
        this.mgmtCompiantSales1 = mgmtCompiantSales1;
    }

    public void setMgmtCompiantSales2(String mgmtCompiantSales2) {
        this.mgmtCompiantSales2 = mgmtCompiantSales2;
    }

    public void setMgmtCompiantSales3(String mgmtCompiantSales3) {
        this.mgmtCompiantSales3 = mgmtCompiantSales3;
    }

    public void setMgmtCompiantSales4(String mgmtCompiantSales4) {
        this.mgmtCompiantSales4 = mgmtCompiantSales4;
    }

    public void setMgmtCompiantSales5(String mgmtCompiantSales5) {
        this.mgmtCompiantSales5 = mgmtCompiantSales5;
    }

    public void setMgmtCompiantMethod1(String mgmtCompiantMethod1) {
        this.mgmtCompiantMethod1 = mgmtCompiantMethod1;
    }

    public void setMgmtCompiantMethod2(String mgmtCompiantMethod2) {
        this.mgmtCompiantMethod2 = mgmtCompiantMethod2;
    }

    public void setMgmtCompiantMethod3(String mgmtCompiantMethod3) {
        this.mgmtCompiantMethod3 = mgmtCompiantMethod3;
    }

    public void setMgmtCompiantMethod4(String mgmtCompiantMethod4) {
        this.mgmtCompiantMethod4 = mgmtCompiantMethod4;
    }

    public void setMgmtCompiantMethod5(String mgmtCompiantMethod5) {
        this.mgmtCompiantMethod5 = mgmtCompiantMethod5;
    }

    public void setMgmtCompiantProduct1(String mgmtCompiantProduct1) {
        this.mgmtCompiantProduct1 = mgmtCompiantProduct1;
    }

    public void setMgmtCompiantProduct2(String mgmtCompiantProduct2) {
        this.mgmtCompiantProduct2 = mgmtCompiantProduct2;
    }

    public void setMgmtCompiantProduct3(String mgmtCompiantProduct3) {
        this.mgmtCompiantProduct3 = mgmtCompiantProduct3;
    }

    public void setMgmtCompiantProduct4(String mgmtCompiantProduct4) {
        this.mgmtCompiantProduct4 = mgmtCompiantProduct4;
    }

    public void setMgmtCompiantProduct5(String mgmtCompiantProduct5) {
        this.mgmtCompiantProduct5 = mgmtCompiantProduct5;
    }

    public void setMgmtCompiantAd1(String mgmtCompiantAd1) {
        this.mgmtCompiantAd1 = mgmtCompiantAd1;
    }

    public void setMgmtCompiantAd2(String mgmtCompiantAd2) {
        this.mgmtCompiantAd2 = mgmtCompiantAd2;
    }

    public void setMgmtCompiantAd3(String mgmtCompiantAd3) {
        this.mgmtCompiantAd3 = mgmtCompiantAd3;
    }

    public void setMgmtCompiantAd4(String mgmtCompiantAd4) {
        this.mgmtCompiantAd4 = mgmtCompiantAd4;
    }

    public void setMgmtCompiantAd5(String mgmtCompiantAd5) {
        this.mgmtCompiantAd5 = mgmtCompiantAd5;
    }

    public void setMgmtTraining1(String mgmtTraining1) {
        this.mgmtTraining1 = mgmtTraining1;
    }

    public void setMgmtTraining2(String mgmtTraining2) {
        this.mgmtTraining2 = mgmtTraining2;
    }

    public void setMgmtTraining3(String mgmtTraining3) {
        this.mgmtTraining3 = mgmtTraining3;
    }

    public void setMgmtTraining4(String mgmtTraining4) {
        this.mgmtTraining4 = mgmtTraining4;
    }

    public void setMgmtTraining5(String mgmtTraining5) {
        this.mgmtTraining5 = mgmtTraining5;
    }

    public void setMgmtReport1(String mgmtReport1) {
        this.mgmtReport1 = mgmtReport1;
    }

    public void setMgmtReport2(String mgmtReport2) {
        this.mgmtReport2 = mgmtReport2;
    }

    public void setMgmtReport3(String mgmtReport3) {
        this.mgmtReport3 = mgmtReport3;
    }

    public void setMgmtReport4(String mgmtReport4) {
        this.mgmtReport4 = mgmtReport4;
    }

    public void setMgmtReport5(String mgmtReport5) {
        this.mgmtReport5 = mgmtReport5;
    }

    public void setMgmtPlan1(String mgmtPlan1) {
        this.mgmtPlan1 = mgmtPlan1;
    }

    public void setMgmtPlan2(String mgmtPlan2) {
        this.mgmtPlan2 = mgmtPlan2;
    }

    public void setMgmtPlan3(String mgmtPlan3) {
        this.mgmtPlan3 = mgmtPlan3;
    }

    public void setMgmtPlan4(String mgmtPlan4) {
        this.mgmtPlan4 = mgmtPlan4;
    }

    public void setMgmtPlan5(String mgmtPlan5) {
        this.mgmtPlan5 = mgmtPlan5;
    }

    public void setMgmtMaintain1(String mgmtMaintain1) {
        this.mgmtMaintain1 = mgmtMaintain1;
    }

    public void setMgmtMaintain2(String mgmtMaintain2) {
        this.mgmtMaintain2 = mgmtMaintain2;
    }

    public void setMgmtMaintain3(String mgmtMaintain3) {
        this.mgmtMaintain3 = mgmtMaintain3;
    }

    public void setMgmtMaintain4(String mgmtMaintain4) {
        this.mgmtMaintain4 = mgmtMaintain4;
    }

    public void setMgmtMaintain5(String mgmtMaintain5) {
        this.mgmtMaintain5 = mgmtMaintain5;
    }

    public void setMgmtFace2Face1(String mgmtFace2Face1) {
        this.mgmtFace2Face1 = mgmtFace2Face1;
    }

    public void setMgmtFace2Face2(String mgmtFace2Face2) {
        this.mgmtFace2Face2 = mgmtFace2Face2;
    }

    public void setMgmtFace2Face3(String mgmtFace2Face3) {
        this.mgmtFace2Face3 = mgmtFace2Face3;
    }

    public void setMgmtFace2Face4(String mgmtFace2Face4) {
        this.mgmtFace2Face4 = mgmtFace2Face4;
    }

    public void setMgmtFace2Face5(String mgmtFace2Face5) {
        this.mgmtFace2Face5 = mgmtFace2Face5;
    }

    public void setClubSalesRatio(float clubSalesRatio) {
        this.clubSalesRatio = clubSalesRatio;
    }

    public void setClubAchAppRatio(float clubAchAppRatio) {
        this.clubAchAppRatio = clubAchAppRatio;
    }

    public void setClubAch1(int clubAch1) {
        this.clubAch1 = clubAch1;
    }

    public void setClubAch2(int clubAch2) {
        this.clubAch2 = clubAch2;
    }

    public void setClubAch3(int clubAch3) {
        this.clubAch3 = clubAch3;
    }

    public void setClubAch4(int clubAch4) {
        this.clubAch4 = clubAch4;
    }

    public void setClubAch5(int clubAch5) {
        this.clubAch5 = clubAch5;
    }

    public void setClubAch6(int clubAch6) {
        this.clubAch6 = clubAch6;
    }

    public void setClubMm1(int clubMm1) {
        this.clubMm1 = clubMm1;
    }

    public void setClubMm2(int clubMm2) {
        this.clubMm2 = clubMm2;
    }

    public void setClubMm3(int clubMm3) {
        this.clubMm3 = clubMm3;
    }

    public void setClubMm4(int clubMm4) {
        this.clubMm4 = clubMm4;
    }

    public void setClubMm5(int clubMm5) {
        this.clubMm5 = clubMm5;
    }

    public void setClubMm6(int clubMm6) {
        this.clubMm6 = clubMm6;
    }

    public void setClubApp1(int clubApp1) {
        this.clubApp1 = clubApp1;
    }

    public void setClubApp2(int clubApp2) {
        this.clubApp2 = clubApp2;
    }

    public void setClubApp3(int clubApp3) {
        this.clubApp3 = clubApp3;
    }

    public void setClubApp4(int clubApp4) {
        this.clubApp4 = clubApp4;
    }

    public void setClubApp5(int clubApp5) {
        this.clubApp5 = clubApp5;
    }

    public void setClubApp6(int clubApp6) {
        this.clubApp6 = clubApp6;
    }

    public void setClubNs1(int clubNs1) {
        this.clubNs1 = clubNs1;
    }

    public void setClubNs2(int clubNs2) {
        this.clubNs2 = clubNs2;
    }

    public void setClubNs3(int clubNs3) {
        this.clubNs3 = clubNs3;
    }

    public void setClubNs4(int clubNs4) {
        this.clubNs4 = clubNs4;
    }

    public void setClubNs5(int clubNs5) {
        this.clubNs5 = clubNs5;
    }

    public void setClubNs6(int clubNs6) {
        this.clubNs6 = clubNs6;
    }

    public void setClubLx1(int clubLx1) {
        this.clubLx1 = clubLx1;
    }

    public void setClubLx2(int clubLx2) {
        this.clubLx2 = clubLx2;
    }

    public void setClubLx3(int clubLx3) {
        this.clubLx3 = clubLx3;
    }

    public void setClubLx4(int clubLx4) {
        this.clubLx4 = clubLx4;
    }

    public void setClubLx5(int clubLx5) {
        this.clubLx5 = clubLx5;
    }

    public void setClubLx6(int clubLx6) {
        this.clubLx6 = clubLx6;
    }

    public void setStaff1Name(String staff1Name) {
        this.staff1Name = staff1Name;
    }

    public void setStaff1SalesRatio(float staff1SalesRatio) {
        this.staff1SalesRatio = staff1SalesRatio;
    }

    public void setStaff1AchAppRatio(float staff1AchAppRatio) {
        this.staff1AchAppRatio = staff1AchAppRatio;
    }

    public void setStaff1Ach1(int staff1Ach1) {
        this.staff1Ach1 = staff1Ach1;
    }

    public void setStaff1Ach2(int staff1Ach2) {
        this.staff1Ach2 = staff1Ach2;
    }

    public void setStaff1Ach3(int staff1Ach3) {
        this.staff1Ach3 = staff1Ach3;
    }

    public void setStaff1Ach4(int staff1Ach4) {
        this.staff1Ach4 = staff1Ach4;
    }

    public void setStaff1Ach5(int staff1Ach5) {
        this.staff1Ach5 = staff1Ach5;
    }

    public void setStaff1Ach6(int staff1Ach6) {
        this.staff1Ach6 = staff1Ach6;
    }

    public void setStaff1Mm1(int staff1Mm1) {
        this.staff1Mm1 = staff1Mm1;
    }

    public void setStaff1Mm2(int staff1Mm2) {
        this.staff1Mm2 = staff1Mm2;
    }

    public void setStaff1Mm3(int staff1Mm3) {
        this.staff1Mm3 = staff1Mm3;
    }

    public void setStaff1Mm4(int staff1Mm4) {
        this.staff1Mm4 = staff1Mm4;
    }

    public void setStaff1Mm5(int staff1Mm5) {
        this.staff1Mm5 = staff1Mm5;
    }

    public void setStaff1Mm6(int staff1Mm6) {
        this.staff1Mm6 = staff1Mm6;
    }

    public void setStaff1App1(int staff1App1) {
        this.staff1App1 = staff1App1;
    }

    public void setStaff1App2(int staff1App2) {
        this.staff1App2 = staff1App2;
    }

    public void setStaff1App3(int staff1App3) {
        this.staff1App3 = staff1App3;
    }

    public void setStaff1App4(int staff1App4) {
        this.staff1App4 = staff1App4;
    }

    public void setStaff1App5(int staff1App5) {
        this.staff1App5 = staff1App5;
    }

    public void setStaff1App6(int staff1App6) {
        this.staff1App6 = staff1App6;
    }

    public void setStaff1Ns1(int staff1Ns1) {
        this.staff1Ns1 = staff1Ns1;
    }

    public void setStaff1Ns2(int staff1Ns2) {
        this.staff1Ns2 = staff1Ns2;
    }

    public void setStaff1Ns3(int staff1Ns3) {
        this.staff1Ns3 = staff1Ns3;
    }

    public void setStaff1Ns4(int staff1Ns4) {
        this.staff1Ns4 = staff1Ns4;
    }

    public void setStaff1Ns5(int staff1Ns5) {
        this.staff1Ns5 = staff1Ns5;
    }

    public void setStaff1Ns6(int staff1Ns6) {
        this.staff1Ns6 = staff1Ns6;
    }

    public void setStaff1Lx1(int staff1Lx1) {
        this.staff1Lx1 = staff1Lx1;
    }

    public void setStaff1Lx2(int staff1Lx2) {
        this.staff1Lx2 = staff1Lx2;
    }

    public void setStaff1Lx3(int staff1Lx3) {
        this.staff1Lx3 = staff1Lx3;
    }

    public void setStaff1Lx4(int staff1Lx4) {
        this.staff1Lx4 = staff1Lx4;
    }

    public void setStaff1Lx5(int staff1Lx5) {
        this.staff1Lx5 = staff1Lx5;
    }

    public void setStaff1Lx6(int staff1Lx6) {
        this.staff1Lx6 = staff1Lx6;
    }

    public void setStaff2Name(String staff2Name) {
        this.staff2Name = staff2Name;
    }

    public void setStaff2SalesRatio(float staff2SalesRatio) {
        this.staff2SalesRatio = staff2SalesRatio;
    }

    public void setStaff2AchAppRatio(float staff2AchAppRatio) {
        this.staff2AchAppRatio = staff2AchAppRatio;
    }

    public void setStaff2Ach1(int staff2Ach1) {
        this.staff2Ach1 = staff2Ach1;
    }

    public void setStaff2Ach2(int staff2Ach2) {
        this.staff2Ach2 = staff2Ach2;
    }

    public void setStaff2Ach3(int staff2Ach3) {
        this.staff2Ach3 = staff2Ach3;
    }

    public void setStaff2Ach4(int staff2Ach4) {
        this.staff2Ach4 = staff2Ach4;
    }

    public void setStaff2Ach5(int staff2Ach5) {
        this.staff2Ach5 = staff2Ach5;
    }

    public void setStaff2Ach6(int staff2Ach6) {
        this.staff2Ach6 = staff2Ach6;
    }

    public void setStaff2Mm1(int staff2Mm1) {
        this.staff2Mm1 = staff2Mm1;
    }

    public void setStaff2Mm2(int staff2Mm2) {
        this.staff2Mm2 = staff2Mm2;
    }

    public void setStaff2Mm3(int staff2Mm3) {
        this.staff2Mm3 = staff2Mm3;
    }

    public void setStaff2Mm4(int staff2Mm4) {
        this.staff2Mm4 = staff2Mm4;
    }

    public void setStaff2Mm5(int staff2Mm5) {
        this.staff2Mm5 = staff2Mm5;
    }

    public void setStaff2Mm6(int staff2Mm6) {
        this.staff2Mm6 = staff2Mm6;
    }

    public void setStaff2App1(int staff2App1) {
        this.staff2App1 = staff2App1;
    }

    public void setStaff2App2(int staff2App2) {
        this.staff2App2 = staff2App2;
    }

    public void setStaff2App3(int staff2App3) {
        this.staff2App3 = staff2App3;
    }

    public void setStaff2App4(int staff2App4) {
        this.staff2App4 = staff2App4;
    }

    public void setStaff2App5(int staff2App5) {
        this.staff2App5 = staff2App5;
    }

    public void setStaff2App6(int staff2App6) {
        this.staff2App6 = staff2App6;
    }

    public void setStaff2Ns1(int staff2Ns1) {
        this.staff2Ns1 = staff2Ns1;
    }

    public void setStaff2Ns2(int staff2Ns2) {
        this.staff2Ns2 = staff2Ns2;
    }

    public void setStaff2Ns3(int staff2Ns3) {
        this.staff2Ns3 = staff2Ns3;
    }

    public void setStaff2Ns4(int staff2Ns4) {
        this.staff2Ns4 = staff2Ns4;
    }

    public void setStaff2Ns5(int staff2Ns5) {
        this.staff2Ns5 = staff2Ns5;
    }

    public void setStaff2Ns6(int staff2Ns6) {
        this.staff2Ns6 = staff2Ns6;
    }

    public void setStaff2Lx1(int staff2Lx1) {
        this.staff2Lx1 = staff2Lx1;
    }

    public void setStaff2Lx2(int staff2Lx2) {
        this.staff2Lx2 = staff2Lx2;
    }

    public void setStaff2Lx3(int staff2Lx3) {
        this.staff2Lx3 = staff2Lx3;
    }

    public void setStaff2Lx4(int staff2Lx4) {
        this.staff2Lx4 = staff2Lx4;
    }

    public void setStaff2Lx5(int staff2Lx5) {
        this.staff2Lx5 = staff2Lx5;
    }

    public void setStaff2Lx6(int staff2Lx6) {
        this.staff2Lx6 = staff2Lx6;
    }

    public void setStaff3Name(String staff3Name) {
        this.staff3Name = staff3Name;
    }

    public void setStaff3SalesRatio(float staff3SalesRatio) {
        this.staff3SalesRatio = staff3SalesRatio;
    }

    public void setStaff3AchAppRatio(float staff3AchAppRatio) {
        this.staff3AchAppRatio = staff3AchAppRatio;
    }

    public void setStaff3Ach1(int staff3Ach1) {
        this.staff3Ach1 = staff3Ach1;
    }

    public void setStaff3Ach2(int staff3Ach2) {
        this.staff3Ach2 = staff3Ach2;
    }

    public void setStaff3Ach3(int staff3Ach3) {
        this.staff3Ach3 = staff3Ach3;
    }

    public void setStaff3Ach4(int staff3Ach4) {
        this.staff3Ach4 = staff3Ach4;
    }

    public void setStaff3Ach5(int staff3Ach5) {
        this.staff3Ach5 = staff3Ach5;
    }

    public void setStaff3Ach6(int staff3Ach6) {
        this.staff3Ach6 = staff3Ach6;
    }

    public void setStaff3Mm1(int staff3Mm1) {
        this.staff3Mm1 = staff3Mm1;
    }

    public void setStaff3Mm2(int staff3Mm2) {
        this.staff3Mm2 = staff3Mm2;
    }

    public void setStaff3Mm3(int staff3Mm3) {
        this.staff3Mm3 = staff3Mm3;
    }

    public void setStaff3Mm4(int staff3Mm4) {
        this.staff3Mm4 = staff3Mm4;
    }

    public void setStaff3Mm5(int staff3Mm5) {
        this.staff3Mm5 = staff3Mm5;
    }

    public void setStaff3Mm6(int staff3Mm6) {
        this.staff3Mm6 = staff3Mm6;
    }

    public void setStaff3App1(int staff3App1) {
        this.staff3App1 = staff3App1;
    }

    public void setStaff3App2(int staff3App2) {
        this.staff3App2 = staff3App2;
    }

    public void setStaff3App3(int staff3App3) {
        this.staff3App3 = staff3App3;
    }

    public void setStaff3App4(int staff3App4) {
        this.staff3App4 = staff3App4;
    }

    public void setStaff3App5(int staff3App5) {
        this.staff3App5 = staff3App5;
    }

    public void setStaff3App6(int staff3App6) {
        this.staff3App6 = staff3App6;
    }

    public void setStaff3Ns1(int staff3Ns1) {
        this.staff3Ns1 = staff3Ns1;
    }

    public void setStaff3Ns2(int staff3Ns2) {
        this.staff3Ns2 = staff3Ns2;
    }

    public void setStaff3Ns3(int staff3Ns3) {
        this.staff3Ns3 = staff3Ns3;
    }

    public void setStaff3Ns4(int staff3Ns4) {
        this.staff3Ns4 = staff3Ns4;
    }

    public void setStaff3Ns5(int staff3Ns5) {
        this.staff3Ns5 = staff3Ns5;
    }

    public void setStaff3Ns6(int staff3Ns6) {
        this.staff3Ns6 = staff3Ns6;
    }

    public void setStaff3Lx1(int staff3Lx1) {
        this.staff3Lx1 = staff3Lx1;
    }

    public void setStaff3Lx2(int staff3Lx2) {
        this.staff3Lx2 = staff3Lx2;
    }

    public void setStaff3Lx3(int staff3Lx3) {
        this.staff3Lx3 = staff3Lx3;
    }

    public void setStaff3Lx4(int staff3Lx4) {
        this.staff3Lx4 = staff3Lx4;
    }

    public void setStaff3Lx5(int staff3Lx5) {
        this.staff3Lx5 = staff3Lx5;
    }

    public void setStaff3Lx6(int staff3Lx6) {
        this.staff3Lx6 = staff3Lx6;
    }

    public void setStaff4Name(String staff4Name) {
        this.staff4Name = staff4Name;
    }

    public void setStaff4SalesRatio(float staff4SalesRatio) {
        this.staff4SalesRatio = staff4SalesRatio;
    }

    public void setStaff4AchAppRatio(float staff4AchAppRatio) {
        this.staff4AchAppRatio = staff4AchAppRatio;
    }

    public void setStaff4Ach1(int staff4Ach1) {
        this.staff4Ach1 = staff4Ach1;
    }

    public void setStaff4Ach2(int staff4Ach2) {
        this.staff4Ach2 = staff4Ach2;
    }

    public void setStaff4Ach3(int staff4Ach3) {
        this.staff4Ach3 = staff4Ach3;
    }

    public void setStaff4Ach4(int staff4Ach4) {
        this.staff4Ach4 = staff4Ach4;
    }

    public void setStaff4Ach5(int staff4Ach5) {
        this.staff4Ach5 = staff4Ach5;
    }

    public void setStaff4Ach6(int staff4Ach6) {
        this.staff4Ach6 = staff4Ach6;
    }

    public void setStaff4Mm1(int staff4Mm1) {
        this.staff4Mm1 = staff4Mm1;
    }

    public void setStaff4Mm2(int staff4Mm2) {
        this.staff4Mm2 = staff4Mm2;
    }

    public void setStaff4Mm3(int staff4Mm3) {
        this.staff4Mm3 = staff4Mm3;
    }

    public void setStaff4Mm4(int staff4Mm4) {
        this.staff4Mm4 = staff4Mm4;
    }

    public void setStaff4Mm5(int staff4Mm5) {
        this.staff4Mm5 = staff4Mm5;
    }

    public void setStaff4Mm6(int staff4Mm6) {
        this.staff4Mm6 = staff4Mm6;
    }

    public void setStaff4App1(int staff4App1) {
        this.staff4App1 = staff4App1;
    }

    public void setStaff4App2(int staff4App2) {
        this.staff4App2 = staff4App2;
    }

    public void setStaff4App3(int staff4App3) {
        this.staff4App3 = staff4App3;
    }

    public void setStaff4App4(int staff4App4) {
        this.staff4App4 = staff4App4;
    }

    public void setStaff4App5(int staff4App5) {
        this.staff4App5 = staff4App5;
    }

    public void setStaff4App6(int staff4App6) {
        this.staff4App6 = staff4App6;
    }

    public void setStaff4Ns1(int staff4Ns1) {
        this.staff4Ns1 = staff4Ns1;
    }

    public void setStaff4Ns2(int staff4Ns2) {
        this.staff4Ns2 = staff4Ns2;
    }

    public void setStaff4Ns3(int staff4Ns3) {
        this.staff4Ns3 = staff4Ns3;
    }

    public void setStaff4Ns4(int staff4Ns4) {
        this.staff4Ns4 = staff4Ns4;
    }

    public void setStaff4Ns5(int staff4Ns5) {
        this.staff4Ns5 = staff4Ns5;
    }

    public void setStaff4Ns6(int staff4Ns6) {
        this.staff4Ns6 = staff4Ns6;
    }

    public void setStaff4Lx1(int staff4Lx1) {
        this.staff4Lx1 = staff4Lx1;
    }

    public void setStaff4Lx2(int staff4Lx2) {
        this.staff4Lx2 = staff4Lx2;
    }

    public void setStaff4Lx3(int staff4Lx3) {
        this.staff4Lx3 = staff4Lx3;
    }

    public void setStaff4Lx4(int staff4Lx4) {
        this.staff4Lx4 = staff4Lx4;
    }

    public void setStaff4Lx5(int staff4Lx5) {
        this.staff4Lx5 = staff4Lx5;
    }

    public void setStaff4Lx6(int staff4Lx6) {
        this.staff4Lx6 = staff4Lx6;
    }

    public void setStaff5Name(String staff5Name) {
        this.staff5Name = staff5Name;
    }

    public void setStaff5SalesRatio(float staff5SalesRatio) {
        this.staff5SalesRatio = staff5SalesRatio;
    }

    public void setStaff5AchAppRatio(float staff5AchAppRatio) {
        this.staff5AchAppRatio = staff5AchAppRatio;
    }

    public void setStaff5Ach1(int staff5Ach1) {
        this.staff5Ach1 = staff5Ach1;
    }

    public void setStaff5Ach2(int staff5Ach2) {
        this.staff5Ach2 = staff5Ach2;
    }

    public void setStaff5Ach3(int staff5Ach3) {
        this.staff5Ach3 = staff5Ach3;
    }

    public void setStaff5Ach4(int staff5Ach4) {
        this.staff5Ach4 = staff5Ach4;
    }

    public void setStaff5Ach5(int staff5Ach5) {
        this.staff5Ach5 = staff5Ach5;
    }

    public void setStaff5Ach6(int staff5Ach6) {
        this.staff5Ach6 = staff5Ach6;
    }

    public void setStaff5Mm1(int staff5Mm1) {
        this.staff5Mm1 = staff5Mm1;
    }

    public void setStaff5Mm2(int staff5Mm2) {
        this.staff5Mm2 = staff5Mm2;
    }

    public void setStaff5Mm3(int staff5Mm3) {
        this.staff5Mm3 = staff5Mm3;
    }

    public void setStaff5Mm4(int staff5Mm4) {
        this.staff5Mm4 = staff5Mm4;
    }

    public void setStaff5Mm5(int staff5Mm5) {
        this.staff5Mm5 = staff5Mm5;
    }

    public void setStaff5Mm6(int staff5Mm6) {
        this.staff5Mm6 = staff5Mm6;
    }

    public void setStaff5App1(int staff5App1) {
        this.staff5App1 = staff5App1;
    }

    public void setStaff5App2(int staff5App2) {
        this.staff5App2 = staff5App2;
    }

    public void setStaff5App3(int staff5App3) {
        this.staff5App3 = staff5App3;
    }

    public void setStaff5App4(int staff5App4) {
        this.staff5App4 = staff5App4;
    }

    public void setStaff5App5(int staff5App5) {
        this.staff5App5 = staff5App5;
    }

    public void setStaff5App6(int staff5App6) {
        this.staff5App6 = staff5App6;
    }

    public void setStaff5Ns1(int staff5Ns1) {
        this.staff5Ns1 = staff5Ns1;
    }

    public void setStaff5Ns2(int staff5Ns2) {
        this.staff5Ns2 = staff5Ns2;
    }

    public void setStaff5Ns3(int staff5Ns3) {
        this.staff5Ns3 = staff5Ns3;
    }

    public void setStaff5Ns4(int staff5Ns4) {
        this.staff5Ns4 = staff5Ns4;
    }

    public void setStaff5Ns5(int staff5Ns5) {
        this.staff5Ns5 = staff5Ns5;
    }

    public void setStaff5Ns6(int staff5Ns6) {
        this.staff5Ns6 = staff5Ns6;
    }

    public void setStaff5Lx1(int staff5Lx1) {
        this.staff5Lx1 = staff5Lx1;
    }

    public void setStaff5Lx2(int staff5Lx2) {
        this.staff5Lx2 = staff5Lx2;
    }

    public void setStaff5Lx3(int staff5Lx3) {
        this.staff5Lx3 = staff5Lx3;
    }

    public void setStaff5Lx4(int staff5Lx4) {
        this.staff5Lx4 = staff5Lx4;
    }

    public void setStaff5Lx5(int staff5Lx5) {
        this.staff5Lx5 = staff5Lx5;
    }

    public void setStaff5Lx6(int staff5Lx6) {
        this.staff5Lx6 = staff5Lx6;
    }

    public void setStaff6Name(String staff6Name) {
        this.staff6Name = staff6Name;
    }

    public void setStaff6SalesRatio(float staff6SalesRatio) {
        this.staff6SalesRatio = staff6SalesRatio;
    }

    public void setStaff6AchAppRatio(float staff6AchAppRatio) {
        this.staff6AchAppRatio = staff6AchAppRatio;
    }

    public void setStaff6Ach1(int staff6Ach1) {
        this.staff6Ach1 = staff6Ach1;
    }

    public void setStaff6Ach2(int staff6Ach2) {
        this.staff6Ach2 = staff6Ach2;
    }

    public void setStaff6Ach3(int staff6Ach3) {
        this.staff6Ach3 = staff6Ach3;
    }

    public void setStaff6Ach4(int staff6Ach4) {
        this.staff6Ach4 = staff6Ach4;
    }

    public void setStaff6Ach5(int staff6Ach5) {
        this.staff6Ach5 = staff6Ach5;
    }

    public void setStaff6Ach6(int staff6Ach6) {
        this.staff6Ach6 = staff6Ach6;
    }

    public void setStaff6Mm1(int staff6Mm1) {
        this.staff6Mm1 = staff6Mm1;
    }

    public void setStaff6Mm2(int staff6Mm2) {
        this.staff6Mm2 = staff6Mm2;
    }

    public void setStaff6Mm3(int staff6Mm3) {
        this.staff6Mm3 = staff6Mm3;
    }

    public void setStaff6Mm4(int staff6Mm4) {
        this.staff6Mm4 = staff6Mm4;
    }

    public void setStaff6Mm5(int staff6Mm5) {
        this.staff6Mm5 = staff6Mm5;
    }

    public void setStaff6Mm6(int staff6Mm6) {
        this.staff6Mm6 = staff6Mm6;
    }

    public void setStaff6App1(int staff6App1) {
        this.staff6App1 = staff6App1;
    }

    public void setStaff6App2(int staff6App2) {
        this.staff6App2 = staff6App2;
    }

    public void setStaff6App3(int staff6App3) {
        this.staff6App3 = staff6App3;
    }

    public void setStaff6App4(int staff6App4) {
        this.staff6App4 = staff6App4;
    }

    public void setStaff6App5(int staff6App5) {
        this.staff6App5 = staff6App5;
    }

    public void setStaff6App6(int staff6App6) {
        this.staff6App6 = staff6App6;
    }

    public void setStaff6Ns1(int staff6Ns1) {
        this.staff6Ns1 = staff6Ns1;
    }

    public void setStaff6Ns2(int staff6Ns2) {
        this.staff6Ns2 = staff6Ns2;
    }

    public void setStaff6Ns3(int staff6Ns3) {
        this.staff6Ns3 = staff6Ns3;
    }

    public void setStaff6Ns4(int staff6Ns4) {
        this.staff6Ns4 = staff6Ns4;
    }

    public void setStaff6Ns5(int staff6Ns5) {
        this.staff6Ns5 = staff6Ns5;
    }

    public void setStaff6Ns6(int staff6Ns6) {
        this.staff6Ns6 = staff6Ns6;
    }

    public void setStaff6Lx1(int staff6Lx1) {
        this.staff6Lx1 = staff6Lx1;
    }

    public void setStaff6Lx2(int staff6Lx2) {
        this.staff6Lx2 = staff6Lx2;
    }

    public void setStaff6Lx3(int staff6Lx3) {
        this.staff6Lx3 = staff6Lx3;
    }

    public void setStaff6Lx4(int staff6Lx4) {
        this.staff6Lx4 = staff6Lx4;
    }

    public void setStaff6Lx5(int staff6Lx5) {
        this.staff6Lx5 = staff6Lx5;
    }

    public void setStaff6Lx6(int staff6Lx6) {
        this.staff6Lx6 = staff6Lx6;
    }

    public void setThisPlan(String thisPlan) {
        this.thisPlan = thisPlan;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public int getClubId() {
        return clubId;
    }

    public int getCaYear() {
        return caYear;
    }

    public int getCaMonth() {
        return caMonth;
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

    public float getGoalsLastShowRatio() {
        return goalsLastShowRatio;
    }

    public float getGoalsLastSalesRatio() {
        return goalsLastSalesRatio;
    }

    public float getGoalsExitsRatio() {
        return goalsExitsRatio;
    }

    public int getGoalsNewSales() {
        return goalsNewSales;
    }

    public int getGoalsAppoints() {
        return goalsAppoints;
    }

    public int getSvcTm1() {
        return svcTm1;
    }

    public int getSvcTm2() {
        return svcTm2;
    }

    public int getSvcTm3() {
        return svcTm3;
    }

    public int getSvcTm4() {
        return svcTm4;
    }

    public int getSvcTm5() {
        return svcTm5;
    }

    public int getSvcTm6() {
        return svcTm6;
    }

    public int getSvcShiftIn1() {
        return svcShiftIn1;
    }

    public int getSvcShiftIn2() {
        return svcShiftIn2;
    }

    public int getSvcShiftIn3() {
        return svcShiftIn3;
    }

    public int getSvcShiftIn4() {
        return svcShiftIn4;
    }

    public int getSvcShiftIn5() {
        return svcShiftIn5;
    }

    public int getSvcShiftIn6() {
        return svcShiftIn6;
    }

    public int getSvcShiftOut1() {
        return svcShiftOut1;
    }

    public int getSvcShiftOut2() {
        return svcShiftOut2;
    }

    public int getSvcShiftOut3() {
        return svcShiftOut3;
    }

    public int getSvcShiftOut4() {
        return svcShiftOut4;
    }

    public int getSvcShiftOut5() {
        return svcShiftOut5;
    }

    public int getSvcShiftOut6() {
        return svcShiftOut6;
    }

    public int getSvcHold1() {
        return svcHold1;
    }

    public int getSvcHold2() {
        return svcHold2;
    }

    public int getSvcHold3() {
        return svcHold3;
    }

    public int getSvcHold4() {
        return svcHold4;
    }

    public int getSvcHold5() {
        return svcHold5;
    }

    public int getSvcHold6() {
        return svcHold6;
    }

    public int getSvcActive1() {
        return svcActive1;
    }

    public int getSvcActive2() {
        return svcActive2;
    }

    public int getSvcActive3() {
        return svcActive3;
    }

    public int getSvcActive4() {
        return svcActive4;
    }

    public int getSvcActive5() {
        return svcActive5;
    }

    public int getSvcActive6() {
        return svcActive6;
    }

    public float getSvcHoldRatio1() {
        return svcHoldRatio1;
    }

    public float getSvcHoldRatio2() {
        return svcHoldRatio2;
    }

    public float getSvcHoldRatio3() {
        return svcHoldRatio3;
    }

    public float getSvcHoldRatio4() {
        return svcHoldRatio4;
    }

    public float getSvcHoldRatio5() {
        return svcHoldRatio5;
    }

    public float getSvcHoldRatio6() {
        return svcHoldRatio6;
    }

    public int getSvcTotalWo1() {
        return svcTotalWo1;
    }

    public int getSvcTotalWo2() {
        return svcTotalWo2;
    }

    public int getSvcTotalWo3() {
        return svcTotalWo3;
    }

    public int getSvcTotalWo4() {
        return svcTotalWo4;
    }

    public int getSvcTotalWo5() {
        return svcTotalWo5;
    }

    public int getSvcTotalWo6() {
        return svcTotalWo6;
    }

    public float getSvcAvgWo1() {
        return svcAvgWo1;
    }

    public float getSvcAvgWo2() {
        return svcAvgWo2;
    }

    public float getSvcAvgWo3() {
        return svcAvgWo3;
    }

    public float getSvcAvgWo4() {
        return svcAvgWo4;
    }

    public float getSvcAvgWo5() {
        return svcAvgWo5;
    }

    public float getSvcAvgWo6() {
        return svcAvgWo6;
    }

    public int getSvcMaxWo1() {
        return svcMaxWo1;
    }

    public int getSvcMaxWo2() {
        return svcMaxWo2;
    }

    public int getSvcMaxWo3() {
        return svcMaxWo3;
    }

    public int getSvcMaxWo4() {
        return svcMaxWo4;
    }

    public int getSvcMaxWo5() {
        return svcMaxWo5;
    }

    public float getSvcMaxWo6() {
        return svcMaxWo6;
    }

    public int getSvcExits1() {
        return svcExits1;
    }

    public int getSvcExits2() {
        return svcExits2;
    }

    public int getSvcExits3() {
        return svcExits3;
    }

    public int getSvcExits4() {
        return svcExits4;
    }

    public int getSvcExits5() {
        return svcExits5;
    }

    public int getSvcExits6() {
        return svcExits6;
    }

    public float getSvcExitsRatio1() {
        return svcExitsRatio1;
    }

    public float getSvcExitsRatio2() {
        return svcExitsRatio2;
    }

    public float getSvcExitsRatio3() {
        return svcExitsRatio3;
    }

    public float getSvcExitsRatio4() {
        return svcExitsRatio4;
    }

    public float getSvcExitsRatio5() {
        return svcExitsRatio5;
    }

    public float getSvcExitsRatio6() {
        return svcExitsRatio6;
    }

    public int getSvcMeasure1() {
        return svcMeasure1;
    }

    public int getSvcMeasure2() {
        return svcMeasure2;
    }

    public int getSvcMeasure3() {
        return svcMeasure3;
    }

    public int getSvcMeasure4() {
        return svcMeasure4;
    }

    public int getSvcMeasure5() {
        return svcMeasure5;
    }

    public int getSvcMeasure6() {
        return svcMeasure6;
    }

    public float getSvcMeasureRatio1() {
        return svcMeasureRatio1;
    }

    public float getSvcMeasureRatio2() {
        return svcMeasureRatio2;
    }

    public float getSvcMeasureRatio3() {
        return svcMeasureRatio3;
    }

    public float getSvcMeasureRatio4() {
        return svcMeasureRatio4;
    }

    public float getSvcMeasureRatio5() {
        return svcMeasureRatio5;
    }

    public float getSvcMeasureRatio6() {
        return svcMeasureRatio6;
    }

    public int getSvc12_5() {
        return svc12_5;
    }

    public float getSvc12_6() {
        return svc12_6;
    }

    public int getSvc8to11_5() {
        return svc8to11_5;
    }

    public float getSvc8to11_6() {
        return svc8to11_6;
    }

    public int getSvc4to7_5() {
        return svc4to7_5;
    }

    public float getSvc4to7_6() {
        return svc4to7_6;
    }

    public int getSvc1to3_5() {
        return svc1to3_5;
    }

    public float getSvc1to3_6() {
        return svc1to3_6;
    }

    public int getSvc0_5() {
        return svc0_5;
    }

    public float getSvc0_6() {
        return svc0_6;
    }

    public String getSvc3More1() {
        return svc3More1;
    }

    public String getSvc3More2() {
        return svc3More2;
    }

    public String getSvc3More3() {
        return svc3More3;
    }

    public String getSvc3More4() {
        return svc3More4;
    }

    public String getSvc3More5() {
        return svc3More5;
    }

    public String getSvcInactive1() {
        return svcInactive1;
    }

    public String getSvcInactive2() {
        return svcInactive2;
    }

    public String getSvcInactive3() {
        return svcInactive3;
    }

    public String getSvcInactive4() {
        return svcInactive4;
    }

    public String getSvcInactive5() {
        return svcInactive5;
    }

    public String getSvcFwoReview1() {
        return svcFwoReview1;
    }

    public String getSvcFwoReview2() {
        return svcFwoReview2;
    }

    public String getSvcFwoReview3() {
        return svcFwoReview3;
    }

    public String getSvcFwoReview4() {
        return svcFwoReview4;
    }

    public String getSvcFwoReview5() {
        return svcFwoReview5;
    }

    public String getSvcInterview1() {
        return svcInterview1;
    }

    public String getSvcInterview2() {
        return svcInterview2;
    }

    public String getSvcInterview3() {
        return svcInterview3;
    }

    public String getSvcInterview4() {
        return svcInterview4;
    }

    public String getSvcInterview5() {
        return svcInterview5;
    }

    public String getSvcThanks1() {
        return svcThanks1;
    }

    public String getSvcThanks2() {
        return svcThanks2;
    }

    public String getSvcThanks3() {
        return svcThanks3;
    }

    public String getSvcThanks4() {
        return svcThanks4;
    }

    public String getSvcThanks5() {
        return svcThanks5;
    }

    public String getSvc3C1() {
        return svc3C1;
    }

    public String getSvc3C2() {
        return svc3C2;
    }

    public String getSvc3C3() {
        return svc3C3;
    }

    public String getSvc3C4() {
        return svc3C4;
    }

    public String getSvc3C5() {
        return svc3C5;
    }

    public String getSvcReward1() {
        return svcReward1;
    }

    public String getSvcReward2() {
        return svcReward2;
    }

    public String getSvcReward3() {
        return svcReward3;
    }

    public String getSvcReward4() {
        return svcReward4;
    }

    public String getSvcReward5() {
        return svcReward5;
    }

    public String getSvcLoyal1() {
        return svcLoyal1;
    }

    public String getSvcLoyal2() {
        return svcLoyal2;
    }

    public String getSvcLoyal3() {
        return svcLoyal3;
    }

    public String getSvcLoyal4() {
        return svcLoyal4;
    }

    public String getSvcLoyal5() {
        return svcLoyal5;
    }

    public int getCmPostFlyer1() {
        return cmPostFlyer1;
    }

    public int getCmPostFlyer2() {
        return cmPostFlyer2;
    }

    public int getCmPostFlyer3() {
        return cmPostFlyer3;
    }

    public int getCmPostFlyer4() {
        return cmPostFlyer4;
    }

    public int getCmPostFlyer5() {
        return cmPostFlyer5;
    }

    public int getCmPostFlyer6() {
        return cmPostFlyer6;
    }

    public int getCmHandFlyer1() {
        return cmHandFlyer1;
    }

    public int getCmHandFlyer2() {
        return cmHandFlyer2;
    }

    public int getCmHandFlyer3() {
        return cmHandFlyer3;
    }

    public int getCmHandFlyer4() {
        return cmHandFlyer4;
    }

    public int getCmHandFlyer5() {
        return cmHandFlyer5;
    }

    public int getCmHandFlyer6() {
        return cmHandFlyer6;
    }

    public float getCmHandFlyerHours1() {
        return cmHandFlyerHours1;
    }

    public float getCmHandFlyerHours2() {
        return cmHandFlyerHours2;
    }

    public float getCmHandFlyerHours3() {
        return cmHandFlyerHours3;
    }

    public float getCmHandFlyerHours4() {
        return cmHandFlyerHours4;
    }

    public float getCmHandFlyerHours5() {
        return cmHandFlyerHours5;
    }

    public float getCmHandFlyerHours6() {
        return cmHandFlyerHours6;
    }

    public float getCmOutGpHours1() {
        return cmOutGpHours1;
    }

    public float getCmOutGpHours2() {
        return cmOutGpHours2;
    }

    public float getCmOutGpHours3() {
        return cmOutGpHours3;
    }

    public float getCmOutGpHours4() {
        return cmOutGpHours4;
    }

    public float getCmOutGpHours5() {
        return cmOutGpHours5;
    }

    public float getCmOutGpHours6() {
        return cmOutGpHours6;
    }

    public int getCmOutGp1() {
        return cmOutGp1;
    }

    public int getCmOutGp2() {
        return cmOutGp2;
    }

    public int getCmOutGp3() {
        return cmOutGp3;
    }

    public int getCmOutGp4() {
        return cmOutGp4;
    }

    public int getCmOutGp5() {
        return cmOutGp5;
    }

    public int getCmOutGp6() {
        return cmOutGp6;
    }

    public int getCmCpBox1() {
        return cmCpBox1;
    }

    public int getCmCpBox2() {
        return cmCpBox2;
    }

    public int getCmCpBox3() {
        return cmCpBox3;
    }

    public int getCmCpBox4() {
        return cmCpBox4;
    }

    public int getCmCpBox5() {
        return cmCpBox5;
    }

    public int getCmCpBox6() {
        return cmCpBox6;
    }

    public int getCmOutGot1() {
        return cmOutGot1;
    }

    public int getCmOutGot2() {
        return cmOutGot2;
    }

    public int getCmOutGot3() {
        return cmOutGot3;
    }

    public int getCmOutGot4() {
        return cmOutGot4;
    }

    public int getCmOutGot5() {
        return cmOutGot5;
    }

    public int getCmOutGot6() {
        return cmOutGot6;
    }

    public int getCmInGot1() {
        return cmInGot1;
    }

    public int getCmInGot2() {
        return cmInGot2;
    }

    public int getCmInGot3() {
        return cmInGot3;
    }

    public int getCmInGot4() {
        return cmInGot4;
    }

    public int getCmInGot5() {
        return cmInGot5;
    }

    public int getCmInGot6() {
        return cmInGot6;
    }

    public int getCmBlogGot1() {
        return cmBlogGot1;
    }

    public int getCmBlogGot2() {
        return cmBlogGot2;
    }

    public int getCmBlogGot3() {
        return cmBlogGot3;
    }

    public int getCmBlogGot4() {
        return cmBlogGot4;
    }

    public int getCmBlogGot5() {
        return cmBlogGot5;
    }

    public int getCmBlogGot6() {
        return cmBlogGot6;
    }

    public int getCmBagGot1() {
        return cmBagGot1;
    }

    public int getCmBagGot2() {
        return cmBagGot2;
    }

    public int getCmBagGot3() {
        return cmBagGot3;
    }

    public int getCmBagGot4() {
        return cmBagGot4;
    }

    public int getCmBagGot5() {
        return cmBagGot5;
    }

    public int getCmBagGot6() {
        return cmBagGot6;
    }

    public int getCmTotalGot1() {
        return cmTotalGot1;
    }

    public int getCmTotalGot2() {
        return cmTotalGot2;
    }

    public int getCmTotalGot3() {
        return cmTotalGot3;
    }

    public int getCmTotalGot4() {
        return cmTotalGot4;
    }

    public int getCmTotalGot5() {
        return cmTotalGot5;
    }

    public int getCmTotalGot6() {
        return cmTotalGot6;
    }

    public int getCmCallIn1() {
        return cmCallIn1;
    }

    public int getCmCallIn2() {
        return cmCallIn2;
    }

    public int getCmCallIn3() {
        return cmCallIn3;
    }

    public int getCmCallIn4() {
        return cmCallIn4;
    }

    public int getCmCallIn5() {
        return cmCallIn5;
    }

    public int getCmCallIn6() {
        return cmCallIn6;
    }

    public int getCmOutGotCall1() {
        return cmOutGotCall1;
    }

    public int getCmOutGotCall2() {
        return cmOutGotCall2;
    }

    public int getCmOutGotCall3() {
        return cmOutGotCall3;
    }

    public int getCmOutGotCall4() {
        return cmOutGotCall4;
    }

    public int getCmOutGotCall5() {
        return cmOutGotCall5;
    }

    public int getCmOutGotCall6() {
        return cmOutGotCall6;
    }

    public int getCmInGotCall1() {
        return cmInGotCall1;
    }

    public int getCmInGotCall2() {
        return cmInGotCall2;
    }

    public int getCmInGotCall3() {
        return cmInGotCall3;
    }

    public int getCmInGotCall4() {
        return cmInGotCall4;
    }

    public int getCmInGotCall5() {
        return cmInGotCall5;
    }

    public int getCmInGotCall6() {
        return cmInGotCall6;
    }

    public int getCmBlogGotCall1() {
        return cmBlogGotCall1;
    }

    public int getCmBlogGotCall2() {
        return cmBlogGotCall2;
    }

    public int getCmBlogGotCall3() {
        return cmBlogGotCall3;
    }

    public int getCmBlogGotCall4() {
        return cmBlogGotCall4;
    }

    public int getCmBlogGotCall5() {
        return cmBlogGotCall5;
    }

    public int getCmBlogGotCall6() {
        return cmBlogGotCall6;
    }

    public int getCmBagGotCall1() {
        return cmBagGotCall1;
    }

    public int getCmBagGotCall2() {
        return cmBagGotCall2;
    }

    public int getCmBagGotCall3() {
        return cmBagGotCall3;
    }

    public int getCmBagGotCall4() {
        return cmBagGotCall4;
    }

    public int getCmBagGotCall5() {
        return cmBagGotCall5;
    }

    public int getCmBagGotCall6() {
        return cmBagGotCall6;
    }

    public int getCmOwnRefs1() {
        return cmOwnRefs1;
    }

    public int getCmOwnRefs2() {
        return cmOwnRefs2;
    }

    public int getCmOwnRefs3() {
        return cmOwnRefs3;
    }

    public int getCmOwnRefs4() {
        return cmOwnRefs4;
    }

    public int getCmOwnRefs5() {
        return cmOwnRefs5;
    }

    public int getCmOwnRefs6() {
        return cmOwnRefs6;
    }

    public int getCmOtherRefs1() {
        return cmOtherRefs1;
    }

    public int getCmOtherRefs2() {
        return cmOtherRefs2;
    }

    public int getCmOtherRefs3() {
        return cmOtherRefs3;
    }

    public int getCmOtherRefs4() {
        return cmOtherRefs4;
    }

    public int getCmOtherRefs5() {
        return cmOtherRefs5;
    }

    public int getCmOtherRefs6() {
        return cmOtherRefs6;
    }

    public int getCmNewspaper1() {
        return cmNewspaper1;
    }

    public int getCmNewspaper2() {
        return cmNewspaper2;
    }

    public int getCmNewspaper3() {
        return cmNewspaper3;
    }

    public int getCmNewspaper4() {
        return cmNewspaper4;
    }

    public int getCmNewspaper5() {
        return cmNewspaper5;
    }

    public int getCmNewspaper6() {
        return cmNewspaper6;
    }

    public int getCmTv1() {
        return cmTv1;
    }

    public int getCmTv2() {
        return cmTv2;
    }

    public int getCmTv3() {
        return cmTv3;
    }

    public int getCmTv4() {
        return cmTv4;
    }

    public int getCmTv5() {
        return cmTv5;
    }

    public int getCmTv6() {
        return cmTv6;
    }

    public int getCmInternet1() {
        return cmInternet1;
    }

    public int getCmInternet2() {
        return cmInternet2;
    }

    public int getCmInternet3() {
        return cmInternet3;
    }

    public int getCmInternet4() {
        return cmInternet4;
    }

    public int getCmInternet5() {
        return cmInternet5;
    }

    public int getCmInternet6() {
        return cmInternet6;
    }

    public int getCmSign1() {
        return cmSign1;
    }

    public int getCmSign2() {
        return cmSign2;
    }

    public int getCmSign3() {
        return cmSign3;
    }

    public int getCmSign4() {
        return cmSign4;
    }

    public int getCmSign5() {
        return cmSign5;
    }

    public int getCmSign6() {
        return cmSign6;
    }

    public int getCmMate1() {
        return cmMate1;
    }

    public int getCmMate2() {
        return cmMate2;
    }

    public int getCmMate3() {
        return cmMate3;
    }

    public int getCmMate4() {
        return cmMate4;
    }

    public int getCmMate5() {
        return cmMate5;
    }

    public int getCmMate6() {
        return cmMate6;
    }

    public int getCmOthers1() {
        return cmOthers1;
    }

    public int getCmOthers2() {
        return cmOthers2;
    }

    public int getCmOthers3() {
        return cmOthers3;
    }

    public int getCmOthers4() {
        return cmOthers4;
    }

    public int getCmOthers5() {
        return cmOthers5;
    }

    public int getCmOthers6() {
        return cmOthers6;
    }

    public int getCmMailAgpIn1() {
        return cmMailAgpIn1;
    }

    public int getCmMailAgpIn2() {
        return cmMailAgpIn2;
    }

    public int getCmMailAgpIn3() {
        return cmMailAgpIn3;
    }

    public int getCmMailAgpIn4() {
        return cmMailAgpIn4;
    }

    public int getCmMailAgpIn5() {
        return cmMailAgpIn5;
    }

    public int getCmMailAgpIn6() {
        return cmMailAgpIn6;
    }

    public int getCmPostFlyerAgpIn1() {
        return cmPostFlyerAgpIn1;
    }

    public int getCmPostFlyerAgpIn2() {
        return cmPostFlyerAgpIn2;
    }

    public int getCmPostFlyerAgpIn3() {
        return cmPostFlyerAgpIn3;
    }

    public int getCmPostFlyerAgpIn4() {
        return cmPostFlyerAgpIn4;
    }

    public int getCmPostFlyerAgpIn5() {
        return cmPostFlyerAgpIn5;
    }

    public int getCmPostFlyerAgpIn6() {
        return cmPostFlyerAgpIn6;
    }

    public int getCmHandFlyerAgpIn1() {
        return cmHandFlyerAgpIn1;
    }

    public int getCmHandFlyerAgpIn2() {
        return cmHandFlyerAgpIn2;
    }

    public int getCmHandFlyerAgpIn3() {
        return cmHandFlyerAgpIn3;
    }

    public int getCmHandFlyerAgpIn4() {
        return cmHandFlyerAgpIn4;
    }

    public int getCmHandFlyerAgpIn5() {
        return cmHandFlyerAgpIn5;
    }

    public int getCmHandFlyerAgpIn6() {
        return cmHandFlyerAgpIn6;
    }

    public int getCmCpAgpIn1() {
        return cmCpAgpIn1;
    }

    public int getCmCpAgpIn2() {
        return cmCpAgpIn2;
    }

    public int getCmCpAgpIn3() {
        return cmCpAgpIn3;
    }

    public int getCmCpAgpIn4() {
        return cmCpAgpIn4;
    }

    public int getCmCpAgpIn5() {
        return cmCpAgpIn5;
    }

    public int getCmCpAgpIn6() {
        return cmCpAgpIn6;
    }

    public int getCmOutAgpOut1() {
        return cmOutAgpOut1;
    }

    public int getCmOutAgpOut2() {
        return cmOutAgpOut2;
    }

    public int getCmOutAgpOut3() {
        return cmOutAgpOut3;
    }

    public int getCmOutAgpOut4() {
        return cmOutAgpOut4;
    }

    public int getCmOutAgpOut5() {
        return cmOutAgpOut5;
    }

    public int getCmOutAgpOut6() {
        return cmOutAgpOut6;
    }

    public int getCmInAgpOut1() {
        return cmInAgpOut1;
    }

    public int getCmInAgpOut2() {
        return cmInAgpOut2;
    }

    public int getCmInAgpOut3() {
        return cmInAgpOut3;
    }

    public int getCmInAgpOut4() {
        return cmInAgpOut4;
    }

    public int getCmInAgpOut5() {
        return cmInAgpOut5;
    }

    public int getCmInAgpOut6() {
        return cmInAgpOut6;
    }

    public int getCmBlogAgpOut1() {
        return cmBlogAgpOut1;
    }

    public int getCmBlogAgpOut2() {
        return cmBlogAgpOut2;
    }

    public int getCmBlogAgpOut3() {
        return cmBlogAgpOut3;
    }

    public int getCmBlogAgpOut4() {
        return cmBlogAgpOut4;
    }

    public int getCmBlogAgpOut5() {
        return cmBlogAgpOut5;
    }

    public int getCmBlogAgpOut6() {
        return cmBlogAgpOut6;
    }

    public int getCmBagAgpOut1() {
        return cmBagAgpOut1;
    }

    public int getCmBagAgpOut2() {
        return cmBagAgpOut2;
    }

    public int getCmBagAgpOut3() {
        return cmBagAgpOut3;
    }

    public int getCmBagAgpOut4() {
        return cmBagAgpOut4;
    }

    public int getCmBagAgpOut5() {
        return cmBagAgpOut5;
    }

    public int getCmBagAgpOut6() {
        return cmBagAgpOut6;
    }

    public int getCmApoTotal1() {
        return cmApoTotal1;
    }

    public int getCmApoTotal2() {
        return cmApoTotal2;
    }

    public int getCmApoTotal3() {
        return cmApoTotal3;
    }

    public int getCmApoTotal4() {
        return cmApoTotal4;
    }

    public int getCmApoTotal5() {
        return cmApoTotal5;
    }

    public int getCmApoTotal6() {
        return cmApoTotal6;
    }

    public float getCmInApptRatio1() {
        return cmInApptRatio1;
    }

    public float getCmInApptRatio2() {
        return cmInApptRatio2;
    }

    public float getCmInApptRatio3() {
        return cmInApptRatio3;
    }

    public float getCmInApptRatio4() {
        return cmInApptRatio4;
    }

    public float getCmInApptRatio5() {
        return cmInApptRatio5;
    }

    public float getCmInApptRatio6() {
        return cmInApptRatio6;
    }

    public float getCmOutApptRatio1() {
        return cmOutApptRatio1;
    }

    public float getCmOutApptRatio2() {
        return cmOutApptRatio2;
    }

    public float getCmOutApptRatio3() {
        return cmOutApptRatio3;
    }

    public float getCmOutApptRatio4() {
        return cmOutApptRatio4;
    }

    public float getCmOutApptRatio5() {
        return cmOutApptRatio5;
    }

    public float getCmOutApptRatio6() {
        return cmOutApptRatio6;
    }

    public int getCmPostPerApo6() {
        return cmPostPerApo6;
    }

    public int getCmHandPerApo6() {
        return cmHandPerApo6;
    }

    public float getCmHandHoursPerApo6() {
        return cmHandHoursPerApo6;
    }

    public float getCmOutGpPerApo6() {
        return cmOutGpPerApo6;
    }

    public float getCmOutGpHoursPerApo6() {
        return cmOutGpHoursPerApo6;
    }

    public float getCmBrAgpRatio1() {
        return cmBrAgpRatio1;
    }

    public float getCmBrAgpRatio2() {
        return cmBrAgpRatio2;
    }

    public float getCmBrAgpRatio3() {
        return cmBrAgpRatio3;
    }

    public float getCmBrAgpRatio4() {
        return cmBrAgpRatio4;
    }

    public float getCmBrAgpRatio5() {
        return cmBrAgpRatio5;
    }

    public float getCmBrAgpRatio6() {
        return cmBrAgpRatio6;
    }

    public int getCmFaSum1() {
        return cmFaSum1;
    }

    public int getCmFaSum2() {
        return cmFaSum2;
    }

    public int getCmFaSum3() {
        return cmFaSum3;
    }

    public int getCmFaSum4() {
        return cmFaSum4;
    }

    public int getCmFaSum5() {
        return cmFaSum5;
    }

    public int getCmFaSum6() {
        return cmFaSum6;
    }

    public float getCmShowRatio1() {
        return cmShowRatio1;
    }

    public float getCmShowRatio2() {
        return cmShowRatio2;
    }

    public float getCmShowRatio3() {
        return cmShowRatio3;
    }

    public float getCmShowRatio4() {
        return cmShowRatio4;
    }

    public float getCmShowRatio5() {
        return cmShowRatio5;
    }

    public float getCmShowRatio6() {
        return cmShowRatio6;
    }

    public String getCmTraining1() {
        return cmTraining1;
    }

    public String getCmTraining2() {
        return cmTraining2;
    }

    public String getCmTraining3() {
        return cmTraining3;
    }

    public String getCmTraining4() {
        return cmTraining4;
    }

    public String getCmTraining5() {
        return cmTraining5;
    }

    public String getCmGot3_1() {
        return cmGot3_1;
    }

    public String getCmGot3_2() {
        return cmGot3_2;
    }

    public String getCmGot3_3() {
        return cmGot3_3;
    }

    public String getCmGot3_4() {
        return cmGot3_4;
    }

    public String getCmGot3_5() {
        return cmGot3_5;
    }

    public String getCmInvitation1() {
        return cmInvitation1;
    }

    public String getCmInvitation2() {
        return cmInvitation2;
    }

    public String getCmInvitation3() {
        return cmInvitation3;
    }

    public String getCmInvitation4() {
        return cmInvitation4;
    }

    public String getCmInvitation5() {
        return cmInvitation5;
    }

    public int getSalesAch1() {
        return salesAch1;
    }

    public int getSalesAch2() {
        return salesAch2;
    }

    public int getSalesAch3() {
        return salesAch3;
    }

    public int getSalesAch4() {
        return salesAch4;
    }

    public int getSalesAch5() {
        return salesAch5;
    }

    public int getSalesAch6() {
        return salesAch6;
    }

    public int getSalesMonthly1() {
        return salesMonthly1;
    }

    public int getSalesMonthly2() {
        return salesMonthly2;
    }

    public int getSalesMonthly3() {
        return salesMonthly3;
    }

    public int getSalesMonthly4() {
        return salesMonthly4;
    }

    public int getSalesMonthly5() {
        return salesMonthly5;
    }

    public int getSalesMonthly6() {
        return salesMonthly6;
    }

    public int getSalesAllPrepay1() {
        return salesAllPrepay1;
    }

    public int getSalesAllPrepay2() {
        return salesAllPrepay2;
    }

    public int getSalesAllPrepay3() {
        return salesAllPrepay3;
    }

    public int getSalesAllPrepay4() {
        return salesAllPrepay4;
    }

    public int getSalesAllPrepay5() {
        return salesAllPrepay5;
    }

    public int getSalesAllPrepay6() {
        return salesAllPrepay6;
    }

    public int getSalesTotal1() {
        return salesTotal1;
    }

    public int getSalesTotal2() {
        return salesTotal2;
    }

    public int getSalesTotal3() {
        return salesTotal3;
    }

    public int getSalesTotal4() {
        return salesTotal4;
    }

    public int getSalesTotal5() {
        return salesTotal5;
    }

    public int getSalesTotal6() {
        return salesTotal6;
    }

    public float getSalesRatio1() {
        return salesRatio1;
    }

    public float getSalesRatio2() {
        return salesRatio2;
    }

    public float getSalesRatio3() {
        return salesRatio3;
    }

    public float getSalesRatio4() {
        return salesRatio4;
    }

    public float getSalesRatio5() {
        return salesRatio5;
    }

    public float getSalesRatio6() {
        return salesRatio6;
    }

    public float getSalesAchAppRatio1() {
        return salesAchAppRatio1;
    }

    public float getSalesAchAppRatio2() {
        return salesAchAppRatio2;
    }

    public float getSalesAchAppRatio3() {
        return salesAchAppRatio3;
    }

    public float getSalesAchAppRatio4() {
        return salesAchAppRatio4;
    }

    public float getSalesAchAppRatio5() {
        return salesAchAppRatio5;
    }

    public float getSalesAchAppRatio6() {
        return salesAchAppRatio6;
    }

    public String getSalesFaReview1() {
        return salesFaReview1;
    }

    public String getSalesFaReview2() {
        return salesFaReview2;
    }

    public String getSalesFaReview3() {
        return salesFaReview3;
    }

    public String getSalesFaReview4() {
        return salesFaReview4;
    }

    public String getSalesFaReview5() {
        return salesFaReview5;
    }

    public String getSalesPriceReview1() {
        return salesPriceReview1;
    }

    public String getSalesPriceReview2() {
        return salesPriceReview2;
    }

    public String getSalesPriceReview3() {
        return salesPriceReview3;
    }

    public String getSalesPriceReview4() {
        return salesPriceReview4;
    }

    public String getSalesPriceReview5() {
        return salesPriceReview5;
    }

    public String getSalesAck1() {
        return salesAck1;
    }

    public String getSalesAck2() {
        return salesAck2;
    }

    public String getSalesAck3() {
        return salesAck3;
    }

    public String getSalesAck4() {
        return salesAck4;
    }

    public String getSalesAck5() {
        return salesAck5;
    }

    public String getSalesTarget1() {
        return salesTarget1;
    }

    public String getSalesTarget2() {
        return salesTarget2;
    }

    public String getSalesTarget3() {
        return salesTarget3;
    }

    public String getSalesTarget4() {
        return salesTarget4;
    }

    public String getSalesTarget5() {
        return salesTarget5;
    }

    public String getSalesMotivation1() {
        return salesMotivation1;
    }

    public String getSalesMotivation2() {
        return salesMotivation2;
    }

    public String getSalesMotivation3() {
        return salesMotivation3;
    }

    public String getSalesMotivation4() {
        return salesMotivation4;
    }

    public String getSalesMotivation5() {
        return salesMotivation5;
    }

    public String getSalesObstacle1() {
        return salesObstacle1;
    }

    public String getSalesObstacle2() {
        return salesObstacle2;
    }

    public String getSalesObstacle3() {
        return salesObstacle3;
    }

    public String getSalesObstacle4() {
        return salesObstacle4;
    }

    public String getSalesObstacle5() {
        return salesObstacle5;
    }

    public String getMgmtMeeting1() {
        return mgmtMeeting1;
    }

    public String getMgmtMeeting2() {
        return mgmtMeeting2;
    }

    public String getMgmtMeeting3() {
        return mgmtMeeting3;
    }

    public String getMgmtMeeting4() {
        return mgmtMeeting4;
    }

    public String getMgmtMeeting5() {
        return mgmtMeeting5;
    }

    public String getMgmtCa1() {
        return mgmtCa1;
    }

    public String getMgmtCa2() {
        return mgmtCa2;
    }

    public String getMgmtCa3() {
        return mgmtCa3;
    }

    public String getMgmtCa4() {
        return mgmtCa4;
    }

    public String getMgmtCa5() {
        return mgmtCa5;
    }

    public String getMgmtGp1() {
        return mgmtGp1;
    }

    public String getMgmtGp2() {
        return mgmtGp2;
    }

    public String getMgmtGp3() {
        return mgmtGp3;
    }

    public String getMgmtGp4() {
        return mgmtGp4;
    }

    public String getMgmtGp5() {
        return mgmtGp5;
    }

    public String getMgmtLearn1() {
        return mgmtLearn1;
    }

    public String getMgmtLearn2() {
        return mgmtLearn2;
    }

    public String getMgmtLearn3() {
        return mgmtLearn3;
    }

    public String getMgmtLearn4() {
        return mgmtLearn4;
    }

    public String getMgmtLearn5() {
        return mgmtLearn5;
    }

    public String getMgmtSheet1() {
        return mgmtSheet1;
    }

    public String getMgmtSheet2() {
        return mgmtSheet2;
    }

    public String getMgmtSheet3() {
        return mgmtSheet3;
    }

    public String getMgmtSheet4() {
        return mgmtSheet4;
    }

    public String getMgmtSheet5() {
        return mgmtSheet5;
    }

    public String getMgmtPolicy1() {
        return mgmtPolicy1;
    }

    public String getMgmtPolicy2() {
        return mgmtPolicy2;
    }

    public String getMgmtPolicy3() {
        return mgmtPolicy3;
    }

    public String getMgmtPolicy4() {
        return mgmtPolicy4;
    }

    public String getMgmtPolicy5() {
        return mgmtPolicy5;
    }

    public String getMgmtCompiantSales1() {
        return mgmtCompiantSales1;
    }

    public String getMgmtCompiantSales2() {
        return mgmtCompiantSales2;
    }

    public String getMgmtCompiantSales3() {
        return mgmtCompiantSales3;
    }

    public String getMgmtCompiantSales4() {
        return mgmtCompiantSales4;
    }

    public String getMgmtCompiantSales5() {
        return mgmtCompiantSales5;
    }

    public String getMgmtCompiantMethod1() {
        return mgmtCompiantMethod1;
    }

    public String getMgmtCompiantMethod2() {
        return mgmtCompiantMethod2;
    }

    public String getMgmtCompiantMethod3() {
        return mgmtCompiantMethod3;
    }

    public String getMgmtCompiantMethod4() {
        return mgmtCompiantMethod4;
    }

    public String getMgmtCompiantMethod5() {
        return mgmtCompiantMethod5;
    }

    public String getMgmtCompiantProduct1() {
        return mgmtCompiantProduct1;
    }

    public String getMgmtCompiantProduct2() {
        return mgmtCompiantProduct2;
    }

    public String getMgmtCompiantProduct3() {
        return mgmtCompiantProduct3;
    }

    public String getMgmtCompiantProduct4() {
        return mgmtCompiantProduct4;
    }

    public String getMgmtCompiantProduct5() {
        return mgmtCompiantProduct5;
    }

    public String getMgmtCompiantAd1() {
        return mgmtCompiantAd1;
    }

    public String getMgmtCompiantAd2() {
        return mgmtCompiantAd2;
    }

    public String getMgmtCompiantAd3() {
        return mgmtCompiantAd3;
    }

    public String getMgmtCompiantAd4() {
        return mgmtCompiantAd4;
    }

    public String getMgmtCompiantAd5() {
        return mgmtCompiantAd5;
    }

    public String getMgmtTraining1() {
        return mgmtTraining1;
    }

    public String getMgmtTraining2() {
        return mgmtTraining2;
    }

    public String getMgmtTraining3() {
        return mgmtTraining3;
    }

    public String getMgmtTraining4() {
        return mgmtTraining4;
    }

    public String getMgmtTraining5() {
        return mgmtTraining5;
    }

    public String getMgmtReport1() {
        return mgmtReport1;
    }

    public String getMgmtReport2() {
        return mgmtReport2;
    }

    public String getMgmtReport3() {
        return mgmtReport3;
    }

    public String getMgmtReport4() {
        return mgmtReport4;
    }

    public String getMgmtReport5() {
        return mgmtReport5;
    }

    public String getMgmtPlan1() {
        return mgmtPlan1;
    }

    public String getMgmtPlan2() {
        return mgmtPlan2;
    }

    public String getMgmtPlan3() {
        return mgmtPlan3;
    }

    public String getMgmtPlan4() {
        return mgmtPlan4;
    }

    public String getMgmtPlan5() {
        return mgmtPlan5;
    }

    public String getMgmtMaintain1() {
        return mgmtMaintain1;
    }

    public String getMgmtMaintain2() {
        return mgmtMaintain2;
    }

    public String getMgmtMaintain3() {
        return mgmtMaintain3;
    }

    public String getMgmtMaintain4() {
        return mgmtMaintain4;
    }

    public String getMgmtMaintain5() {
        return mgmtMaintain5;
    }

    public String getMgmtFace2Face1() {
        return mgmtFace2Face1;
    }

    public String getMgmtFace2Face2() {
        return mgmtFace2Face2;
    }

    public String getMgmtFace2Face3() {
        return mgmtFace2Face3;
    }

    public String getMgmtFace2Face4() {
        return mgmtFace2Face4;
    }

    public String getMgmtFace2Face5() {
        return mgmtFace2Face5;
    }

    public float getClubSalesRatio() {
        return clubSalesRatio;
    }

    public float getClubAchAppRatio() {
        return clubAchAppRatio;
    }

    public int getClubAch1() {
        return clubAch1;
    }

    public int getClubAch2() {
        return clubAch2;
    }

    public int getClubAch3() {
        return clubAch3;
    }

    public int getClubAch4() {
        return clubAch4;
    }

    public int getClubAch5() {
        return clubAch5;
    }

    public int getClubAch6() {
        return clubAch6;
    }

    public int getClubMm1() {
        return clubMm1;
    }

    public int getClubMm2() {
        return clubMm2;
    }

    public int getClubMm3() {
        return clubMm3;
    }

    public int getClubMm4() {
        return clubMm4;
    }

    public int getClubMm5() {
        return clubMm5;
    }

    public int getClubMm6() {
        return clubMm6;
    }

    public int getClubApp1() {
        return clubApp1;
    }

    public int getClubApp2() {
        return clubApp2;
    }

    public int getClubApp3() {
        return clubApp3;
    }

    public int getClubApp4() {
        return clubApp4;
    }

    public int getClubApp5() {
        return clubApp5;
    }

    public int getClubApp6() {
        return clubApp6;
    }

    public int getClubNs1() {
        return clubNs1;
    }

    public int getClubNs2() {
        return clubNs2;
    }

    public int getClubNs3() {
        return clubNs3;
    }

    public int getClubNs4() {
        return clubNs4;
    }

    public int getClubNs5() {
        return clubNs5;
    }

    public int getClubNs6() {
        return clubNs6;
    }

    public int getClubLx1() {
        return clubLx1;
    }

    public int getClubLx2() {
        return clubLx2;
    }

    public int getClubLx3() {
        return clubLx3;
    }

    public int getClubLx4() {
        return clubLx4;
    }

    public int getClubLx5() {
        return clubLx5;
    }

    public int getClubLx6() {
        return clubLx6;
    }

    public String getStaff1Name() {
        return staff1Name;
    }

    public float getStaff1SalesRatio() {
        return staff1SalesRatio;
    }

    public float getStaff1AchAppRatio() {
        return staff1AchAppRatio;
    }

    public int getStaff1Ach1() {
        return staff1Ach1;
    }

    public int getStaff1Ach2() {
        return staff1Ach2;
    }

    public int getStaff1Ach3() {
        return staff1Ach3;
    }

    public int getStaff1Ach4() {
        return staff1Ach4;
    }

    public int getStaff1Ach5() {
        return staff1Ach5;
    }

    public int getStaff1Ach6() {
        return staff1Ach6;
    }

    public int getStaff1Mm1() {
        return staff1Mm1;
    }

    public int getStaff1Mm2() {
        return staff1Mm2;
    }

    public int getStaff1Mm3() {
        return staff1Mm3;
    }

    public int getStaff1Mm4() {
        return staff1Mm4;
    }

    public int getStaff1Mm5() {
        return staff1Mm5;
    }

    public int getStaff1Mm6() {
        return staff1Mm6;
    }

    public int getStaff1App1() {
        return staff1App1;
    }

    public int getStaff1App2() {
        return staff1App2;
    }

    public int getStaff1App3() {
        return staff1App3;
    }

    public int getStaff1App4() {
        return staff1App4;
    }

    public int getStaff1App5() {
        return staff1App5;
    }

    public int getStaff1App6() {
        return staff1App6;
    }

    public int getStaff1Ns1() {
        return staff1Ns1;
    }

    public int getStaff1Ns2() {
        return staff1Ns2;
    }

    public int getStaff1Ns3() {
        return staff1Ns3;
    }

    public int getStaff1Ns4() {
        return staff1Ns4;
    }

    public int getStaff1Ns5() {
        return staff1Ns5;
    }

    public int getStaff1Ns6() {
        return staff1Ns6;
    }

    public int getStaff1Lx1() {
        return staff1Lx1;
    }

    public int getStaff1Lx2() {
        return staff1Lx2;
    }

    public int getStaff1Lx3() {
        return staff1Lx3;
    }

    public int getStaff1Lx4() {
        return staff1Lx4;
    }

    public int getStaff1Lx5() {
        return staff1Lx5;
    }

    public int getStaff1Lx6() {
        return staff1Lx6;
    }

    public String getStaff2Name() {
        return staff2Name;
    }

    public float getStaff2SalesRatio() {
        return staff2SalesRatio;
    }

    public float getStaff2AchAppRatio() {
        return staff2AchAppRatio;
    }

    public int getStaff2Ach1() {
        return staff2Ach1;
    }

    public int getStaff2Ach2() {
        return staff2Ach2;
    }

    public int getStaff2Ach3() {
        return staff2Ach3;
    }

    public int getStaff2Ach4() {
        return staff2Ach4;
    }

    public int getStaff2Ach5() {
        return staff2Ach5;
    }

    public int getStaff2Ach6() {
        return staff2Ach6;
    }

    public int getStaff2Mm1() {
        return staff2Mm1;
    }

    public int getStaff2Mm2() {
        return staff2Mm2;
    }

    public int getStaff2Mm3() {
        return staff2Mm3;
    }

    public int getStaff2Mm4() {
        return staff2Mm4;
    }

    public int getStaff2Mm5() {
        return staff2Mm5;
    }

    public int getStaff2Mm6() {
        return staff2Mm6;
    }

    public int getStaff2App1() {
        return staff2App1;
    }

    public int getStaff2App2() {
        return staff2App2;
    }

    public int getStaff2App3() {
        return staff2App3;
    }

    public int getStaff2App4() {
        return staff2App4;
    }

    public int getStaff2App5() {
        return staff2App5;
    }

    public int getStaff2App6() {
        return staff2App6;
    }

    public int getStaff2Ns1() {
        return staff2Ns1;
    }

    public int getStaff2Ns2() {
        return staff2Ns2;
    }

    public int getStaff2Ns3() {
        return staff2Ns3;
    }

    public int getStaff2Ns4() {
        return staff2Ns4;
    }

    public int getStaff2Ns5() {
        return staff2Ns5;
    }

    public int getStaff2Ns6() {
        return staff2Ns6;
    }

    public int getStaff2Lx1() {
        return staff2Lx1;
    }

    public int getStaff2Lx2() {
        return staff2Lx2;
    }

    public int getStaff2Lx3() {
        return staff2Lx3;
    }

    public int getStaff2Lx4() {
        return staff2Lx4;
    }

    public int getStaff2Lx5() {
        return staff2Lx5;
    }

    public int getStaff2Lx6() {
        return staff2Lx6;
    }

    public String getStaff3Name() {
        return staff3Name;
    }

    public float getStaff3SalesRatio() {
        return staff3SalesRatio;
    }

    public float getStaff3AchAppRatio() {
        return staff3AchAppRatio;
    }

    public int getStaff3Ach1() {
        return staff3Ach1;
    }

    public int getStaff3Ach2() {
        return staff3Ach2;
    }

    public int getStaff3Ach3() {
        return staff3Ach3;
    }

    public int getStaff3Ach4() {
        return staff3Ach4;
    }

    public int getStaff3Ach5() {
        return staff3Ach5;
    }

    public int getStaff3Ach6() {
        return staff3Ach6;
    }

    public int getStaff3Mm1() {
        return staff3Mm1;
    }

    public int getStaff3Mm2() {
        return staff3Mm2;
    }

    public int getStaff3Mm3() {
        return staff3Mm3;
    }

    public int getStaff3Mm4() {
        return staff3Mm4;
    }

    public int getStaff3Mm5() {
        return staff3Mm5;
    }

    public int getStaff3Mm6() {
        return staff3Mm6;
    }

    public int getStaff3App1() {
        return staff3App1;
    }

    public int getStaff3App2() {
        return staff3App2;
    }

    public int getStaff3App3() {
        return staff3App3;
    }

    public int getStaff3App4() {
        return staff3App4;
    }

    public int getStaff3App5() {
        return staff3App5;
    }

    public int getStaff3App6() {
        return staff3App6;
    }

    public int getStaff3Ns1() {
        return staff3Ns1;
    }

    public int getStaff3Ns2() {
        return staff3Ns2;
    }

    public int getStaff3Ns3() {
        return staff3Ns3;
    }

    public int getStaff3Ns4() {
        return staff3Ns4;
    }

    public int getStaff3Ns5() {
        return staff3Ns5;
    }

    public int getStaff3Ns6() {
        return staff3Ns6;
    }

    public int getStaff3Lx1() {
        return staff3Lx1;
    }

    public int getStaff3Lx2() {
        return staff3Lx2;
    }

    public int getStaff3Lx3() {
        return staff3Lx3;
    }

    public int getStaff3Lx4() {
        return staff3Lx4;
    }

    public int getStaff3Lx5() {
        return staff3Lx5;
    }

    public int getStaff3Lx6() {
        return staff3Lx6;
    }

    public String getStaff4Name() {
        return staff4Name;
    }

    public float getStaff4SalesRatio() {
        return staff4SalesRatio;
    }

    public float getStaff4AchAppRatio() {
        return staff4AchAppRatio;
    }

    public int getStaff4Ach1() {
        return staff4Ach1;
    }

    public int getStaff4Ach2() {
        return staff4Ach2;
    }

    public int getStaff4Ach3() {
        return staff4Ach3;
    }

    public int getStaff4Ach4() {
        return staff4Ach4;
    }

    public int getStaff4Ach5() {
        return staff4Ach5;
    }

    public int getStaff4Ach6() {
        return staff4Ach6;
    }

    public int getStaff4Mm1() {
        return staff4Mm1;
    }

    public int getStaff4Mm2() {
        return staff4Mm2;
    }

    public int getStaff4Mm3() {
        return staff4Mm3;
    }

    public int getStaff4Mm4() {
        return staff4Mm4;
    }

    public int getStaff4Mm5() {
        return staff4Mm5;
    }

    public int getStaff4Mm6() {
        return staff4Mm6;
    }

    public int getStaff4App1() {
        return staff4App1;
    }

    public int getStaff4App2() {
        return staff4App2;
    }

    public int getStaff4App3() {
        return staff4App3;
    }

    public int getStaff4App4() {
        return staff4App4;
    }

    public int getStaff4App5() {
        return staff4App5;
    }

    public int getStaff4App6() {
        return staff4App6;
    }

    public int getStaff4Ns1() {
        return staff4Ns1;
    }

    public int getStaff4Ns2() {
        return staff4Ns2;
    }

    public int getStaff4Ns3() {
        return staff4Ns3;
    }

    public int getStaff4Ns4() {
        return staff4Ns4;
    }

    public int getStaff4Ns5() {
        return staff4Ns5;
    }

    public int getStaff4Ns6() {
        return staff4Ns6;
    }

    public int getStaff4Lx1() {
        return staff4Lx1;
    }

    public int getStaff4Lx2() {
        return staff4Lx2;
    }

    public int getStaff4Lx3() {
        return staff4Lx3;
    }

    public int getStaff4Lx4() {
        return staff4Lx4;
    }

    public int getStaff4Lx5() {
        return staff4Lx5;
    }

    public int getStaff4Lx6() {
        return staff4Lx6;
    }

    public String getStaff5Name() {
        return staff5Name;
    }

    public float getStaff5SalesRatio() {
        return staff5SalesRatio;
    }

    public float getStaff5AchAppRatio() {
        return staff5AchAppRatio;
    }

    public int getStaff5Ach1() {
        return staff5Ach1;
    }

    public int getStaff5Ach2() {
        return staff5Ach2;
    }

    public int getStaff5Ach3() {
        return staff5Ach3;
    }

    public int getStaff5Ach4() {
        return staff5Ach4;
    }

    public int getStaff5Ach5() {
        return staff5Ach5;
    }

    public int getStaff5Ach6() {
        return staff5Ach6;
    }

    public int getStaff5Mm1() {
        return staff5Mm1;
    }

    public int getStaff5Mm2() {
        return staff5Mm2;
    }

    public int getStaff5Mm3() {
        return staff5Mm3;
    }

    public int getStaff5Mm4() {
        return staff5Mm4;
    }

    public int getStaff5Mm5() {
        return staff5Mm5;
    }

    public int getStaff5Mm6() {
        return staff5Mm6;
    }

    public int getStaff5App1() {
        return staff5App1;
    }

    public int getStaff5App2() {
        return staff5App2;
    }

    public int getStaff5App3() {
        return staff5App3;
    }

    public int getStaff5App4() {
        return staff5App4;
    }

    public int getStaff5App5() {
        return staff5App5;
    }

    public int getStaff5App6() {
        return staff5App6;
    }

    public int getStaff5Ns1() {
        return staff5Ns1;
    }

    public int getStaff5Ns2() {
        return staff5Ns2;
    }

    public int getStaff5Ns3() {
        return staff5Ns3;
    }

    public int getStaff5Ns4() {
        return staff5Ns4;
    }

    public int getStaff5Ns5() {
        return staff5Ns5;
    }

    public int getStaff5Ns6() {
        return staff5Ns6;
    }

    public int getStaff5Lx1() {
        return staff5Lx1;
    }

    public int getStaff5Lx2() {
        return staff5Lx2;
    }

    public int getStaff5Lx3() {
        return staff5Lx3;
    }

    public int getStaff5Lx4() {
        return staff5Lx4;
    }

    public int getStaff5Lx5() {
        return staff5Lx5;
    }

    public int getStaff5Lx6() {
        return staff5Lx6;
    }

    public String getStaff6Name() {
        return staff6Name;
    }

    public float getStaff6SalesRatio() {
        return staff6SalesRatio;
    }

    public float getStaff6AchAppRatio() {
        return staff6AchAppRatio;
    }

    public int getStaff6Ach1() {
        return staff6Ach1;
    }

    public int getStaff6Ach2() {
        return staff6Ach2;
    }

    public int getStaff6Ach3() {
        return staff6Ach3;
    }

    public int getStaff6Ach4() {
        return staff6Ach4;
    }

    public int getStaff6Ach5() {
        return staff6Ach5;
    }

    public int getStaff6Ach6() {
        return staff6Ach6;
    }

    public int getStaff6Mm1() {
        return staff6Mm1;
    }

    public int getStaff6Mm2() {
        return staff6Mm2;
    }

    public int getStaff6Mm3() {
        return staff6Mm3;
    }

    public int getStaff6Mm4() {
        return staff6Mm4;
    }

    public int getStaff6Mm5() {
        return staff6Mm5;
    }

    public int getStaff6Mm6() {
        return staff6Mm6;
    }

    public int getStaff6App1() {
        return staff6App1;
    }

    public int getStaff6App2() {
        return staff6App2;
    }

    public int getStaff6App3() {
        return staff6App3;
    }

    public int getStaff6App4() {
        return staff6App4;
    }

    public int getStaff6App5() {
        return staff6App5;
    }

    public int getStaff6App6() {
        return staff6App6;
    }

    public int getStaff6Ns1() {
        return staff6Ns1;
    }

    public int getStaff6Ns2() {
        return staff6Ns2;
    }

    public int getStaff6Ns3() {
        return staff6Ns3;
    }

    public int getStaff6Ns4() {
        return staff6Ns4;
    }

    public int getStaff6Ns5() {
        return staff6Ns5;
    }

    public int getStaff6Ns6() {
        return staff6Ns6;
    }

    public int getStaff6Lx1() {
        return staff6Lx1;
    }

    public int getStaff6Lx2() {
        return staff6Lx2;
    }

    public int getStaff6Lx3() {
        return staff6Lx3;
    }

    public int getStaff6Lx4() {
        return staff6Lx4;
    }

    public int getStaff6Lx5() {
        return staff6Lx5;
    }

    public int getStaff6Lx6() {
        return staff6Lx6;
    }

    public String getThisPlan() {
        return thisPlan;
    }

    public String getNextPlan() {
        return nextPlan;
    }
}
