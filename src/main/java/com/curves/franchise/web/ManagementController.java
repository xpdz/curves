package com.curves.franchise.web;

import com.curves.franchise.domain.Ca;
import com.curves.franchise.domain.PjSum;
import com.curves.franchise.repository.CaRepository;
import com.curves.franchise.repository.PjSumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ManagementController {
    private Logger logger = LoggerFactory.getLogger(ManagementController.class);

    @Autowired
    private PjSumRepository pjSumRepo;

    @Autowired
    private CaRepository caRepo;

    @RequestMapping(value = "/rest/benchmarking", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Integer>> findBenchmarkingByClubAndYearAndMonth(@RequestParam("clubId") int clubId, @RequestParam("year") int year, @RequestParam("month") int month) {
        List<Ca> cas = caRepo.findByCaYearAndCaMonth(year, month);
        logger.info("--result-"+cas.size());
        Map<String, List<Integer>> values = new HashMap<>(10);
        for (Ca ca : cas) {
            processList(clubId, values, "CmPostFlyer", ca, ca.getCmPostFlyer6());
            processList(clubId, values, "CmHandFlyer", ca, ca.getCmHandFlyer6());
            processList(clubId, values, "CmHandFlyerHours", ca, (int)(ca.getCmHandFlyerHours6()*100));
            processList(clubId, values, "CmOutGp", ca, ca.getCmOutGp6());
            processList(clubId, values, "CmOutGpHours", ca, (int)(ca.getCmOutGpHours6()*100));
            processList(clubId, values, "CmCpBox", ca, ca.getCmCpBox6());
            processList(clubId, values, "CmApoTotal", ca, ca.getCmApoTotal6());
            processList(clubId, values, "CmFaSum", ca, ca.getCmFaSum6());
            processList(clubId, values, "SalesAch", ca, ca.getSalesAch6());
            processList(clubId, values, "SalesRatio", ca, (int)(ca.getSalesRatio6()*100));
        }
        return values;
    }

    private void processList(int clubId, Map<String, List<Integer>> values, String key, Ca ca, int value) {
        List<Integer> valuesList = values.get(key);
        if (valuesList == null) {
            valuesList = new ArrayList<>(3);
            valuesList.add(value);
            valuesList.add(value);
            values.put(key, valuesList);
        } else {
            int max = valuesList.get(0);
            if (value > max) {
                valuesList.set(0, value);
            }
            int min = valuesList.get(1);
            if (value < min) {
                valuesList.set(1, value);
            }
        }
        if (ca.getClubId() == clubId) {
            valuesList.add(value);
        }
    }

    @RequestMapping(value = "/rest/trends", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Integer>> findTrendsByClub(@RequestParam("clubId") int clubId,
                                                       @RequestParam("yStart") int yStart, @RequestParam("mStart") int mStart,
                                                       @RequestParam("yEnd") int yEnd, @RequestParam("mEnd") int mEnd) {
        logger.info("---> trends, clubId: "+clubId+", y1:"+yStart+", y2:"+yEnd+", m1: "+mStart+", m2:"+mEnd);
        // TODO need to improve performance
        Map<String, List<Integer>> typeValues = new HashMap<>(10);
        List<PjSum> pjSums = pjSumRepo.findByClubIdAndYearBetweenAndMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
        List<Integer> newSales = new ArrayList<>(6);
        typeValues.put("NewSales", newSales);
        List<Integer> productsRevenue = new ArrayList<>(6);
        typeValues.put("ProductsRevenue", productsRevenue);
        List<Integer> brOwnRef = new ArrayList<>(6);
        typeValues.put("BrOwnRef", brOwnRef);
        List<Integer> faSum = new ArrayList<>(6);
        typeValues.put("FaSum", faSum);
        List<Integer> exitsRatio = new ArrayList<>(6);
        typeValues.put("ExitsRatio", exitsRatio);
        for (PjSum pjSum : pjSums) {
            newSales.add(pjSum.getNewSales());
            productsRevenue.add(pjSum.getProductsRevenue());
            brOwnRef.add(pjSum.getBrOwnRef());
            faSum.add(pjSum.getFaSum());
            exitsRatio.add((int)(pjSum.getExitRatio()*100));
        }
        List<Ca> cas = caRepo.findByClubIdAndCaYearBetweenAndCaMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
        List<Integer> svcTotalWo6 = new ArrayList<>(6);
        typeValues.put("SvcTotalWo", svcTotalWo6);
        List<Integer> cmTotalGot6 = new ArrayList<>(6);
        typeValues.put("CmTotalGot", cmTotalGot6);
        List<Integer> cmInApptRatio6 = new ArrayList<>(6);
        typeValues.put("CmInApptRatio", cmInApptRatio6);
        List<Integer> cmFaSum6 = new ArrayList<>(6);
        typeValues.put("CmFaSum", cmFaSum6);
        List<Integer> salesRatio6 = new ArrayList<>(6);
        typeValues.put("SalesRatio", salesRatio6);
        for (Ca ca : cas) {
            svcTotalWo6.add(ca.getSvcTotalWo6());
            cmTotalGot6.add(ca.getCmTotalGot6());
            cmInApptRatio6.add((int)(ca.getCmInApptRatio6()*100));
            salesRatio6.add((int)(ca.getSalesRatio6()*100));
            cmFaSum6.add(ca.getCmFaSum6());
        }

        return typeValues;
    }
}
