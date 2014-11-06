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
    private Logger logger = LoggerFactory.getLogger(PjSumController.class);

    @Autowired
    private PjSumRepository pjSumRepo;

    @Autowired
    private CaRepository caRepo;

    @RequestMapping(value = "/rest/benchmarking", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Integer>> findBenchmarkingByClub(@RequestParam("clubId") int clubId) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        List<Ca> cas = caRepo.findByCaYearAndCaMonth(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
        Map<String, List<Integer>> values = new HashMap<>(10);
        for (Ca ca : cas) {
            int tm = ca.getSvcTm6();
            List<Integer> tmList = values.get("TM");
            if (tmList == null) {
                tmList = new ArrayList<>(3);
                tmList.add(tm);
                tmList.add(tm);
                values.put("TM", tmList);
            } else {
                int max = tmList.get(0);
                if (tm > max) {
                    tmList.set(0, tm);
                }
                int min = tmList.get(1);
                if (tm < min) {
                    tmList.set(1, tm);
                }
            }
            if (ca.getClubId() == clubId) {
                tmList.add(tm);
            }

            int tam = (int)(ca.getSvcActive6()*100);
            List<Integer> tamList = values.get("TAM");
            if (tamList == null) {
                tamList = new ArrayList<>(3);
                tamList.add(tam);
                tamList.add(tam);
                values.put("TAM", tamList);
            } else {
                int max = tamList.get(0);
                if (tam > max) {
                    tamList.set(0, tam);
                }
                int min = tamList.get(1);
                if (tam < min) {
                    tamList.set(1, tam);
                }
            }
            if (ca.getClubId() == clubId) {
                tamList.add(tam);
            }

            int holdRatio = (int)(ca.getSvcHoldRatio6()*100);
            List<Integer> holdRatioList = values.get("HoldRatio");
            if (holdRatioList == null) {
                holdRatioList = new ArrayList<>(3);
                holdRatioList.add(holdRatio);
                holdRatioList.add(holdRatio);
                values.put("HoldRatio", holdRatioList);
            } else {
                int max = holdRatioList.get(0);
                if (holdRatio > max) {
                    holdRatioList.set(0, holdRatio);
                }
                int min = holdRatioList.get(1);
                if (holdRatio < min) {
                    holdRatioList.set(1, holdRatio);
                }
            }
            if (ca.getClubId() == clubId) {
                holdRatioList.add(holdRatio);
            }

            int totalwo = ca.getSvcTotalWo6();
            List<Integer> totalwoList = values.get("TotalWo");
            if (totalwoList == null) {
                totalwoList = new ArrayList<>(3);
                totalwoList.add(totalwo);
                totalwoList.add(totalwo);
                values.put("TotalWo", totalwoList);
            } else {
                int max = totalwoList.get(0);
                if (totalwo > max) {
                    totalwoList.set(0, totalwo);
                }
                int min = totalwoList.get(1);
                if (totalwo < min) {
                    totalwoList.set(1, totalwo);
                }
            }
            if (ca.getClubId() == clubId) {
                totalwoList.add(totalwo);
            }

            int exitsRatio = (int)(ca.getSvcHoldRatio6()*100);
            List<Integer> exitsRatioList = values.get("ExitsRatio");
            if (exitsRatioList == null) {
                exitsRatioList = new ArrayList<>(3);
                exitsRatioList.add(exitsRatio);
                exitsRatioList.add(exitsRatio);
                values.put("ExitsRatio", exitsRatioList);
            } else {
                int max = exitsRatioList.get(0);
                if (exitsRatio > max) {
                    exitsRatioList.set(0, exitsRatio);
                }
                int min = exitsRatioList.get(1);
                if (exitsRatio < min) {
                    exitsRatioList.set(1, exitsRatio);
                }
            }
            if (ca.getClubId() == clubId) {
                exitsRatioList.add(exitsRatio);
            }

            int active12 = (int)(ca.getSvc12_6()*100);
            List<Integer> active12List = values.get("Active12");
            if (active12List == null) {
                active12List = new ArrayList<>(3);
                active12List.add(active12);
                active12List.add(active12);
                values.put("Active12", active12List);
            } else {
                int max = active12List.get(0);
                if (active12 > max) {
                    active12List.set(0, active12);
                }
                int min = active12List.get(1);
                if (active12 < min) {
                    active12List.set(1, active12);
                }
            }
            if (ca.getClubId() == clubId) {
                active12List.add(active12);
            }

            int postFlyer = ca.getCmPostFlyer6();
            List<Integer> postFlyerList = values.get("PostFlyer");
            if (postFlyerList == null) {
                postFlyerList = new ArrayList<>(3);
                postFlyerList.add(postFlyer);
                postFlyerList.add(postFlyer);
                values.put("PostFlyer", postFlyerList);
            } else {
                int max = postFlyerList.get(0);
                if (postFlyer > max) {
                    postFlyerList.set(0, postFlyer);
                }
                int min = postFlyerList.get(1);
                if (postFlyer < min) {
                    postFlyerList.set(1, postFlyer);
                }
            }
            if (ca.getClubId() == clubId) {
                postFlyerList.add(postFlyer);
            }

            int handFlyer = ca.getCmPostFlyer6();
            List<Integer> handFlyerList = values.get("HandFlyer");
            if (handFlyerList == null) {
                handFlyerList = new ArrayList<>(3);
                handFlyerList.add(handFlyer);
                handFlyerList.add(handFlyer);
                values.put("HandFlyer", handFlyerList);
            } else {
                int max = handFlyerList.get(0);
                if (handFlyer > max) {
                    handFlyerList.set(0, handFlyer);
                }
                int min = handFlyerList.get(1);
                if (handFlyer < min) {
                    handFlyerList.set(1, handFlyer);
                }
            }
            if (ca.getClubId() == clubId) {
                handFlyerList.add(handFlyer);
            }

            int outGp = ca.getCmOutGp6();
            List<Integer> outGpList = values.get("OutGp");
            if (outGpList == null) {
                outGpList = new ArrayList<>(3);
                outGpList.add(outGp);
                outGpList.add(outGp);
                values.put("OutGp", outGpList);
            } else {
                int max = outGpList.get(0);
                if (outGp > max) {
                    outGpList.set(0, outGp);
                }
                int min = outGpList.get(1);
                if (outGp < min) {
                    outGpList.set(1, outGp);
                }
            }
            if (ca.getClubId() == clubId) {
                outGpList.add(outGp);
            }

            int ownRefs = ca.getCmOwnRefs6();
            List<Integer> ownRefsList = values.get("OwnRefs");
            if (ownRefsList == null) {
                ownRefsList = new ArrayList<>(3);
                ownRefsList.add(ownRefs);
                ownRefsList.add(ownRefs);
                values.put("OwnRefs", ownRefsList);
            } else {
                int max = ownRefsList.get(0);
                if (ownRefs > max) {
                    ownRefsList.set(0, ownRefs);
                }
                int min = ownRefsList.get(1);
                if (ownRefs < min) {
                    ownRefsList.set(1, ownRefs);
                }
            }
            if (ca.getClubId() == clubId) {
                ownRefsList.add(ownRefs);
            }

            int faSum = ca.getCmFaSum6();
            List<Integer> faSumList = values.get("FaSum");
            if (faSumList == null) {
                faSumList = new ArrayList<>(3);
                faSumList.add(faSum);
                faSumList.add(faSum);
                values.put("FaSum", faSumList);
            } else {
                int max = faSumList.get(0);
                if (faSum > max) {
                    faSumList.set(0, faSum);
                }
                int min = faSumList.get(1);
                if (faSum < min) {
                    faSumList.set(1, faSum);
                }
            }
            if (ca.getClubId() == clubId) {
                faSumList.add(faSum);
            }
        }
        return values;
    }
    @RequestMapping(value = "/rest/trends", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> findTrendsByTypeAndClub(@RequestParam("clubId") int clubId, @RequestParam("trendType") String trendType) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        int yEnd = c.get(Calendar.YEAR);
        int mEnd = c.get(Calendar.MONTH);
        c.add(Calendar.MONTH, -7);
        int yStart = c.get(Calendar.YEAR);
        int mStart = c.get(Calendar.MONTH);
        logger.info("---> trends, clubId: "+clubId+", y1:"+yStart+", y2:"+yEnd+", m1: "+mStart+", m2:"+mEnd);
        // TODO need to improve performance
        if (trendType.startsWith("PJ")) {
            return getTrendsFromPj(clubId, trendType, yEnd, mEnd, yStart, mStart);
        } else {
            return getTrendsFromCa(clubId, trendType, yEnd, mEnd, yStart, mStart);
        }
    }

    private List<Integer> getTrendsFromPj(int clubId, String trendType, int yEnd, int mEnd, int yStart, int mStart) {
        List<PjSum> pjSums = pjSumRepo.findByClubIdAndYearBetweenAndMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
        List<Integer> values = new ArrayList<>();
        for (PjSum pjSum : pjSums) {
            if ("PJ_NewSales".equals(trendType)) {
                values.add(pjSum.getNewSales());
            } else if ("PJ_ProductsRevenue".equals(trendType)) {
                values.add(pjSum.getProductsRevenue());
            } else if ("PJ_Exits".equals(trendType)) {
                values.add(pjSum.getExits());
            } else if ("PJ_BrOwnRef".equals(trendType)) {
                values.add(pjSum.getBrOwnRef());
            } else if ("PJ_FaSum".equals(trendType)) {
                values.add(pjSum.getFaSum());
            }
        }
        logger.info("--->> PJ trneds data: "+values);
        return values;
    }

    private List<Integer> getTrendsFromCa(int clubId, String trendType, int yEnd, int mEnd, int yStart, int mStart) {
        List<Ca> cas = caRepo.findByClubIdAndCaYearBetweenAndCaMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
        List<Integer> values = new ArrayList<>();
        for (Ca ca : cas) {
            if ("CA_SvcTotalWo".equals(trendType)) {
                values.add(ca.getSvcTotalWo6());
            } else if ("CA_SvcExitsRatio".equals(trendType)) {
                values.add((int)(ca.getSvcExitsRatio6()*100));
            } else if ("CA_SvcMeasureRatio".equals(trendType)) {
                values.add((int)ca.getSvcMeasureRatio6()*100);
            } else if ("CA_CmTotalGot".equals(trendType)) {
                values.add(ca.getCmTotalGot6());
            } else if ("CA_CmApoTotal".equals(trendType)) {
                values.add(ca.getCmApoTotal6());
            } else if ("CA_CmInApptRatio".equals(trendType)) {
                values.add((int)(ca.getCmInApptRatio6()*100));
            } else if ("CA_CmFaSum".equals(trendType)) {
                values.add(ca.getCmFaSum6());
            } else if ("CA_CmShowRatio".equals(trendType)) {
                values.add((int)(ca.getCmShowRatio6()*100));
            } else if ("CA_SalesTotal".equals(trendType)) {
                values.add(ca.getSalesTotal6());
            } else if ("CA_SalesRatio".equals(trendType)) {
                values.add((int)(ca.getSalesRatio6()*100));
            } else if ("CA_CmInApptRatio".equals(trendType)) {
                values.add((int)(ca.getCmInApptRatio6()*100));
            } else if ("CA_SalesAchAppRatio".equals(trendType)) {
                values.add((int)(ca.getSalesAchAppRatio6()*100));
            }
        }
        logger.info("--->> return: "+values);
        return values;
    }
}
