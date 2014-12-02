package com.curves.franchise.web;

import com.curves.franchise.domain.Ca;
import com.curves.franchise.repository.CaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class CaAllHelper {
    private Logger logger = LoggerFactory.getLogger(CaAllHelper.class);

    int sumGoalsTm = 0, sumLastGoalsTm = 0, sumLastGoalsActive = 0, sumLastGoalsShowRatio = 0, sumLastGoalsSalesRatio = 0,
            sumGoalsExitsRatio = 0, sumGoalsNewSales = 0, sumGoalsAppoints = 0, sumSvcTm6 = 0, sumSvcHold6 = 0,
            sumSvcActive6 = 0, sumSvcHoldRatio6 = 0, sumSvcTotalWo6 = 0, sumSvcAvgWo6 = 0, sumSvcMaxWo6 = 0,
            sumSvcExits6 = 0, sumSvcExitsRatio6 = 0, sumSvcMeasure6 = 0, sumSvcMeasureRatio6 = 0, sumSvc12_6 = 0,
            sumSvc8to11_6 = 0, sumSvc4to7_6 = 0, sumSvc1to3_6 = 0, sumSvc0_6 = 0, sumCmPostFlyer6 = 0,
            sumCmHandFlyerHours6 = 0, sumCmOutGpHours6 = 0, sumCmCpBox6 = 0, sumCmOutGot6 = 0, sumCmInGot6 = 0,
            sumCmBlogGot6 = 0, sumCmBagGot6 = 0, sumCmTotalGot6 = 0, sumCmCallIn6 = 0, sumCmOutGotCall6 = 0,
            sumCmInGotCall6 = 0, sumCmBlogGotCall6 = 0, sumCmBagGotCall6 = 0, sumCmOwnRefs6 = 0, sumCmNewspaper6 = 0,
            sumCmTv6 = 0, sumCmInternet6 = 0, sumCmSign6 = 0, sumCmMate6 = 0, sumCmOthers6 = 0, sumCmMailAgpIn6 = 0,
            sumCmPostFlyerAgpIn6 = 0, sumCmHandFlyerAgpIn6 = 0, sumCmCpAgpIn6 = 0, sumCmOutAgpOut6 = 0, sumCmInAgpOut6 = 0,
            sumCmBlogAgpOut6 = 0, sumCmBagAgpOut6 = 0, sumCmApoTotal6 = 0, sumCmInApptRatio6 = 0, sumCmOutApptRatio6 = 0,
            sumCmPostPerApo6 = 0, sumCmHandPerApo6 = 0, sumCmHandHoursPerApo6 = 0, sumCmOutGpHoursPerApo6 = 0,
            sumCmOutGpPerApo6 = 0, sumCmBrAgpRatio6 = 0, sumCmFaSum6 = 0, sumCmShowRatio6 = 0, sumSalesAch6 = 0,
            sumSalesMonthly6 = 0, sumSalesAllPrepay6 = 0, sumSalesTotal6 = 0, sumSalesRatio6 = 0, sumSalesAchAppRatio6 = 0;

    public Map<String, Map<String, String>> fillItems(CaRepository caRepo, int year, int month) {
        List<Ca> cas = caRepo.findByCaYearAndCaMonth(year, month, new Sort(Sort.Direction.DESC, "clubId"));
        if (cas.size() == 0) {
            return new HashMap<>(0);
        }
        List<Ca> lastCas = caRepo.findByCaYearAndCaMonth(year, month-1, new Sort(Sort.Direction.DESC, "clubId"));
        
        for (Ca lastCa : lastCas) {
            sumLastGoalsTm += lastCa.getSvcTm6();
            sumLastGoalsActive += lastCa.getSvcActive6();
            sumLastGoalsShowRatio += lastCa.getCmShowRatio6();
            sumLastGoalsSalesRatio += lastCa.getSalesRatio6();
        }

        for (Ca ca : cas) {
            sumGoalsTm += ca.getGoalsTm();
            sumGoalsExitsRatio += ca.getGoalsExitsRatio();
            sumGoalsNewSales += ca.getGoalsNewSales();
            sumGoalsAppoints += ca.getGoalsAppoints();
            sumSvcTm6 += ca.getSvcTm6();
            sumSvcHold6 += ca.getSvcHold6();
            sumSvcActive6 += ca.getSvcActive6();
            sumSvcHoldRatio6 += ca.getSvcHoldRatio6();
            sumSvcTotalWo6 += ca.getSvcTotalWo6();
            sumSvcAvgWo6 += ca.getSvcAvgWo6();
            sumSvcMaxWo6 += ca.getSvcMaxWo6();
            sumSvcExits6 += ca.getSvcExits6();
            sumSvcExitsRatio6 += ca.getSvcExitsRatio6();
            sumSvcMeasure6 += ca.getSvcMeasure6();
            sumSvcMeasureRatio6 += ca.getSvcMeasureRatio6();
            sumSvc12_6 += ca.getSvc12_6();
            sumSvc8to11_6 += ca.getSvc8to11_6();
            sumSvc4to7_6 += ca.getSvc4to7_6();
            sumSvc1to3_6 += ca.getSvc1to3_6();
            sumSvc0_6 += ca.getSvc0_6();
            sumCmPostFlyer6 += ca.getCmPostFlyer6();
            sumCmHandFlyerHours6 += ca.getCmHandFlyerHours6();
            sumCmOutGpHours6 += ca.getCmOutGpHours6();
            sumCmCpBox6 += ca.getCmCpBox6();
            sumCmOutGot6 += ca.getCmOutGot6();
            sumCmInGot6 += ca.getCmInGot6();
            sumCmBlogGot6 += ca.getCmBlogGot6();
            sumCmBagGot6 += ca.getCmBagGot6();
            sumCmTotalGot6 += ca.getCmTotalGot6();
            sumCmCallIn6 += ca.getCmCallIn6();
            sumCmOutGotCall6 += ca.getCmOutGotCall6();
            sumCmInGotCall6 += ca.getCmInGotCall6();
            sumCmBlogGotCall6 += ca.getCmBlogGotCall6();
            sumCmBagGotCall6 += ca.getCmBagGotCall6();
            sumCmOwnRefs6 += ca.getCmOwnRefs6();
            sumCmNewspaper6 += ca.getCmNewspaper6();
            sumCmTv6 += ca.getCmTv6();
            sumCmInternet6 += ca.getCmInternet6();
            sumCmSign6 += ca.getCmSign6();
            sumCmMate6 += ca.getCmMate6();
            sumCmOthers6 += ca.getCmOthers6();
            sumCmMailAgpIn6 += ca.getCmMailAgpIn6();
            sumCmPostFlyerAgpIn6 += ca.getCmPostFlyerAgpIn6();
            sumCmHandFlyerAgpIn6 += ca.getCmHandFlyerAgpIn6();
            sumCmCpAgpIn6 += ca.getCmCpAgpIn6();
            sumCmOutAgpOut6 += ca.getCmOutAgpOut6();
            sumCmInAgpOut6 += ca.getCmInAgpOut6();
            sumCmBlogAgpOut6 += ca.getCmBlogAgpOut6();
            sumCmBagAgpOut6 += ca.getCmBagAgpOut6();
            sumCmApoTotal6 += ca.getCmApoTotal6();
            sumCmInApptRatio6 += ca.getCmInApptRatio6();
            sumCmOutApptRatio6 += ca.getCmOutApptRatio6();
            sumCmPostPerApo6 += ca.getCmPostPerApo6();
            sumCmHandPerApo6 += ca.getCmHandPerApo6();
            sumCmHandHoursPerApo6 += ca.getCmHandHoursPerApo6();
            sumCmOutGpHoursPerApo6 += ca.getCmOutGpHoursPerApo6();
            sumCmOutGpPerApo6 += ca.getCmOutGpPerApo6();
            sumCmBrAgpRatio6 += ca.getCmBrAgpRatio6();
            sumCmFaSum6 += ca.getCmFaSum6();
            sumCmShowRatio6 += ca.getCmShowRatio6();
            sumSalesAch6 += ca.getSalesAch6();
            sumSalesMonthly6 += ca.getSalesMonthly6();
            sumSalesAllPrepay6 += ca.getSalesAllPrepay6();
            sumSalesTotal6 += ca.getSalesTotal6();
            sumSalesRatio6 += ca.getSalesRatio6();
            sumSalesAchAppRatio6 += ca.getSalesAchAppRatio6();
        }

        Map<String, Map<String, String>> valueV = new HashMap<>(69);
        fillOne(cas, valueV, "GoalsTm");
        fillOne(cas, valueV, "GoalsExitsRatio");
        fillOne(cas, valueV, "GoalsNewSales");
        fillOne(cas, valueV, "GoalsAppoints");
        fillOne(cas, valueV, "SvcTm6");
        fillOne(cas, valueV, "SvcHold6");
        fillOne(cas, valueV, "SvcActive6");
        fillOne(cas, valueV, "SvcHoldRatio6");
        fillOne(cas, valueV, "SvcTotalWo6");
        fillOne(cas, valueV, "SvcAvgWo6");
        fillOne(cas, valueV, "SvcMaxWo6");
        fillOne(cas, valueV, "SvcExits6");
        fillOne(cas, valueV, "SvcExitsRatio6");
        fillOne(cas, valueV, "SvcMeasure6");
        fillOne(cas, valueV, "SvcMeasureRatio6");
        fillOne(cas, valueV, "Svc12_6");
        fillOne(cas, valueV, "Svc8to11_6");
        fillOne(cas, valueV, "Svc4to7_6");
        fillOne(cas, valueV, "Svc1to3_6");
        fillOne(cas, valueV, "Svc0_6");
        fillOne(cas, valueV, "CmPostFlyer6");
        fillOne(cas, valueV, "CmHandFlyerHours6");
        fillOne(cas, valueV, "CmOutGpHours6");
        fillOne(cas, valueV, "CmCpBox6");
        fillOne(cas, valueV, "CmOutGot6");
        fillOne(cas, valueV, "CmInGot6");
        fillOne(cas, valueV, "CmBlogGot6");
        fillOne(cas, valueV, "CmBagGot6");
        fillOne(cas, valueV, "CmTotalGot6");
        fillOne(cas, valueV, "CmCallIn6");
        fillOne(cas, valueV, "CmOutGotCall6");
        fillOne(cas, valueV, "CmInGotCall6");
        fillOne(cas, valueV, "CmBlogGotCall6");
        fillOne(cas, valueV, "CmBagGotCall6");
        fillOne(cas, valueV, "CmOwnRefs6");
        fillOne(cas, valueV, "CmNewspaper6");
        fillOne(cas, valueV, "CmTv6");
        fillOne(cas, valueV, "CmInternet6");
        fillOne(cas, valueV, "CmSign6");
        fillOne(cas, valueV, "CmMate6");
        fillOne(cas, valueV, "CmOthers6");
        fillOne(cas, valueV, "CmMailAgpIn6");
        fillOne(cas, valueV, "CmPostFlyerAgpIn6");
        fillOne(cas, valueV, "CmHandFlyerAgpIn6");
        fillOne(cas, valueV, "CmCpAgpIn6");
        fillOne(cas, valueV, "CmOutAgpOut6");
        fillOne(cas, valueV, "CmInAgpOut6");
        fillOne(cas, valueV, "CmBlogAgpOut6");
        fillOne(cas, valueV, "CmBagAgpOut6");
        fillOne(cas, valueV, "CmApoTotal6");
        fillOne(cas, valueV, "CmOutApptRatio6");
        fillOne(cas, valueV, "CmPostPerApo6");
        fillOne(cas, valueV, "CmHandPerApo6");
        fillOne(cas, valueV, "CmHandHoursPerApo6");
        fillOne(cas, valueV, "CmOutGpHoursPerApo6");
        fillOne(cas, valueV, "CmOutGpPerApo6");
        fillOne(cas, valueV, "CmBrAgpRatio6");
        fillOne(cas, valueV, "CmFaSum6");
        fillOne(cas, valueV, "CmShowRatio6");
        fillOne(cas, valueV, "SalesAch6");
        fillOne(cas, valueV, "SalesMonthly6");
        fillOne(cas, valueV, "SalesAllPrepay6");
        fillOne(cas, valueV, "SalesTotal6");
        fillOne(cas, valueV, "SalesRatio6");
        fillOne(cas, valueV, "SalesAchAppRatio6");

        Collections.sort(lastCas, new Comparator<Ca>() {
            @Override
            public int compare(Ca ca1, Ca ca2) {
                return ca2.getSvcTm6() - ca1.getSvcTm6();
            }
        });
        Map<String, String> valueX = new HashMap<>(4);
        valueV.put("LastGoalsTm", valueX);
        valueX.put("highest", "" + lastCas.get(0).getSvcTm6());
        valueX.put("lowest", "" + lastCas.get(lastCas.size() - 1).getSvcTm6());
        valueX.put("sum", "" + sumLastGoalsTm);
        valueX.put("avg", "" + sumLastGoalsTm/lastCas.size());

        Collections.sort(lastCas, new Comparator<Ca>() {
            @Override
            public int compare(Ca ca1, Ca ca2) {
                return ca2.getSvcActive6() - ca1.getSvcActive6();
            }
        });
        valueX = new HashMap<>(4);
        valueV.put("LastGoalsActive", valueX);
        valueX.put("highest", "" + lastCas.get(0).getSvcActive6());
        valueX.put("lowest", "" + lastCas.get(lastCas.size() - 1).getSvcActive6());
        valueX.put("sum", "" + sumLastGoalsTm);
        valueX.put("avg", "" + sumLastGoalsTm/lastCas.size());

        Collections.sort(lastCas, new Comparator<Ca>() {
            @Override
            public int compare(Ca ca1, Ca ca2) {
                return (int)Math.ceil(ca2.getCmShowRatio6() - ca1.getCmShowRatio6());
            }
        });
        valueX = new HashMap<>(4);
        valueV.put("LastGoalsShowRatio", valueX);
        valueX.put("highest", "" + lastCas.get(0).getCmShowRatio6());
        valueX.put("lowest", "" + lastCas.get(lastCas.size() - 1).getCmShowRatio6());
        valueX.put("sum", "" + sumGoalsTm);
        valueX.put("avg", "" + sumGoalsTm/lastCas.size());

        Collections.sort(lastCas, new Comparator<Ca>() {
            @Override
            public int compare(Ca ca1, Ca ca2) {
                return (int)Math.ceil(ca2.getSalesRatio6() - ca1.getSalesRatio6());
            }
        });
        valueX = new HashMap<>(4);
        valueV.put("LastGoalsSalesRatio", valueX);
        valueX.put("highest", "" + lastCas.get(0).getSalesRatio6());
        valueX.put("lowest", "" + lastCas.get(lastCas.size() - 1).getSalesRatio6());
        valueX.put("sum", "" + sumGoalsTm);
        valueX.put("avg", "" + sumGoalsTm/lastCas.size());

        return valueV;
    }

    private void fillOne(List<Ca> cas, Map<String, Map<String, String>> valueV, final String item) {
        Map<String, String> valueX = new HashMap<>(4);
        try {
            Field sumField = CaAllHelper.class.getDeclaredField("sum" + item);
            Number sumFieldValue = (Number)sumField.get(this);
            final Method xMethod = Ca.class.getDeclaredMethod("get"+item);

            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    try {
                        return (int)Math.ceil(((Number)xMethod.invoke(ca2)).floatValue() - ((Number)xMethod.invoke(ca2)).floatValue());
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                    return 0;
                }
            });

            valueX.put("highest", "" + xMethod.invoke(cas.get(0)));
            valueX.put("lowest", "" + xMethod.invoke(cas.get(cas.size() - 1)));
            valueX.put("sum", "" + sumFieldValue);
            valueX.put("avg", "" + sumFieldValue.floatValue()/cas.size());

            valueV.put(item, valueX);
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
