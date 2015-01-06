package com.curves.franchise.web;

import com.curves.franchise.domain.*;
import com.curves.franchise.repository.*;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class ManagementController {
    private Logger logger = LoggerFactory.getLogger(ManagementController.class);

    @Autowired
    private ClubRepository clubRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private GoalRepository goalRepo;

    @Autowired
    private PjSumRepository pjSumRepo;

    @Autowired
    private CaRepository caRepo;

    @RequestMapping(value = "/rest/user", method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(@RequestParam("clubId") int clubId, @RequestParam("name") String name, @RequestParam("owner") String owner) {
        logger.info("---saveUser---clubId: "+clubId+", name: "+name+", owner: "+owner);
        Club club = new Club();
        club.setClubId(clubId);
        club.setName(name);
        club.setOwner(owner);

        User user = new User();
        user.setUsername("" + clubId);
        user.setPassword("" + clubId);
        user.setEnabled(true);

        try {
            clubRepo.save(club);
            userRepo.save(user);
        } catch (Exception e) {
            return null;
        }
        return "";
    }

    @RequestMapping(value = "/rest/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(@RequestParam("clubId") String clubId, @RequestParam("password") String password) {
        logger.info("---changePassword---clubId: "+clubId);
        User user = userRepo.findByUsername(clubId);
        if (user == null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
        return "";
    }

    @RequestMapping(value = "/rest/reset_password")
    public void processPassword(@RequestParam("clubId") String clubId) throws Exception {
        logger.info("Request reset password! user: "+clubId);
        User user = userRepo.findByUsername(clubId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        user.setPassword(encoder.encode(clubId));
        userRepo.save(user);
    }

    @RequestMapping(value = "/rest/goal")
    @ResponseBody
    public Goal findGoal(@RequestParam("year") int year, @RequestParam("month") int month) {
        logger.info("---findGoal---year: "+year+", month: "+month);
        Goal g = goalRepo.findByGYearAndGMonth(year, month);
        if (g == null) {
            logger.info("---findGoal--- Goal not found!");
            g = new Goal();
        }
        return g;
    }

    @RequestMapping(value = "/rest/goal", method = RequestMethod.POST)
    @ResponseBody
    public Long saveGoal(@RequestParam("year") int year, @RequestParam("month") int month, @RequestParam("item") String item, @RequestParam("value") float value) {
        logger.info("---saveGoal---item: "+item+", value: "+value+" @ "+year+"-"+month);
        Goal goal = goalRepo.findByGYearAndGMonth(year, month);
        if (goal == null) {
            goal = new Goal();
            goal.setgYear(year);
            goal.setgMonth(month);
        }
        if ("newSalesRevenue".equals(item) || "duesDraftsRevenue".equals(item) ||
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
            } else if ("cmOutAgpOut6".equals(item)) {
                goal.setCmOutAgpOut6((int)value);
            } else if ("cmInAgpOut6".equals(item)) {
                goal.setCmInAgpOut6((int)value);
            }
        }
        goal = goalRepo.save(goal);
        logger.info("---saveGoal---completed.");
        return goal.getId();
    }

    @RequestMapping(value = "/rest/rank")
    @ResponseBody
    public Map<String, Float> findRank(@RequestParam("item") String item, @RequestParam("year") int year, @RequestParam("month") int month) {
        logger.info("---findRank---item: "+item+", year: "+year+", month: "+month);
        Iterable<Club> clubs = clubRepo.findAll();
        Map<Integer, String> clubIdNameMap = new HashMap<>();
        for (Club club : clubs) {
            clubIdNameMap.put(club.getClubId(), club.getName());
        }
        logger.info("---findRank---clubIdNameMap.size: "+clubIdNameMap.size());
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
                } else if ("cmOutAgpOut6".equals(item)) {
                    values.put(clubName, (float)ca.getCmOutAgpOut6());
                } else if ("cmInAgpOut6".equals(item)) {
                    values.put(clubName, (float)ca.getCmInAgpOut6());
                }
            }
        }
        logger.info("---findRank---values.size: "+values.size());
        return values;
    }

    @RequestMapping(value = "/rest/CaAll")
    @ResponseBody
    public Map<String, Map<String, Number>> findCaAll(@RequestParam("caYear") int caYear, @RequestParam("caMonth") int caMonth) {
        logger.info("---findCaAll---caYear: " + caYear + ", caMonth: " + caMonth);
        return new CaAllHelper().fillCaAllClubs(caRepo, clubRepo, caYear, caMonth);
    }

    @RequestMapping(value = "/rest/CaAll/stat")
    @ResponseBody
    public Map<String, Map<String, Number>> findCaAllStat(@RequestParam("caYear") int caYear, @RequestParam("caMonth") int caMonth) {
        logger.info("---findCaAll/stat---caYear: " + caYear + ", caMonth: " + caMonth);
        return new CaAllHelper().fillCaAllStat(caRepo, caYear, caMonth);
    }

    @RequestMapping(value = "/rest/CaAll/CA_overall", produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @ResponseBody
    public FileSystemResource exportCaAll(@RequestParam("caYear") int caYear, @RequestParam("caMonth") int caMonth) {
        logger.info("---exportCaAll---caYear: " + caYear + ", caMonth: " + caMonth);
        Workbook wb = null;
        String fdl = System.getProperty("user.home") + File.separator + "curves_data";
        File template = new File(fdl + File.separator + "CA_Summary_Sheet_ALL_Clubs.xlsx");
        File target = null;
        try {
            target = File.createTempFile("CA_Summary_Sheet_ALL_Clubs", "xlsx");
            FileUtils.copyFile(template, target);
            wb = WorkbookFactory.create(new FileInputStream(target));
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

        Sheet sh = wb.getSheetAt(0);
        CellStyle cellStyle0 = wb.createCellStyle();
        cellStyle0.setDataFormat((short)9);
        CellStyle cellStyle1 = wb.createCellStyle();
        cellStyle1.setDataFormat(wb.createDataFormat().getFormat("0.0%"));

        Map<String, Map<String, Number>> itemStatMap = new CaAllHelper().fillCaAllStat(caRepo, caYear, caMonth);
        Map<String, Map<String, Number>> clubMap = new CaAllHelper().fillCaAllClubs(caRepo, clubRepo, caYear, caMonth);
        Set<String> keyItem = itemStatMap.keySet();
        int rowIdx = 1;
        for (String item : keyItem) {
            Map<String, Number> statMap = itemStatMap.get(item);
            Row row = sh.getRow(rowIdx);
            formatValue(wb, row.createCell(3), item, statMap.get("sum"));
            formatValue(wb, row.createCell(4), item, statMap.get("avg"));
            formatValue(wb, row.createCell(5), item, statMap.get("highest"));
            formatValue(wb, row.createCell(6), item, statMap.get("lowest"));
            rowIdx++;
            if (rowIdx == 9 || rowIdx == 37 || rowIdx == 43 || rowIdx == 68) {
                rowIdx = creatBlankCell4Sum(sh, rowIdx);
            } else if (rowIdx == 26) {
                rowIdx = creatBlankCell4Sum(sh, rowIdx);
                rowIdx = creatBlankCell4Sum(sh, rowIdx);
            }
        }

        int clubCellIdx = 7;
        Set<String> keyClubs = clubMap.keySet();
        for (String clubName : keyClubs) {
            Row row = sh.getRow(0);
            Cell cell = row.createCell(clubCellIdx);
            cell.setCellValue(clubName);
            cell.setCellStyle(row.getCell(3).getCellStyle());

            Map<String, Number> itemMap = clubMap.get(clubName);
            Set<String> items = itemMap.keySet();
            rowIdx = 1;
            for (String item : items) {
                row = sh.getRow(rowIdx);
                formatValue(wb, row.createCell(clubCellIdx), item, itemMap.get(item));
                rowIdx++;
                if (rowIdx == 9 || rowIdx == 37 || rowIdx == 43 || rowIdx == 68) {
                    rowIdx = createBlankCell4Club(sh, rowIdx, clubCellIdx);
                } else if (rowIdx == 26) {
                    rowIdx = createBlankCell4Club(sh, rowIdx, clubCellIdx);
                    rowIdx = createBlankCell4Club(sh, rowIdx, clubCellIdx);
                }
            }
            clubCellIdx++;
        }

        try {
            OutputStream fos = new FileOutputStream(target);
            wb.write(fos);
            fos.close();
        } catch (IOException e) {
            logger.error("", e);
        }
        return new FileSystemResource(target);
    }

    private void formatValue(Workbook wb, Cell cell, String item, Number v) {
        float value = v.floatValue();
        if (item.indexOf("Ratio") != -1 || item.equals("SvcMaxWo6")) {
            if (item.indexOf("ExitsRatio") != -1 || item.indexOf("HoldRatio") != -1) {
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setDataFormat(wb.createDataFormat().getFormat("0.0%"));
                cell.setCellStyle(cellStyle);
            } else {
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setDataFormat((short)9);
                cell.setCellStyle(cellStyle);
            }
            cell.setCellValue(v.doubleValue());
        }
        if (item.equals("SvcAvgWo6")
                || item.equals("Svc12_6") || item.equals("Svc8to11_6") || item.equals("Svc4to7_6")
                || item.equals("Svc1to3_6") || item.equals("Svc0_6") || item.equals("CmHandFlyerHours6")
                || item.equals("CmOutGpHours6") || item.equals("CmHandHoursPerApo6") || item.equals("CmOutGpHoursPerApo6")) {
            cell.setCellValue(new BigDecimal(v.floatValue()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
        } else {
            cell.setCellValue(v.intValue());
        }
    }

    private int createBlankCell4Club(Sheet sh, int rowIdx, int clubCellIdx) {
        Row row = sh.getRow(rowIdx);
        row.createCell(clubCellIdx, Cell.CELL_TYPE_BLANK);
        return rowIdx+1;
    }

    private int creatBlankCell4Sum(Sheet sh, int rowIdx) {
        Row row = sh.getRow(rowIdx);
        row.createCell(3, Cell.CELL_TYPE_BLANK);
        row.createCell(4, Cell.CELL_TYPE_BLANK);
        row.createCell(5, Cell.CELL_TYPE_BLANK);
        row.createCell(6, Cell.CELL_TYPE_BLANK);
        return rowIdx+1;
    }

    @RequestMapping(value = "/rest/benchmarking")
    @ResponseBody
    public Map<String, Map<String, Number>> findBenchmarking(@AuthenticationPrincipal UserDetails user,
                                                              @RequestParam("year") int year,
                                                              @RequestParam("month") int month) {
        // NOTE: This user isn't 000000, but a logged on franchisee
        int clubId = Integer.parseInt(user.getUsername());
        logger.info("---findBenchmarking---clubId: " + clubId + ", year: " + year + ", month: " + month);
        Map<String, Map<String, Number>> valueV = new HashMap<>(17);
        List<Ca> cas = caRepo.findByCaYearAndCaMonth(year, month, new Sort(Sort.Direction.DESC, "clubId"));
        List<PjSum> pjs = pjSumRepo.findByYearAndMonth(year, month, new Sort(Sort.Direction.DESC, "clubId"));
        if (cas.size() > 0) {
            fillCaItem(clubId, cas, "CmPostFlyer6", valueV);
            fillCaItem(clubId, cas, "CmHandFlyer6", valueV);
            fillCaItem(clubId, cas, "CmOutGp6", valueV);
            fillCaItem(clubId, cas, "CmHandFlyerHours6", valueV);
            fillCaItem(clubId, cas, "CmOutGpHours6", valueV);
            fillCaItem(clubId, cas, "CmCpBox6", valueV);
            fillCaItem(clubId, cas, "CmInAgpOut6", valueV);
            fillCaItem(clubId, cas, "CmOutAgpOut6", valueV);
            fillCaItem(clubId, cas, "CmApoTotal6", valueV);
            fillCaItem(clubId, cas, "CmFaSum6", valueV);
            fillCaItem(clubId, cas, "SalesAch6", valueV);
            fillCaItem(clubId, cas, "CmOwnRefs6", valueV);
            fillCaItem(clubId, cas, "SalesRatio6", valueV);
        }
        if (pjs.size() > 0) {
            fillPjItem(clubId, pjs, "NewSalesRevenue", valueV);
            fillPjItem(clubId, pjs, "DuesDraftsRevenue", valueV);
            fillPjItem(clubId, pjs, "Revenue", valueV);
            fillPjItem(clubId, pjs, "ProductsRevenue", valueV);
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

    private void fillCaItem(int clubId, List<Ca> cas, String item, Map<String, Map<String, Number>> valueV) {
        try {
            final Method xMethod = Ca.class.getDeclaredMethod("get" + item);
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
            Map<String, Number> valueX = new HashMap<>(5);
            valueV.put(item, valueX);
            valueX.put("max", (Number)xMethod.invoke(cas.get(0)));
            valueX.put("mid", (Number)xMethod.invoke(cas.get(cas.size() / 2)));
            valueX.put("min", (Number)xMethod.invoke(cas.get(cas.size() - 1)));
            int factor = 10;
            if (cas.size() < 10) {
                factor = 1;
            }
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (Number)xMethod.invoke(cas.get(i)));
                    valueX.put("rank", 1 + i * factor / cas.size());
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private void fillPjItem(int clubId, List<PjSum> pjSums, String item, Map<String, Map<String, Number>> valueV) {
        try {
            final Method xMethod = PjSum.class.getDeclaredMethod("get" + item);
            Collections.sort(pjSums, new Comparator<PjSum>() {
                @Override
                public int compare(PjSum pjSum1, PjSum pjSum2) {
                    try {
                        return ((Number) xMethod.invoke(pjSum2)).intValue() - ((Number) xMethod.invoke(pjSum1)).intValue();
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                    return 0;
                }
            });
            Map<String, Number> valueX = new HashMap<>(5);
            valueV.put(item, valueX);
            valueX.put("max", (Number)xMethod.invoke(pjSums.get(0)));
            valueX.put("mid", (Number)xMethod.invoke(pjSums.get(pjSums.size() / 2)));
            valueX.put("min", (Number)xMethod.invoke(pjSums.get(pjSums.size() - 1)));
            int factor = 10;
            if (pjSums.size() < 10) {
                factor = 1;
            }
            for (int i = 0; i < pjSums.size(); i++) {
                if (pjSums.get(i).getClubId() == clubId) {
                    valueX.put("you", (Number)xMethod.invoke(pjSums.get(i)));
                    valueX.put("rank", 1 + i * factor / pjSums.size());
                    valueX.put("total", pjSums.size());
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @RequestMapping(value = "/rest/trends")
    @ResponseBody
    public Map<String, List<Integer>> findTrendsByClub(@RequestParam("clubId") int clubId,
                                                       @RequestParam("yStart") int yStart, @RequestParam("mStart") int mStart,
                                                       @RequestParam("yEnd") int yEnd, @RequestParam("mEnd") int mEnd) {
        logger.info("---findTrendsByClub---clubId: "+clubId+", start: "+yStart+"-"+mStart+", end: "+yEnd+"-"+mEnd);
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

        logger.info("---findTrendsByClub---typeValues.size: "+typeValues.size());
        return typeValues;
    }

    @RequestMapping(value = "/rest/pjSummary")
    @ResponseBody
    public List<PjSum> findPjSummaryByClub(@RequestParam("clubId") int clubId,
                                                       @RequestParam("yStart") int yStart, @RequestParam("mStart") int mStart,
                                                       @RequestParam("yEnd") int yEnd, @RequestParam("mEnd") int mEnd) {

        logger.info("---findPjSummaryByClub---clubId: "+clubId+", start: "+yStart+"-"+mStart+", end: "+yEnd+"-"+mEnd);
        return pjSumRepo.findByClubIdAndYearBetweenAndMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
    }

    @RequestMapping(value = "/rest/PJ_summary", produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @ResponseBody
    public FileSystemResource exportPjSummaryByClub(@RequestParam("clubId") int clubId,
                                           @RequestParam("yStart") int yStart, @RequestParam("mStart") int mStart,
                                           @RequestParam("yEnd") int yEnd, @RequestParam("mEnd") int mEnd) {

        logger.info("---exportPjSummaryByClub---clubId: "+clubId+", start: "+yStart+"-"+mStart+", end: "+yEnd+"-"+mEnd);
        List<PjSum> pjSums = pjSumRepo.findByClubIdAndYearBetweenAndMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
        Workbook wb = null;
        String fdl = System.getProperty("user.home") + File.separator + "curves_data";
        File template = new File(fdl + File.separator + "PJ_summary.xlsx");
        File target = null;
        try {
            target = File.createTempFile("PJ_summary", "xlsx");
            FileUtils.copyFile(template, target);
            wb = WorkbookFactory.create(new FileInputStream(target));
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

        Sheet sh = wb.getSheetAt(0);

        for (int i = 0; i < pjSums.size(); i++) {
            Row row = sh.createRow(i+3);
            row.createCell(0).setCellValue(pjSums.get(i).getYear());
            row.createCell(1).setCellValue(pjSums.get(i).getMonth()+1);
            row.createCell(2).setCellValue(pjSums.get(i).getNewSales());
            row.createCell(3).setCellValue(pjSums.get(i).getExits());
            row.createCell(4).setCellValue(pjSums.get(i).getShiftIn());
            row.createCell(5).setCellValue(pjSums.get(i).getShiftOut());
            row.createCell(6).setCellValue(pjSums.get(i).getIncrement());
            row.createCell(7).setCellValue(pjSums.get(i).getRevenue());
            row.createCell(8).setCellValue(pjSums.get(i).getEnrolled());
            row.createCell(9).setCellValue(pjSums.get(i).getLeaves());
            row.createCell(10).setCellValue(pjSums.get(i).getValids());
            float v = pjSums.get(i).getSalesRatio();
            Cell cell = row.createCell(11);
            cell.setCellValue(v);
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat((short)9);
            cell.setCellStyle(cellStyle);
            v = pjSums.get(i).getExitRatio();
            cell = row.createCell(12);
            cell.setCellValue(v);
            cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(wb.createDataFormat().getFormat("0.0%"));
            cell.setCellStyle(cellStyle);
            v = pjSums.get(i).getLeaveRatio();
            cell = row.createCell(13);
            cell.setCellValue(v);
            cell.setCellStyle(cellStyle);
            row.createCell(14).setCellValue(pjSums.get(i).getMaxWorkOuts());
            row.createCell(15).setCellValue(pjSums.get(i).getNewSalesRevenue());
            row.createCell(16).setCellValue(pjSums.get(i).getDuesDraftsRevenue());
            row.createCell(17).setCellValue(pjSums.get(i).getProductsRevenue());
            row.createCell(18).setCellValue(pjSums.get(i).getOtherRevenue());
            row.createCell(19).setCellValue(pjSums.get(i).getFaSum());
            row.createCell(20).setCellValue(pjSums.get(i).getEnrollAch());
            row.createCell(21).setCellValue(pjSums.get(i).getEnrollMonthly());
            row.createCell(22).setCellValue(pjSums.get(i).getEnrollAllPrepay());
            row.createCell(23).setCellValue(pjSums.get(i).getExits());
        }

        wb.getCreationHelper().createFormulaEvaluator().evaluateAll();

        try {
            OutputStream fos = new FileOutputStream(target);
            wb.write(fos);
            fos.close();
        } catch (IOException e) {
            logger.error("", e);
        }
        return new FileSystemResource(target);
    }

    @RequestMapping(value = "/rest/caSummary")
    @ResponseBody
    public List<Ca> findCaSummaryByClub(@RequestParam("clubId") int clubId,
                                        @RequestParam("yStart") int yStart, @RequestParam("mStart") int mStart,
                                        @RequestParam("yEnd") int yEnd, @RequestParam("mEnd") int mEnd) {
        logger.info("---findCaSummaryByClub---clubId: "+clubId+", start: "+yStart+"-"+mStart+", end: "+yEnd+"-"+mEnd);
        return caRepo.findByClubIdAndCaYearBetweenAndCaMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
    }

    @RequestMapping(value = "/rest/CA_summary", produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @ResponseBody
    public FileSystemResource exportCaSummaryByClub(@RequestParam("clubId") int clubId,
                                                    @RequestParam("yStart") int yStart, @RequestParam("mStart") int mStart,
                                                    @RequestParam("yEnd") int yEnd, @RequestParam("mEnd") int mEnd) {

        logger.info("---exportCaSummaryByClub---clubId: "+clubId+", start: "+yStart+"-"+mStart+", end: "+yEnd+"-"+mEnd);
        List<Ca> cas = caRepo.findByClubIdAndCaYearBetweenAndCaMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
        Workbook wb = null;
        String fdl = System.getProperty("user.home") + File.separator + "curves_data";
        File template = new File(fdl + File.separator + "CA_summary.xlsx");
        File target = null;
        try {
            target = File.createTempFile("CA_summary", "xlsx");
            FileUtils.copyFile(template, target);
            wb = WorkbookFactory.create(new FileInputStream(target));
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

        Sheet sh = wb.getSheetAt(0);

        Club club = clubRepo.findOne(clubId);
        sh.getRow(0).getCell(0).setCellValue(club.getName());
        sh.getRow(1).getCell(0).setCellValue(club.getOwner());

        CellStyle cellStyle0 = wb.createCellStyle();
        cellStyle0.setDataFormat((short)9);
        CellStyle cellStyle1 = wb.createCellStyle();
        cellStyle1.setDataFormat(wb.createDataFormat().getFormat("0.0%"));

        for (int i = 0; i < cas.size(); i++) {
            Ca ca = cas.get(i);
            Row row = sh.getRow(0);
            row.createCell(i+2).setCellValue(ca.getCaYear()+"-"+(ca.getCaMonth()+1));
            row = sh.getRow(13);
            row.createCell(i+2).setCellValue(ca.getSvcTm6());
            row = sh.getRow(14);
            row.createCell(i+2).setCellValue(ca.getSvcHold6());
            row = sh.getRow(15);
            row.createCell(i+2).setCellValue(ca.getSvcActive6());
            row = sh.getRow(16);
            Cell cell = row.createCell(i + 2);
            cell.setCellValue(ca.getSvcHoldRatio6());
            cell.setCellStyle(cellStyle1);
            row = sh.getRow(17);
            row.createCell(i+2).setCellValue(ca.getSvcTotalWo6());
            row = sh.getRow(18);
            float v = new BigDecimal(ca.getSvcAvgWo6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            row.createCell(i+2).setCellValue(v);
            row = sh.getRow(19);
            cell = row.createCell(i+2);
            cell.setCellValue(ca.getSvcMaxWo6());
            cell.setCellStyle(cellStyle0);
            row = sh.getRow(20);
            row.createCell(i+2).setCellValue(ca.getSvcExits6());
            row = sh.getRow(21);
            cell = row.createCell(i+2);
            cell.setCellValue(ca.getSvcExitsRatio6());
            cell.setCellStyle(cellStyle1);
            row = sh.getRow(22);
            row.createCell(i+2).setCellValue(ca.getSvcMeasure6());
            row = sh.getRow(23);
            cell = row.createCell(i+2);
            cell.setCellValue(ca.getSvcMeasureRatio6());
            cell.setCellStyle(cellStyle0);
            row = sh.getRow(24);
            v = new BigDecimal(ca.getSvc12_6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            row.createCell(i+2).setCellValue(v);
            row = sh.getRow(25);
            v = new BigDecimal(ca.getSvc8to11_6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            row.createCell(i+2).setCellValue(v);
            row = sh.getRow(26);
            v = new BigDecimal(ca.getSvc4to7_6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            row.createCell(i+2).setCellValue(v);
            row = sh.getRow(27);
            v = new BigDecimal(ca.getSvc1to3_6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            row.createCell(i+2).setCellValue(v);
            row = sh.getRow(28);
            v = new BigDecimal(ca.getSvc0_6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            row.createCell(i+2).setCellValue(v);
            row = sh.getRow(39);
            row.createCell(i+2).setCellValue(ca.getCmPostFlyer6());
            row = sh.getRow(40);
            v = new BigDecimal(ca.getCmHandFlyerHours6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            row.createCell(i+2).setCellValue(v);
            row = sh.getRow(41);
            v = new BigDecimal(ca.getCmOutGpHours6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            row.createCell(i+2).setCellValue(v);
            row = sh.getRow(42);
            row.createCell(i+2).setCellValue(ca.getCmCpBox6());
            row = sh.getRow(43);
            row.createCell(i+2).setCellValue(ca.getCmOutGot6());
            row = sh.getRow(44);
            row.createCell(i+2).setCellValue(ca.getCmInGot6());
            row = sh.getRow(45);
            row.createCell(i+2).setCellValue(ca.getCmBlogGot6());
            row = sh.getRow(47);
            row.createCell(i+2).setCellValue(ca.getCmTotalGot6());
            row = sh.getRow(49);
            row.createCell(i+2).setCellValue(ca.getCmCallIn6());
            row = sh.getRow(50);
            row.createCell(i+2).setCellValue(ca.getCmOutGotCall6());
            row = sh.getRow(51);
            row.createCell(i+2).setCellValue(ca.getCmInGotCall6());
            row = sh.getRow(52);
            row.createCell(i+2).setCellValue(ca.getCmBlogGotCall6());
            row = sh.getRow(53);
            row.createCell(i+2).setCellValue(ca.getCmBagGotCall6());
            row = sh.getRow(55);
            row.createCell(i+2).setCellValue(ca.getCmOwnRefs6());
            row = sh.getRow(56);
            row.createCell(i+2).setCellValue(ca.getCmNewspaper6());
            row = sh.getRow(57);
            row.createCell(i+2).setCellValue(ca.getCmTv6());
            row = sh.getRow(58);
            row.createCell(i+2).setCellValue(ca.getCmInternet6());
            row = sh.getRow(59);
            row.createCell(i+2).setCellValue(ca.getCmSign6());
            row = sh.getRow(60);
            row.createCell(i+2).setCellValue(ca.getCmMate6());
            row = sh.getRow(61);
            row.createCell(i+2).setCellValue(ca.getCmOthers6());
            row = sh.getRow(62);
            row.createCell(i+2).setCellValue(ca.getCmMailAgpIn6());
            row = sh.getRow(63);
            row.createCell(i+2).setCellValue(ca.getCmPostFlyerAgpIn6());
            row = sh.getRow(64);
            row.createCell(i+2).setCellValue(ca.getCmHandFlyerAgpIn6());
            row = sh.getRow(65);
            row.createCell(i+2).setCellValue(ca.getCmCpAgpIn6());
            row = sh.getRow(66);
            row.createCell(i+2).setCellValue(ca.getCmOutAgpOut6());
            row = sh.getRow(67);
            row.createCell(i+2).setCellValue(ca.getCmInAgpOut6());
            row = sh.getRow(68);
            row.createCell(i+2).setCellValue(ca.getCmBlogAgpOut6());
            row = sh.getRow(69);
            row.createCell(i+2).setCellValue(ca.getCmBagAgpOut6());
            row = sh.getRow(70);
            row.createCell(i+2).setCellValue(ca.getCmApoTotal6());
            row = sh.getRow(71);
            cell = row.createCell(i+2);
            cell.setCellValue(ca.getCmInApptRatio6());
            cell.setCellStyle(cellStyle0);
            row = sh.getRow(72);
            cell = row.createCell(i+2);
            cell.setCellValue(ca.getCmOutApptRatio6());
            cell.setCellStyle(cellStyle0);
            row = sh.getRow(76);
            cell = row.createCell(i+2);
            cell.setCellValue(ca.getCmBrAgpRatio6());
            cell.setCellStyle(cellStyle0);
            row = sh.getRow(77);
            row.createCell(i+2).setCellValue(ca.getCmFaSum6());
            row = sh.getRow(78);
            cell = row.createCell(i+2);
            cell.setCellValue(ca.getCmShowRatio6());
            cell.setCellStyle(cellStyle0);
            row = sh.getRow(83);
            row.createCell(i+2).setCellValue(ca.getSalesAch6());
            row = sh.getRow(84);
            row.createCell(i+2).setCellValue(ca.getSalesMonthly6());
            row = sh.getRow(85);
            row.createCell(i+2).setCellValue(ca.getSalesAllPrepay6());
            row = sh.getRow(86);
            row.createCell(i+2).setCellValue(ca.getSalesTotal6());
            row = sh.getRow(87);
            cell = row.createCell(i+2);
            cell.setCellValue(ca.getSalesRatio6());
            cell.setCellStyle(cellStyle0);
            row = sh.getRow(88);
            cell = row.createCell(i+2);
            cell.setCellValue(ca.getSalesAchAppRatio6());
            cell.setCellStyle(cellStyle0);
            row = sh.getRow(113);
            row.createCell(i+2).setCellValue(ca.getClubAch6());
            row = sh.getRow(114);
            row.createCell(i+2).setCellValue(ca.getClubMm6());
            row = sh.getRow(115);
            row.createCell(i+2).setCellValue(ca.getClubApp6());
            row = sh.getRow(116);
            row.createCell(i+2).setCellValue(ca.getClubNs6());
            row = sh.getRow(117);
            row.createCell(i+2).setCellValue(ca.getClubLx6());
        }

        try {
            OutputStream fos = new FileOutputStream(target);
            wb.write(fos);
            fos.close();
        } catch (IOException e) {
            logger.error("", e);
        }
        return new FileSystemResource(target);
    }

    @RequestMapping(value = "/rest/usReport")
    @ResponseBody
    public List<PjSum> findUsReport(@RequestParam("year") int year, @RequestParam("month") int month) {
        logger.info("---findUsReport---year: "+year+", month: "+month);
        return pjSumRepo.findByYearAndMonth(year, month, new Sort(Sort.Direction.ASC, "clubId"));
    }

    @RequestMapping(value = "/rest/US_report_for_Taiwan", produces="application/vnd.ms-excel")
    @ResponseBody
    public FileSystemResource exportUsReport(@RequestParam("year") int year, @RequestParam("month") int month) {
        logger.info("---exportUsReport---year: "+year+", month: "+month);
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
        cell.setCellValue(pjSum.getMonth()+1);
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
        cell.setCellValue(pjSum.getBrOwnRef()+pjSum.getAgpOutApoIn());
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
