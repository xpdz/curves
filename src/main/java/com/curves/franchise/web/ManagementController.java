package com.curves.franchise.web;

import com.curves.franchise.domain.Ca;
import com.curves.franchise.domain.Club;
import com.curves.franchise.domain.Goal;
import com.curves.franchise.domain.PjSum;
import com.curves.franchise.repository.CaRepository;
import com.curves.franchise.repository.ClubRepository;
import com.curves.franchise.repository.GoalRepository;
import com.curves.franchise.repository.PjSumRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@RestController
public class ManagementController {
    private Logger logger = LoggerFactory.getLogger(ManagementController.class);

    @Autowired
    private ClubRepository clubRepo;

    @Autowired
    private GoalRepository goalRepo;

    @Autowired
    private PjSumRepository pjSumRepo;

    @Autowired
    private CaRepository caRepo;

    @RequestMapping(value = "/rest/goal", method = RequestMethod.GET)
    @ResponseBody
    public Goal findGoal(@RequestParam("year") int year, @RequestParam("month") int month) {
        Goal g = goalRepo.findByGYearAndGMonth(year, month);
        if (g == null) {
            g = new Goal();
        }
        return g;
    }

    @RequestMapping(value = "/rest/goal", method = RequestMethod.POST)
    @ResponseBody
    public Long saveGoal(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("item") String item, @RequestParam("value") float value) {
        logger.info("Save goal, item: "+item+", value: "+value+" @ "+year+"-"+month);
        Goal goal = goalRepo.findByGYearAndGMonth(year, month);
        if (goal == null) {
            goal = new Goal();
            goal.setgYear(year);
            goal.setgMonth(month);
        }
        if ("newSalesRevenue".equals(item) || "duesDraftRevenue".equals(item) ||
                "productsRevenue".equals(item) || "revenue".equals(item)) {
            if ("newSalesRevenue".equals(item)) {
                goal.setNewSalesRevenue((int)value);
            } else if ("duesDraftsRevenue".equals(item)) {
                goal.setDuesDraftRevenue((int)value);
            } else if ("productsRevenue".equals(item)) {
                goal.setProductsRevenue((int)value);
            } else if ("revenue".equals(item)) {
                goal.setRevenue((int)value);
            }
        } else {
            if ("cmPostFlyer6".equals(item)) {
                goal.setCmPostFlyer6((int)value);
            } else if ("cmHandFlyer6".equals(item)) {
                goal.setCmHandFlyer6((int)value);
            } else if ("cmHandFlyerHours6".equals(item)) {
                goal.setCmHandFlyerHours6(value);
            } else if ("cmOutGp6".equals(item)) {
                goal.setCmOutGp6((int)value);
            } else if ("cmOutGpHours6".equals(item)) {
                goal.setCmOutGpHours6(value);
            } else if ("cmCpBox6".equals(item)) {
                goal.setCmCpBox6((int)value);
            } else if ("cmApoTotal6".equals(item)) {
                goal.setCmApoTotal6((int)value);
            } else if ("cmFaSum6".equals(item)) {
                goal.setCmFaSum6((int)value);
            } else if ("salesAch6".equals(item)) {
                goal.setSalesAch6((int)value);
            } else if ("salesRatio6".equals(item)) {
                goal.setSalesRatio6(value);
            } else if ("cmOwnRefs6".equals(item)) {
                goal.setCmOwnRefs6((int)value);
            } else if ("cmOutAgpOut".equals(item)) {
                goal.setCmOutAgpOut((int)value);
            } else if ("cmInAgpOut6".equals(item)) {
                goal.setCmInAgpOut6((int)value);
            }
        }
        goal = goalRepo.save(goal);
        return goal.getId();
    }

    @RequestMapping(value = "/rest/rank", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Float> findRank(@RequestParam("item") String item, @RequestParam("year") int year, @RequestParam("month") int month) {
        Iterable<Club> clubs = clubRepo.findAll();
        Map<Integer, String> clubIdNameMap = new HashMap<>();
        for (Club club : clubs) {
            clubIdNameMap.put(club.getClubId(), club.getName());
        }
        Map<String, Float> values = new LinkedHashMap<>();
        if ("newSalesRevenue".equals(item) || "duesDraftsRevenue".equals(item) ||
             "productsRevenue".equals(item) || "revenue".equals(item)) {
            List<PjSum> pjSums = pjSumRepo.findByYearAndMonth(year, month, new Sort(Sort.Direction.DESC, item));
            for (PjSum pjSum : pjSums) {
                String clubName = clubIdNameMap.get(pjSum.getClubId());
                if (clubName == null) {
                    continue;
                }
                if ("newSalesRevenue".equals(item)) {
                    values.put(clubName, (float)pjSum.getNewSalesRevenue());
                } else if ("duesDraftsRevenue".equals(item)) {
                    values.put(clubName, (float)pjSum.getDuesDraftsRevenue());
                } else if ("productsRevenue".equals(item)) {
                    values.put(clubName, (float)pjSum.getProductsRevenue());
                } else if ("revenue".equals(item)) {
                    values.put(clubName, (float)pjSum.getRevenue());
                }
            }
        } else {
            List<Ca> cas = caRepo.findByCaYearAndCaMonth(year, month, new Sort(Sort.Direction.DESC, item));
            for (Ca ca : cas) {
                String clubName = clubIdNameMap.get(ca.getClubId());
                if (clubName == null) {
                    continue;
                }
                if ("cmPostFlyer6".equals(item)) {
                    values.put(clubName, (float)ca.getCmPostFlyer6());
                } else if ("cmHandFlyer6".equals(item)) {
                    values.put(clubName, (float)ca.getCmHandFlyer6());
                } else if ("cmHandFlyerHours6".equals(item)) {
                    values.put(clubName, ca.getCmHandFlyerHours6());
                } else if ("cmOutGp6".equals(item)) {
                    values.put(clubName, (float)ca.getCmOutGp6());
                } else if ("cmOutGpHours6".equals(item)) {
                    values.put(clubName, ca.getCmOutGpHours6());
                } else if ("cmCpBox6".equals(item)) {
                    values.put(clubName, (float)ca.getCmCpBox6());
                } else if ("cmApoTotal6".equals(item)) {
                    values.put(clubName, (float)ca.getCmApoTotal6());
                } else if ("cmFaSum6".equals(item)) {
                    values.put(clubName, (float)ca.getCmFaSum6());
                } else if ("salesAch6".equals(item)) {
                    values.put(clubName, (float)ca.getSalesAch6());
                } else if ("salesRatio6".equals(item)) {
                    values.put(clubName, ca.getSalesRatio6());
                } else if ("cmOwnRefs6".equals(item)) {
                    values.put(clubName, (float)ca.getCmOwnRefs6());
                } else if ("cmOutAgpOut".equals(item)) {
                    values.put(clubName, (float)ca.getCmOutAgpOut6());
                } else if ("cmInAgpOut6".equals(item)) {
                    values.put(clubName, (float)ca.getCmInAgpOut6());
                }
            }
        }
        return values;
    }

    @RequestMapping(value = "/rest/benchmarking", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Map<String, Integer>> findBenchmarkingByClubAndYearAndMonth(@RequestParam("clubId") int clubId, @RequestParam("year") int year, @RequestParam("month") int month) {
        List<Ca> cas = caRepo.findByCaYearAndCaMonth(year, month, new Sort(Sort.Direction.DESC));
        Map<String, Map<String, Integer>> values = new HashMap<>(10);
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
            processList(clubId, values, "SalesRatio", ca, (int) (ca.getSalesRatio6() * 100));
        }
        return values;
    }

    private void processList(int clubId, Map<String, Map<String, Integer>> valueV, String key, Ca ca, int value) {
        Map<String, Integer> values = valueV.get(key);
        if (values == null) {
            values = new HashMap<>(3);
            values.put("max", value);
            values.put("min", value);
            valueV.put(key, values);
        } else {
            int max = values.get("max");
            if (value > max) {
                values.put("max", value);
            }
            int min = values.get("min");
            if (value < min) {
                values.put("min", value);
            }
        }
        if (ca.getClubId() == clubId) {
            values.put("val", value);
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

    @RequestMapping(value = "/rest/pjSummary", method = RequestMethod.GET)
    @ResponseBody
    public List<PjSum> findPjSummaryByClub(@RequestParam("clubId") int clubId,
                                                       @RequestParam("yStart") int yStart, @RequestParam("mStart") int mStart,
                                                       @RequestParam("yEnd") int yEnd, @RequestParam("mEnd") int mEnd) {

        return pjSumRepo.findByClubIdAndYearBetweenAndMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
    }

    @RequestMapping(value = "/rest/caSummary", method = RequestMethod.GET)
    @ResponseBody
    public List<Ca> findCaSummaryByClub(@RequestParam("clubId") int clubId,
                                        @RequestParam("yStart") int yStart, @RequestParam("mStart") int mStart,
                                        @RequestParam("yEnd") int yEnd, @RequestParam("mEnd") int mEnd) {

        return caRepo.findByClubIdAndCaYearBetweenAndCaMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
    }

    @RequestMapping(value = "/rest/usReport", method = RequestMethod.GET)
    @ResponseBody
    public List<PjSum> findUsReport(@RequestParam("year") int year, @RequestParam("month") int month) {
        return pjSumRepo.findByYearAndMonth(year, month, new Sort(Sort.Direction.ASC, "clubId"));
    }

    @RequestMapping(value = "/rest/US_report_for_Taiwan", method = RequestMethod.GET, produces="application/vnd.ms-excel")
    @ResponseBody
    public FileSystemResource exportUsReport(@RequestParam("year") int year, @RequestParam("month") int month) {
        List<PjSum> pjSums = pjSumRepo.findByYearAndMonth(year, month, new Sort(Sort.Direction.ASC, "clubId"));
        Workbook wb = new HSSFWorkbook();
        Sheet sh = wb.createSheet();
        createTitleRow(sh);
        for (int i = 0; i < pjSums.size(); i++) {
            PjSum pjSum = pjSums.get(i);
            Row row = sh.createRow(i+1);
            createRowX(row, pjSum);
        }
        File tmp = null;
        try {
            tmp = File.createTempFile("US_report_for_Taiwan", "xls");
            OutputStream fos = new FileOutputStream(tmp);
            wb.write(fos);
            fos.close();
        } catch (IOException e) {
            logger.error("", e);
        }
        return new FileSystemResource(tmp);
    }

    private void createRowX(Row row, PjSum pjSum) {
        Cell cell = row.createCell(0);
        cell.setCellValue(pjSum.getMonth());
        cell = row.createCell(1);
        cell.setCellValue(pjSum.getYear());
        cell = row.createCell(2);
        cell.setCellValue(pjSum.getClubId());
        cell = row.createCell(3);
        cell.setCellValue(pjSum.getMaxWorkOuts());
        cell = row.createCell(4);
        cell.setCellValue(pjSum.getNewSalesRevenue());
        cell = row.createCell(5);
        cell.setCellValue(0);
        cell = row.createCell(6);
        cell.setCellValue(pjSum.getDuesDraftsRevenue());
        cell = row.createCell(7);
        cell.setCellValue(0);
        cell = row.createCell(8);
        cell.setCellValue(pjSum.getProductsRevenue());
        cell = row.createCell(9);
        cell.setCellValue(0);
        cell = row.createCell(10);
        cell.setCellValue(pjSum.getEnrollAch());
        cell = row.createCell(11);
        cell.setCellValue(pjSum.getEnrollMonthly());
        cell = row.createCell(12);
        cell.setCellValue(pjSum.getEnrollAllPrepay());
        cell = row.createCell(13);
        cell.setCellValue(pjSum.getExits());
        cell = row.createCell(14);
        cell.setCellValue(pjSum.getIncomingCalls());
        cell = row.createCell(15);
        cell.setCellValue(pjSum.getIncomingApo());
        cell = row.createCell(16);
        cell.setCellValue(pjSum.getOutgoingCalls());
        cell = row.createCell(17);
        cell.setCellValue(pjSum.getOutgoingApo());
        cell = row.createCell(18);
        cell.setCellValue(pjSum.getBrOthersRef()+pjSum.getAgpOutApoIn());
        cell = row.createCell(19);
        cell.setCellValue(pjSum.getBrandedNewspaper());
        cell = row.createCell(20);
        cell.setCellValue(pjSum.getBrandedTv());
        cell = row.createCell(21);
        cell.setCellValue(pjSum.getBrandedInternet());
        cell = row.createCell(22);
        cell.setCellValue(pjSum.getAgpInDirectMail());
        cell = row.createCell(23);
        cell.setCellValue(pjSum.getBrandedSign());
        cell = row.createCell(24);
        cell.setCellValue(pjSum.getAgpInMailFlyer()+pjSum.getAgpInHandFlyer()+pjSum.getAgpOutApoOut());
        cell = row.createCell(25);
        cell.setCellValue(0);
        cell = row.createCell(26);
        cell.setCellValue(pjSum.getAgpInCp()+pjSum.getAgpOutApoBlog()+pjSum.getAgpOutApoBag());
        cell = row.createCell(27);
        cell.setCellValue(pjSum.getBrandedMate());
        cell = row.createCell(28);
        cell.setCellValue(pjSum.getBrandedOthers());
        cell = row.createCell(29);
        cell.setCellValue(0);
        cell = row.createCell(30);
        cell.setCellValue(pjSum.getEnrolled());
    }

    private void createTitleRow(Sheet sh) {
        Row row = sh.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("PSMonth");
        cell = row.createCell(1);
        cell.setCellValue("PSYear");
        cell = row.createCell(2);
        cell.setCellValue("Franchisee");
        cell = row.createCell(3);
        cell.setCellValue("HighestTraffic");
        cell = row.createCell(4);
        cell.setCellValue("NewSales");
        cell = row.createCell(5);
        cell.setCellValue("Adjustments");
        cell = row.createCell(6);
        cell.setCellValue("DuesDrafts");
        cell = row.createCell(7);
        cell.setCellValue("WeightManagement");
        cell = row.createCell(8);
        cell.setCellValue("Products");
        cell = row.createCell(9);
        cell.setCellValue("RetailSales");
        cell = row.createCell(10);
        cell.setCellValue("CheckDraftsAO");
        cell = row.createCell(11);
        cell.setCellValue("MonthtoMonth");
        cell = row.createCell(12);
        cell.setCellValue("PrePays");
        cell = row.createCell(13);
        cell.setCellValue("MemberCancels");
        cell = row.createCell(14);
        cell.setCellValue("Call");
        cell = row.createCell(15);
        cell.setCellValue("CAppt");
        cell = row.createCell(16);
        cell.setCellValue("BR");
        cell = row.createCell(17);
        cell.setCellValue("BRAppt");
        cell = row.createCell(18);
        cell.setCellValue("Referral");
        cell = row.createCell(19);
        cell.setCellValue("Newspaper");
        cell = row.createCell(20);
        cell.setCellValue("Television");
        cell = row.createCell(21);
        cell.setCellValue("Internet");
        cell = row.createCell(22);
        cell.setCellValue("DirectMail");
        cell = row.createCell(23);
        cell.setCellValue("Sign");
        cell = row.createCell(24);
        cell.setCellValue("Flyer");
        cell = row.createCell(25);
        cell.setCellValue("Phone");
        cell = row.createCell(26);
        cell.setCellValue("LeadBag");
        cell = row.createCell(27);
        cell.setCellValue("NationalAlliance");
        cell = row.createCell(28);
        cell.setCellValue("Other");
        cell = row.createCell(29);
        cell.setCellValue("Wellness");
        cell = row.createCell(30);
        cell.setCellValue("TotalMembers");
    }
}
