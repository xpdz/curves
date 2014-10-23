package com.curves.franchise.domain;

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
    private Date lastModified;

    private int goalsThisEnrolled;
    private int goalsLastEnrolled;
    private int goalsLastValid;
    private int goalsLastShowRate;
    private int goalsLastSalesRate;
    private int goalsExitsRate;
    private int goalsNewSales;
    private int goalsAppoints;

    // service
    private int[] serviceEnrolled; // array-0 for monthly, array-1 for Monday, ..., array-5 for Friday
    private int[] serviceShift; // added in new fmt
    private int[] serviceLeave;
    private int[] serviceValid;
    private int[] serviceTotalWO;
    private int[] serviceMaxWO;
    private int[] serviceExits;
    private int[] serviceMeasure;
    private int service12;
    private int service8to11;
    private int service4to7;
    private int service1to3;
    private int service0;
    private boolean[] serviceRemindersActive;
    private boolean[] serviceRemindersInactive;
    private boolean[] serviceRemindersFwoReview;
    private boolean[] serviceRemindersInterview;
    private boolean[] serviceRemindersThanks;
    private boolean[] serviceReminders3C;
    private boolean[] serviceRemindersReward;
    private boolean[] serviceRemindersLoyal;
    // customer make
    private int[] cmLeadMailFlyer;
    private float[] cmLeadHandFlyer;
    private float[] cmLeadOut;
    private int[] cmLeadCP;
    private int[] cmLeadOutGot;
    private int[] cmLeadInGot;
    private int[] cmLeadBlogGot;
    private int[] cmLeadBagGot;
    private int[] cmCalls;
    private int[] cmCallsOutGot;
    private int[] cmCallsInGot;
    private int[] cmCallsBlogGot;
    private int[] cmCallsBagGot;
    private int[] cmOwnRefs;
    private int[] cmOtherRefs;
    private int[] cmNewspaper;
    private int[] cmTV;
    private int[] cmInternet;
    private int[] cmSign;
    private int[] cmMate;
    private int[] cmOthers;
    private int[] cmAgpInDirectMail;
    private int[] cmAgpInMailFlyer;
    private int[] cmAgpInHandFlyer;
    private int[] cmAgpInCP;
    private int[] cmAgpOutApoOut;
    private int[] cmAgpOutApoIn;
    private int[] cmAgpOutApoBlog;
    private int[] cmAgpOutAgpBag;
    private int[] cmTog;
    private boolean[] cmTraining;
    private boolean[] cmGot3;
    private boolean[] cmInvitation;
    // sales
    private int[] salesAch;
    private int[] salesMonthly;
    private int[] salesAllPrepay;
    private boolean[] salesReminderFaReview;
    private boolean[] salesReminderPriceReview;
    private boolean[] salesReminderAck;
    private boolean[] salesReminderTarget;
    private boolean[] salesReminderMotivation;
    private boolean[] salesReminderObstacle;
    private boolean[] mgmtMeeting;
    private boolean[] mgmtCA;
    private boolean[] mgmtGP;
    private boolean[] mgmtLearn;
    private boolean[] mgmtSheet;
    private boolean[] mgmtPolicy;
    private boolean[] mgmtCompiantSales;
    private boolean[] mgmtCompiantMethod;
    private boolean[] mgmtCompiantProduct;
    private boolean[] mgmtCompiantAD;
    private boolean[] mgmtTraining;
    private boolean[] mgmtReport;
    private boolean[] mgmtPlan;
    private boolean[] mgmtMaintain;
    private boolean[] mgmtFace2Face;
}
