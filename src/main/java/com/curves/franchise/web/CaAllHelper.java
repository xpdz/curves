package com.curves.franchise.web;

import com.curves.franchise.domain.Ca;
import com.curves.franchise.domain.Club;
import com.curves.franchise.repository.CaRepository;
import com.curves.franchise.repository.ClubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class CaAllHelper {
    private Logger logger = LoggerFactory.getLogger(CaAllHelper.class);

    float sumGoalsTm = 0, sumLastGoalsTm = 0, sumLastGoalsActive = 0, sumLastGoalsShowRatio = 0, sumLastGoalsSalesRatio = 0,
            sumGoalsExitsRatio = 0, sumGoalsNewSales = 0, sumGoalsAppoints = 0, sumSvcTm6 = 0, sumSvcShiftOut6 = 0, sumSvcShiftIn6 = 0, sumSvcHold6 = 0,
            sumSvcActive6 = 0, sumSvcHoldRatio6 = 0, sumSvcTotalWo6 = 0, sumSvcAvgWo6 = 0, sumSvcMaxWo6 = 0,
            sumSvcExits6 = 0, sumSvcExitsRatio6 = 0, sumSvcMeasure6 = 0, sumSvcMeasureRatio6 = 0, sumSvc12_6 = 0,
            sumSvc8to11_6 = 0, sumSvc4to7_6 = 0, sumSvc1to3_6 = 0, sumSvc0_6 = 0, sumCmPostFlyer6 = 0,
            sumCmHandFlyer6 = 0, sumCmHandFlyerHours6 = 0, sumCmOutGpHours6 = 0, sumCmOutGp6 = 0, sumCmCpBox6 = 0, sumCmOutGot6 = 0, sumCmInGot6 = 0,
            sumCmBlogGot6 = 0, sumCmBagGot6 = 0, sumCmTotalGot6 = 0, sumCmCallIn6 = 0, sumCmOutGotCall6 = 0,
            sumCmInGotCall6 = 0, sumCmBlogGotCall6 = 0, sumCmBagGotCall6 = 0, sumCmOwnRefs6 = 0, sumCmNewspaper6 = 0,
            sumCmTv6 = 0, sumCmInternet6 = 0, sumCmSign6 = 0, sumCmMate6 = 0, sumCmOthers6 = 0, sumCmMailAgpIn6 = 0,
            sumCmPostFlyerAgpIn6 = 0, sumCmHandFlyerAgpIn6 = 0, sumCmCpAgpIn6 = 0, sumCmOutAgpOut6 = 0, sumCmInAgpOut6 = 0,
            sumCmBlogAgpOut6 = 0, sumCmBagAgpOut6 = 0, sumCmApoTotal6 = 0, sumCmInApptRatio6 = 0, sumCmOutApptRatio6 = 0,
            sumCmPostPerApo6 = 0, sumCmHandHoursPerApo6 = 0, sumCmOutGpHoursPerApo6 = 0, sumCmBrAgpRatio6 = 0,
            sumCmFaSum6 = 0, sumCmShowRatio6 = 0, sumSalesAch6 = 0, sumSalesMonthly6 = 0, sumSalesAllPrepay6 = 0,
            sumSalesTotal6 = 0, sumSalesRatio6 = 0, sumSalesAchAppRatio6 = 0;

    public Map<String, Map<String, Number>> fillCaAllStat(CaRepository caRepo, int year, int month) {
        List<Ca> cas = caRepo.findByCaYearAndCaMonth(year, month, new Sort(Sort.Direction.DESC, "clubId"));
        if (cas.size() == 0) {
            return new HashMap<>(0);
        }
        List<Ca> lastCas = caRepo.findByCaYearAndCaMonth(year, month-1, new Sort(Sort.Direction.DESC, "clubId"));
        // these last items fetched from last month data
        for (Ca lastCa : lastCas) {
            sumLastGoalsTm += lastCa.getSvcTm6();
            sumLastGoalsActive += lastCa.getSvcActive6();
            sumLastGoalsShowRatio += lastCa.getCmShowRatio6();
            sumLastGoalsSalesRatio += lastCa.getSalesRatio6();
        }

        // reflection - calculate the sum of each field
        Field[] fields = CaAllHelper.class.getDeclaredFields();
        for (Ca ca : cas) {
            for (Field field : fields) {
                String fieldName = field.getName();
                if (fieldName.startsWith("sum") && !fieldName.startsWith("sumLast")) {
                    String item = fieldName.substring(3);
                    try {
                        float value = ((Number) field.get(this)).floatValue();
                        Method xMethod = Ca.class.getDeclaredMethod("get" + item);
                        value += ((Number)xMethod.invoke(ca)).floatValue();
                        field.setFloat(this, value);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            }
        }

        // reflection - fill each item with sum/avg/high/low values
        Map<String, Map<String, Number>> valueV = new LinkedHashMap<>(69);
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.startsWith("sum")) {
                if (fieldName.startsWith("sumLast")) {
                    if (lastCas.size() > 0) {
                        fillOne(lastCas, valueV, field);
                    }
                } else {
                    fillOne(cas, valueV, field);
                }
            }
        }

        return valueV;
    }

    private void fillOne(List<Ca> cas, Map<String, Map<String, Number>> valueV, Field sumField) {
        String item = sumField.getName().substring(3);
        Map<String, Number> valueX = new HashMap<>(4);
        try {
            String methodName = null;
            // handle last month data as special;
            if (item.equals("LastGoalsTm")) {
                methodName = "getSvcTm6";
            } else if (item.equals("LastGoalsActive")) {
                methodName = "getSvcActive6";
            } else if (item.equals("LastGoalsShowRatio")) {
                methodName = "getCmShowRatio6";
            } else if (item.equals("LastGoalsSalesRatio")) {
                methodName = "getSalesRatio6";
            } else {
                methodName = "get" + item;
            }
            // get realted method
            final Method xMethod = Ca.class.getDeclaredMethod(methodName);

            // sort by DESC
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    try {
                        return compareFloat(((Number) xMethod.invoke(ca2)).floatValue(), ((Number) xMethod.invoke(ca1)).floatValue());
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                    return 0;
                }
            });

            // fill sum/avg/high/low
            Number sumFieldValue = (Number)sumField.get(this);
            if (cas.size() > 0) {
                valueX.put("highest", (Number)xMethod.invoke(cas.get(0)));
                valueX.put("lowest", (Number)xMethod.invoke(cas.get(cas.size() - 1)));
                valueX.put("sum", sumFieldValue);
                valueX.put("avg", sumFieldValue.floatValue()/cas.size());
            }

            valueV.put(item, valueX);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public Map<String, Map<String, Number>> fillCaAllClubs(CaRepository caRepo, ClubRepository clubRepo, int caYear, int caMonth) {
        List<Ca> cas = caRepo.findByCaYearAndCaMonth(caYear, caMonth, new Sort(Sort.Direction.DESC, "clubId"));
        Map<String, Map<String, Number>> valueV = new HashMap<>(100);
        if (cas.size() == 0) {
            return valueV;
        }

        Iterable<Club> clubs = clubRepo.findAll();
        for (Ca ca : cas) {
            boolean found = false;
            Map<String, Number> valueX = new LinkedHashMap<>(69);
            for (Club club : clubs) {
                if (club.getClubId() == ca.getClubId()) {
                    valueV.put(club.getName(), valueX);
                    found = true;
                    break;
                }
            }
            if (!found) {
                logger.error(">>> Club ID "+ca.getClubId()+" in CA NOT FOUND in clubs <<<");
                continue;
            }

            valueX.put("GoalsTm", ca.getGoalsTm());
            Ca lastCa = caRepo.findByClubIdAndCaYearAndCaMonth(ca.getClubId(), ca.getCaYear(), ca.getCaMonth() - 1);
            if (lastCa != null) {
                valueX.put("LastGoalsTm", lastCa.getSvcTm6());
                valueX.put("LastGoalsActive", lastCa.getSvcActive6());
                valueX.put("LastGoalsShowRatio", lastCa.getCmShowRatio6());
                valueX.put("LastGoalsSalesRatio", lastCa.getSalesRatio6());
            } else {
                valueX.put("LastGoalsTm", 0);
                valueX.put("LastGoalsActive", 0);
                valueX.put("LastGoalsShowRatio", 0);
                valueX.put("LastGoalsSalesRatio", 0);
            }
            valueX.put("GoalsExitsRatio", ca.getGoalsExitsRatio());
            valueX.put("GoalsNewSales", ca.getGoalsNewSales());
            valueX.put("GoalsAppoints", ca.getGoalsAppoints());
            valueX.put("SvcTm6", ca.getSvcTm6());
            valueX.put("SvcShiftOut6", ca.getSvcShiftOut6());
            valueX.put("SvcShiftIn6", ca.getSvcShiftIn6());
            valueX.put("SvcHold6", ca.getSvcHold6());
            valueX.put("SvcActive6", ca.getSvcActive6());
            valueX.put("SvcHoldRatio6", ca.getSvcHoldRatio6());
            valueX.put("SvcTotalWo6", ca.getSvcTotalWo6());
            valueX.put("SvcAvgWo6", ca.getSvcAvgWo6());
            valueX.put("SvcMaxWo6", ca.getSvcMaxWo6());
            valueX.put("SvcExits6", ca.getSvcExits6());
            valueX.put("SvcExitsRatio6", ca.getSvcExitsRatio6());
            valueX.put("SvcMeasure6", ca.getSvcMeasure6());
            valueX.put("SvcMeasureRatio6", ca.getSvcMeasureRatio6());
            valueX.put("Svc12_6", ca.getSvc12_6());
            valueX.put("Svc8to11_6", ca.getSvc8to11_6());
            valueX.put("Svc4to7_6", ca.getSvc4to7_6());
            valueX.put("Svc1to3_6", ca.getSvc1to3_6());
            valueX.put("Svc0_6", ca.getSvc0_6());
            valueX.put("CmPostFlyer6", ca.getCmPostFlyer6());
            valueX.put("CmHandFlyer6", ca.getCmHandFlyer6());
            valueX.put("CmHandFlyerHours6", ca.getCmHandFlyerHours6());
            valueX.put("CmOutGpHours6", ca.getCmOutGpHours6());
            valueX.put("CmOutGp6", ca.getCmOutGp6());
            valueX.put("CmCpBox6", ca.getCmCpBox6());
            valueX.put("CmOutGot6", ca.getCmOutGot6());
            valueX.put("CmInGot6", ca.getCmInGot6());
            valueX.put("CmBlogGot6", ca.getCmBlogGot6());
            valueX.put("CmBagGot6", ca.getCmBagGot6());
            valueX.put("CmTotalGot6", ca.getCmTotalGot6());
            valueX.put("CmCallIn6", ca.getCmCallIn6());
            valueX.put("CmOutGotCall6", ca.getCmOutGotCall6());
            valueX.put("CmInGotCall6", ca.getCmInGotCall6());
            valueX.put("CmBlogGotCall6", ca.getCmBlogGotCall6());
            valueX.put("CmBagGotCall6", ca.getCmBagGotCall6());
            valueX.put("CmOwnRefs6", ca.getCmOwnRefs6());
            valueX.put("CmNewspaper6", ca.getCmNewspaper6());
            valueX.put("CmTv6", ca.getCmTv6());
            valueX.put("CmInternet6", ca.getCmInternet6());
            valueX.put("CmSign6", ca.getCmSign6());
            valueX.put("CmMate6", ca.getCmMate6());
            valueX.put("CmOthers6", ca.getCmOthers6());
            valueX.put("CmMailAgpIn6", ca.getCmMailAgpIn6());
            valueX.put("CmPostFlyerAgpIn6", ca.getCmPostFlyerAgpIn6());
            valueX.put("CmHandFlyerAgpIn6", ca.getCmHandFlyerAgpIn6());
            valueX.put("CmCpAgpIn6", ca.getCmCpAgpIn6());
            valueX.put("CmOutAgpOut6", ca.getCmOutAgpOut6());
            valueX.put("CmInAgpOut6", ca.getCmInAgpOut6());
            valueX.put("CmBlogAgpOut6", ca.getCmBlogAgpOut6());
            valueX.put("CmBagAgpOut6", ca.getCmBagAgpOut6());
            valueX.put("CmApoTotal6", ca.getCmApoTotal6());
            valueX.put("CmInApptRatio6", ca.getCmInApptRatio6());
            valueX.put("CmOutApptRatio6", ca.getCmOutApptRatio6());
            valueX.put("CmPostPerApo6", ca.getCmPostPerApo6());
            valueX.put("CmHandHoursPerApo6", ca.getCmHandHoursPerApo6());
            valueX.put("CmOutGpHoursPerApo6", ca.getCmOutGpHoursPerApo6());
            valueX.put("CmBrAgpRatio6", ca.getCmBrAgpRatio6());
            valueX.put("CmFaSum6", ca.getCmFaSum6());
            valueX.put("CmShowRatio6", ca.getCmShowRatio6());
            valueX.put("SalesAch6", ca.getSalesAch6());
            valueX.put("SalesMonthly6", ca.getSalesMonthly6());
            valueX.put("SalesAllPrepay6", ca.getSalesAllPrepay6());
            valueX.put("SalesTotal6", ca.getSalesTotal6());
            valueX.put("SalesRatio6", ca.getSalesRatio6());
            valueX.put("SalesAchAppRatio6", ca.getSalesAchAppRatio6());
        }
        return valueV;
    }

    private int compareFloat(float f1, float f2) {
        float f = f1 - f2;
        if (f > 0) {
            return 1;
        } else if (f < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
