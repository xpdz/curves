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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class ManagementController {
    private final Logger logger = LoggerFactory.getLogger(ManagementController.class);

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

    @RequestMapping(value = "/rest/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(@RequestParam String clubId, @RequestParam String password) {
        logger.info("---changePassword---clubId: "+clubId);
        User user = userRepo.findByUsername(clubId);
        if (user == null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
        return "";
    }

    @RequestMapping(value = "/rest/reset_password")
    public void resetPassword(@RequestParam("clubId") String clubId) {
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

    @RequestMapping(value = "/rest/benchmarking")
    @ResponseBody
    public Map<String, Float> findBenchmarking(@RequestParam("item") String item, @RequestParam("year") int year, @RequestParam("month") int month) {
        logger.info("---findBenchmarking---item: "+item+", year: "+year+", month: "+month);
        Iterable<Club> clubs = clubRepo.findAll();
        Map<Integer, String> clubIdNameMap = new HashMap<>();
        for (Club club : clubs) {
            clubIdNameMap.put(club.getClubId(), club.getName());
        }
        logger.info("---findBenchmarking---clubIdNameMap.size: "+clubIdNameMap.size());
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
        logger.info("---findBenchmarking---values.size: "+values.size());
        return values;
    }

    @RequestMapping(value = "/rest/PJ_All/export", produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @ResponseBody
    public FileSystemResource exportPjAll(@RequestParam("year") int year, @RequestParam("month") int month) {
        logger.info("---exportPjAll---year: " + year + ", month: " + month);
        Workbook wb;
        String fdl = System.getProperty("user.home") + File.separator + "curves_data";
        File template = new File(fdl + File.separator + "PJ_Summary_Sheet_ALL_Clubs.xlsx");
        File target;
        try {
            target = File.createTempFile("PJ_Summary_Sheet_ALL_Clubs", "xlsx");
            FileUtils.copyFile(template, target);
            wb = WorkbookFactory.create(new FileInputStream(target));
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

        Sheet sh = wb.getSheetAt(0);

        List<PjSum> pjSums = pjSumRepo.findByYearAndMonth(year, month, new Sort(Sort.Direction.ASC, "clubId"));
        Map<Integer, String> clubIdNameMap = getClubIdNameMap();

        int rowIdx = 3;
        for (PjSum pjSum : pjSums) {
            Row row = sh.createRow(rowIdx);
            rowIdx++;
            Cell cell = row.createCell(0, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getClubId());
            cell = row.createCell(1, Cell.CELL_TYPE_STRING);
            cell.setCellValue(clubIdNameMap.get(pjSum.getClubId()));
            cell = row.createCell(2, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getYear());
            cell = row.createCell(3, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getMonth() + 1);
            cell = row.createCell(4, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getEnrolled());
            cell = row.createCell(5, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getLeaves());
            cell = row.createCell(6, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getValids());
            cell = row.createCell(7, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getNewSales());
            cell = row.createCell(8, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getExits());
            cell = row.createCell(9, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getShiftIn());
            cell = row.createCell(10, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getShiftOut());
            cell = row.createCell(11, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getIncrement());
            cell = row.createCell(12, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getRevenue());
            cell = row.createCell(13, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getNewSalesRevenue());
            cell = row.createCell(14, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getDuesDraftsRevenue());
            cell = row.createCell(15, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getProductsRevenue());
            cell = row.createCell(16, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getWheyProteinRevenue());
            cell = row.createCell(17, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getOtherRevenue());
            cell = row.createCell(18, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getIncomingCalls());
            cell = row.createCell(19, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getIncomingApo());
            cell = row.createCell(20, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getOutgoingCalls());
            cell = row.createCell(21, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getOutgoingApo());
            cell = row.createCell(22, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getBrOwnRef());
            cell = row.createCell(23, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getBrOthersRef());
            cell = row.createCell(24, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getBrandedNewspaper());
            cell = row.createCell(25, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getBrandedTv());
            cell = row.createCell(26, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getBrandedInternet());
            cell = row.createCell(27, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getBrandedSign());
            cell = row.createCell(28, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getBrandedMate());
            cell = row.createCell(29, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getBrandedOthers());
            cell = row.createCell(30, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getAgpInDirectMail());
            cell = row.createCell(31, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getAgpInMailFlyer());
            cell = row.createCell(32, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getAgpInHandFlyer());
            cell = row.createCell(33, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getAgpInCp());
            cell = row.createCell(34, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getAgpOutApoOut());
            cell = row.createCell(35, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getAgpOutApoIn());
            cell = row.createCell(36, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getAgpOutApoBlog());
            cell = row.createCell(37, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getAgpOutApoBag());
            cell = row.createCell(38, Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(pjSum.getFaSum());
            cell = row.createCell(39, Cell.CELL_TYPE_NUMERIC);
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat((short) 9);
            cell.setCellStyle(cellStyle);
            cell.setCellValue((pjSum.getEnrollAch() + pjSum.getEnrollAllPrepay()) / pjSum.getNewSales());
            cell = row.createCell(40, Cell.CELL_TYPE_NUMERIC);
            cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat((short) 9);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(pjSum.getEnrollMonthly() / pjSum.getNewSales());
            cell = row.createCell(41, Cell.CELL_TYPE_NUMERIC);
            cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat((short) 9);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(pjSum.getSalesRatio());
            cell = row.createCell(42, Cell.CELL_TYPE_NUMERIC);
            cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(wb.createDataFormat().getFormat("0.0%"));
            cell.setCellStyle(cellStyle);
            cell.setCellValue(pjSum.getExitRatio());
            cell = row.createCell(43, Cell.CELL_TYPE_NUMERIC);
            cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(wb.createDataFormat().getFormat("0.0%"));
            cell.setCellStyle(cellStyle);
            cell.setCellValue(pjSum.getLeaveRatio());
            cell = row.createCell(44, Cell.CELL_TYPE_NUMERIC);
            cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(wb.createDataFormat().getFormat("0.0%"));
            cell.setCellStyle(cellStyle);
            cell.setCellValue((pjSum.getLeaveRatio() + pjSum.getExitRatio()));
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

    @RequestMapping(value = "/rest/PJ_All")
    @ResponseBody
    public Map<String, PjSum> findPjAll(@RequestParam String clubName, @RequestParam int year, @RequestParam int month) {
        logger.info("---findPjAll---clubName: " + clubName+", year: " + year + ", month: " + month);
        Map<String, PjSum> pjAlls = new LinkedHashMap<>();
        if (clubName.length() > 0) {
            Pageable pageable = new PageRequest(0, 100, Sort.Direction.ASC, "clubId");
            Page<Club> clubs = clubRepo.findByNameContaining(clubName, pageable);
            for (Club club : clubs) {
                PjSum pjSum = pjSumRepo.findByClubIdAndYearAndMonth(club.getClubId(), year, month);
                pjAlls.put(club.getName(), pjSum);
            }
        } else {
            List<PjSum> pjSums = pjSumRepo.findByYearAndMonth(year, month, new Sort(Sort.Direction.ASC, "clubId"));
            Map<Integer, String> clubIdNameMap = getClubIdNameMap();
            for (PjSum pjSum : pjSums) {
                pjAlls.put(clubIdNameMap.get(pjSum.getClubId()), pjSum);
            }
        }
        return pjAlls;
    }

    private Map<Integer, String> getClubIdNameMap() {
        List<Club> clubs = clubRepo.findAll();
        Map<Integer, String> clubIdNameMap = new HashMap<>();
        for (Club club : clubs) {
            clubIdNameMap.put(club.getClubId(), club.getName());
        }
        return clubIdNameMap;
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

    @RequestMapping(value = "/rest/CaAll/export", produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @ResponseBody
    public FileSystemResource exportCaAll(@RequestParam("caYear") int caYear, @RequestParam("caMonth") int caMonth) {
        logger.info("---exportCaAll---caYear: " + caYear + ", caMonth: " + caMonth);
        Workbook wb;
        String fdl = System.getProperty("user.home") + File.separator + "curves_data";
        File template = new File(fdl + File.separator + "CA_Summary_Sheet_ALL_Clubs.xlsx");
        File target;
        try {
            target = File.createTempFile("CA_Summary_Sheet_ALL_Clubs", "xlsx");
            FileUtils.copyFile(template, target);
            wb = WorkbookFactory.create(new FileInputStream(target));
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

        Sheet sh = wb.getSheetAt(0);

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
            if (rowIdx == 9 || rowIdx == 41 || rowIdx == 47 || rowIdx == 72) {
                rowIdx++;
            } else if (rowIdx == 28) {
                rowIdx += 2;
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
                if (rowIdx == 9 || rowIdx == 41 || rowIdx == 47 || rowIdx == 72) {
                    rowIdx++;
                } else if (rowIdx == 28) {
                    rowIdx += 2;
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
        if (item.contains("Ratio")) {
            if (item.contains("ExitsRatio") || item.contains("HoldRatio")) {
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setDataFormat(wb.createDataFormat().getFormat("0.0%"));
                cell.setCellStyle(cellStyle);
            } else {
                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setDataFormat((short) 9);
                cell.setCellStyle(cellStyle);
            }
            cell.setCellValue(v.doubleValue());
        } else if (item.equals("Svc12_6") || item.equals("Svc8to11_6") || item.equals("Svc4to7_6")
                || item.equals("Svc1to3_6") || item.equals("Svc0_6")) {
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
            cell.setCellStyle(cellStyle);
            cell.setCellValue(v.doubleValue());
        } else if (item.equals("SvcMaxWo6") || item.equals("SvcAvgWo6") || item.equals("CmHandFlyerHours6")
                || item.equals("CmOutGpHours6") || item.equals("CmHandHoursPerApo6") || item.equals("CmOutGpHoursPerApo6")) {
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setDataFormat(wb.createDataFormat().getFormat("0.0"));
            cell.setCellStyle(cellStyle);
            cell.setCellValue(v.doubleValue());
        } else {
            cell.setCellValue(v.intValue());
        }
    }

    @RequestMapping(value = "/rest/ranking")
    @ResponseBody
    public Map<String, Map<String, Number>> findRanking(@RequestParam int clubId, @RequestParam int year, @RequestParam int month) {
        logger.info("---findRanking---clubId: " + clubId + ", year: " + year + ", month: " + month);
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

    private void fillCaItem(int clubId, List<Ca> cas, String item, Map<String, Map<String, Number>> valueV) {
        try {
            final Method xMethod = sortCaBy(cas, item, false);
            Map<String, Number> valueX = new HashMap<>(5);
            valueV.put(item, valueX);
            valueX.put("max", (Number)xMethod.invoke(cas.get(0)));
            valueX.put("mid", (Number)xMethod.invoke(cas.get(cas.size() / 2)));
            valueX.put("min", (Number)xMethod.invoke(cas.get(cas.size() - 1)));
            int factor = 10;
            if (cas.size() < 10) {
                factor = 1;
            }
            double halfHigh = 0.0;
            boolean bFoundYou = false;
            for (int i = 0; i < cas.size(); i++) {
                Number n = (Number)xMethod.invoke(cas.get(i));
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", n);
                    valueX.put("rank", 1 + i * factor / cas.size());
                    bFoundYou = true;
                }
                if (i < cas.size()/2) {
                    halfHigh += n.doubleValue();
                } else if (bFoundYou) {
                    break;
                }
            }
            valueX.put("halfHigh", halfHigh / cas.size() / 2);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private Method sortCaBy(List<Ca> cas, String item, final boolean isAsc) throws NoSuchMethodException {
        final Method xMethod = Ca.class.getDeclaredMethod("get" + item);
        Collections.sort(cas, new Comparator<Ca>() {
            @Override
            public int compare(Ca ca1, Ca ca2) {
                int x = 0;
                try {
                    x = (int)(((Number) xMethod.invoke(ca1)).floatValue() - ((Number) xMethod.invoke(ca2)).floatValue())*1000;
                } catch (Exception e) {
                    logger.error("", e);
                }
                return isAsc ? -x : x;
            }
        });
        return xMethod;
    }

    private void fillPjItem(int clubId, List<PjSum> pjSums, String item, Map<String, Map<String, Number>> valueV) {
        try {
            final Method xMethod = sortPjBy(pjSums, item, false);
            Map<String, Number> valueX = new HashMap<>(5);
            valueV.put(item, valueX);
            valueX.put("max", (Number)xMethod.invoke(pjSums.get(0)));
            valueX.put("mid", (Number)xMethod.invoke(pjSums.get(pjSums.size() / 2)));
            valueX.put("min", (Number)xMethod.invoke(pjSums.get(pjSums.size() - 1)));
            int factor = 10;
            if (pjSums.size() < 10) {
                factor = 1;
            }
            double halfHigh = 0.0;
            boolean bFoundYou = false;
            for (int i = 0; i < pjSums.size(); i++) {
                Number n = (Number)xMethod.invoke(pjSums.get(i));
                if (pjSums.get(i).getClubId() == clubId) {
                    valueX.put("you", n);
                    valueX.put("rank", 1 + i * factor / pjSums.size());
                    bFoundYou = true;
                }
                if (i < pjSums.size()/2) {
                    halfHigh += n.doubleValue();
                } else if (bFoundYou) {
                    break;
                }
            }
            valueX.put("halfHigh", halfHigh / pjSums.size() / 2);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private Method sortPjBy(List<PjSum> pjSums, final String item, final boolean isAsc) throws NoSuchMethodException {
        final Method xMethod = PjSum.class.getDeclaredMethod("get" + item);
        Collections.sort(pjSums, new Comparator<PjSum>() {
            @Override
            public int compare(PjSum pjSum1, PjSum pjSum2) {
                int x = 0;
                try {
                    x = (int) (((Number) xMethod.invoke(pjSum1)).floatValue() - ((Number) xMethod.invoke(pjSum2)).floatValue())*1000;
                } catch (Exception e) {
                    logger.error("", e);
                }
                return isAsc ? x : -x;
            }
        });
        return xMethod;
    }

    @RequestMapping(value = "/rest/ranking_all")
    @ResponseBody
    public DtReturn findRankingAll(@RequestParam int year, @RequestParam int month, @RequestParam int draw,
                                   @RequestParam("order[0][column]") int colIndex, @RequestParam("order[0][dir]") String dir) {
        final String[] sortBys = {"", "MaxWorkOuts", "NewSalesRevenue", "DuesDraftsRevenue", "ProductsRevenue",
                "EnrollAch", "EnrollMonthly", "EnrollAllPrepay", "IncomingCalls", "IncomingApo", "OutgoingCalls",
                "OutgoingApo", "BrOwnRef", "BrandedInternet", "BrandedSign", "agpInFlyer", "agp", "Enrolled",
                "Valids", "ExitRatio", "LeaveRatio", "NewSales", "Exits", "Increment", "FaSum", "CmInApptRatio6",
                "CmShowRatio6", "SalesRatio6", "SalesAchAppRatio6", "CmBrAgpRatio6"};
        boolean isAsc = !"asc".equals(dir); // reverse it for UI display
        logger.info("---findRankingAll---year: "+year+", month: "+month+", sortBy: "+sortBys[colIndex]+", isAsc: "+isAsc);

        List<PjSum> pjSums = pjSumRepo.findByYearAndMonth(year, month);
        List<Ca> cas = caRepo.findByCaYearAndCaMonth(year, month);

        Map<Integer, List<String>> rankingAll = new LinkedHashMap<>(pjSums.size()+3);
        if (pjSums.size() == 0 || cas.size() == 0) {
            logger.error("PJ or CA is empty for this month, PJ-size: "+pjSums.size()+", CA-size: "+cas.size());
            return new DtReturn(draw, rankingAll.size(), rankingAll.size(), rankingAll.values(), "");
        }

        List<String> rowMax = new ArrayList<>(30);
        rowMax.add("Max.");
        rankingAll.put(1, rowMax);
        List<String> rowAvg = new ArrayList<>(30);
        rowAvg.add("Avg.");
        rankingAll.put(2, rowAvg);
        List<String> row50Avg = new ArrayList<>(30);
        row50Avg.add(">50% Avg.");
        rankingAll.put(3, row50Avg);

        Map<Integer, String> clubIdNameMap = getClubIdNameMap();
        if (colIndex > 24) {
            try {sortCaBy(cas, sortBys[colIndex], isAsc);} catch (NoSuchMethodException e) {logger.error("", e);}
            for (Ca ca : cas) {
                List<String> rowClub = new ArrayList<>(30);
                rowClub.add(clubIdNameMap.get(ca.getClubId()));
                rankingAll.put(ca.getClubId(), rowClub);
            }
        } else {
            if (colIndex == 15 || colIndex == 16) {
                sortByPj2(pjSums, sortBys[colIndex], isAsc);
            } else {
                try {sortPjBy(pjSums, sortBys[colIndex], isAsc);} catch (NoSuchMethodException e) {logger.error("", e);}
            }
            for (PjSum pjSum : pjSums) {
                List<String> rowClub = new ArrayList<>(30);
                rowClub.add(clubIdNameMap.get(pjSum.getClubId()));
                rankingAll.put(pjSum.getClubId(), rowClub);
            }
        }

        for (int i = 1; i < sortBys.length; i++) {
            if (i == 15 || i == 16) {
                rankPj2(sortBys[i], rankingAll, pjSums);
            } else if (i > 24) {
                rankCa(sortBys[i], rankingAll, cas);
            } else {
                rankPj(sortBys[i], rankingAll, pjSums);
            }
        }

        return new DtReturn(draw, rankingAll.size(), rankingAll.size(), rankingAll.values(), "");
    }

    private void rankPj(String item, Map<Integer, List<String>> rankingAll, List<PjSum> pjSums) {
        try {
            final Method xMethod = sortPjBy(pjSums, item, false);
            Number nMax = (Number) xMethod.invoke(pjSums.get(0));
            Number nMin = (Number) xMethod.invoke(pjSums.get(pjSums.size() - 1));
            float step = (nMax.floatValue() - nMin.floatValue()) / 10;
            float sumAll = 0, sumHalf = 0;
            for (int i = 0; i < pjSums.size(); i++) {
                PjSum pjSum = pjSums.get(i);
                List<String> rowClub = rankingAll.get(pjSum.getClubId());
                float v = ((Number) xMethod.invoke(pjSum)).floatValue();
                int level = (int)Math.ceil((nMax.floatValue() - v)/step);
                rowClub.add(level == 0 ? "1" : ""+level);
                sumAll += v;
                if (i < pjSums.size() / 2.0) {
                    sumHalf += v;
                }
            }

            List<String> rowMax = rankingAll.get(1);
            List<String> rowAvg = rankingAll.get(2);
            List<String> row50Avg = rankingAll.get(3);
            if ("ExitRatio".equals(item) || "LeaveRatio".equals(item)) {
                rowMax.add(Math.round(nMax.floatValue()*100)+"%");
                rowAvg.add(Math.round(nMax.floatValue()*100)+"%");
                row50Avg.add(Math.round(nMax.floatValue() * 100) + "%");
            } else {
                rowMax.add(""+nMax.intValue());
                rowAvg.add(""+Math.round(sumAll / pjSums.size()));
                row50Avg.add("" + Math.round(sumHalf / pjSums.size() / 2.0));
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    // process combined fields
    private void rankPj2(final String item, Map<Integer, List<String>> rankingAll, List<PjSum> pjSums) {
        try {
            sortByPj2(pjSums, item, false);
            int nMax = -0xFFFF, nMin = 0xFFFF;
            PjSum pMax = pjSums.get(0);
            PjSum pMin = pjSums.get(pjSums.size() - 1);
            if ("agpInFlyer".equals(item)) {
                nMax = pMax.getAgpInHandFlyer() + pMax.getAgpInMailFlyer();
                nMin = pMin.getAgpInHandFlyer() + pMin.getAgpInMailFlyer();
            } else if ("agp".equals(item)) {
                nMax = pMax.getAgpInCp() + pMax.getAgpOutApoBlog() + pMax.getAgpOutApoBag();
                nMin = pMin.getAgpInCp() + pMin.getAgpOutApoBlog() + pMin.getAgpOutApoBag();
            }
            double step = (nMax - nMin) / 10.0;
            float sumAll = 0, sumHalf = 0;
            for (int i = 0; i < pjSums.size(); i++) {
                PjSum pjSum = pjSums.get(i);
                List<String> rowClub = rankingAll.get(pjSum.getClubId());
                float v = 0;
                if ("agpInFlyer".equals(item)) {
                    v = pjSum.getAgpInHandFlyer() + pjSum.getAgpInMailFlyer();
                } else if ("agp".equals(item)) {
                    v = pjSum.getAgpInCp() + pjSum.getAgpOutApoBlog() + pjSum.getAgpOutApoBag();
                }
                int level = (int)Math.ceil((nMax - v)/step);
                rowClub.add(level == 0 ? "1" : ""+level);
                sumAll += v;
                if (i < pjSums.size() / 2.0) {
                    sumHalf += v;
                }
            }

            List<String> rowMax = rankingAll.get(1);
            rowMax.add(""+nMax);
            List<String> rowAvg = rankingAll.get(2);
            rowAvg.add(""+Math.round(sumAll / pjSums.size()));
            List<String> rowMidAvg = rankingAll.get(3);
            rowMidAvg.add(""+Math.round(sumHalf / pjSums.size() / 2.0));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private void sortByPj2(List<PjSum> pjSums, final String item, final boolean isAsc) {
        Collections.sort(pjSums, new Comparator<PjSum>() {
            @Override
            public int compare(PjSum pjSum1, PjSum pjSum2) {
                int x = 0;
                try {
                    if ("agpInFlyer".equals(item)) {
                        x = pjSum1.getAgpInHandFlyer() + pjSum1.getAgpInMailFlyer() - pjSum2.getAgpInHandFlyer() - pjSum2.getAgpInMailFlyer();
                    } else if ("agp".equals(item)) {
                        x = pjSum1.getAgpInCp() + pjSum1.getAgpOutApoBlog() + pjSum1.getAgpOutApoBag() - pjSum2.getAgpInCp() - pjSum2.getAgpOutApoBlog() - pjSum2.getAgpOutApoBag();
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
                return isAsc ? -x : x;
            }
        });
    }

    private void rankCa(String item, Map<Integer, List<String>> rankingAll, List<Ca> cas) {
        try {
            final Method xMethod = sortCaBy(cas, item, false);
            Number nMax = (Number) xMethod.invoke(cas.get(0));
            Number nMin = (Number) xMethod.invoke(cas.get(cas.size() - 1));
            float step = (nMax.floatValue() - nMin.floatValue()) / 10;
            float sumAll = 0, sumHalf = 0;
            for (int i = 0; i < cas.size(); i++) {
                Ca ca = cas.get(i);
                List<String> rowClub = rankingAll.get(ca.getClubId());
                float v = ((Number) xMethod.invoke(ca)).floatValue();
                int level = (int)Math.ceil((nMax.floatValue() - v)/step);
                rowClub.add(level == 0 ? "1" : ""+level);
                sumAll += v;
                if (i < cas.size() / 2.0) {
                    sumHalf += v;
                }
            }

            List<String> rowMax = rankingAll.get(1);
            rowMax.add(Math.round(nMax.floatValue()*100)+"%");
            List<String> rowAvg = rankingAll.get(2);
            rowAvg.add(Math.round(sumAll*100 / cas.size())+"%");
            List<String> rowMidAvg = rankingAll.get(3);
            rowMidAvg.add(Math.round(sumHalf*100 / Math.round(cas.size() / 2.0))+"%");
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

        logger.info("---findTrendsByClub---CAs.size="+cas.size()+", PjSums.size="+pjSums.size());
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
        Workbook wb;
        String fdl = System.getProperty("user.home") + File.separator + "curves_data";
        File template = new File(fdl + File.separator + "PJ_summary.xlsx");
        File target;
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
            row.createCell(18).setCellValue(pjSums.get(i).getWheyProteinRevenue());
            row.createCell(19).setCellValue(pjSums.get(i).getOtherRevenue());
            row.createCell(20).setCellValue(pjSums.get(i).getFaSum());
            row.createCell(21).setCellValue(pjSums.get(i).getEnrollAch());
            row.createCell(22).setCellValue(pjSums.get(i).getEnrollMonthly());
            row.createCell(23).setCellValue(pjSums.get(i).getEnrollAllPrepay());
            row.createCell(24).setCellValue(pjSums.get(i).getExits());
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
        Workbook wb;
        String fdl = System.getProperty("user.home") + File.separator + "curves_data";
        File template = new File(fdl + File.separator + "CA_summary.xlsx");
        File target;
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
            int idx = i + 2;
            Ca ca = cas.get(i);
            sh.getRow(0).createCell(idx).setCellValue(ca.getCaYear() + "-" + (ca.getCaMonth() + 1));
            sh.getRow(13).createCell(idx).setCellValue(ca.getSvcTm6());
            sh.getRow(14).createCell(idx).setCellValue(ca.getSvcShiftOut6());
            sh.getRow(15).createCell(idx).setCellValue(ca.getSvcShiftIn6());
            sh.getRow(16).createCell(idx).setCellValue(ca.getSvcHold6());
            sh.getRow(17).createCell(idx).setCellValue(ca.getSvcActive6());
            Cell cell = sh.getRow(18).createCell(i + 2);
            cell.setCellValue(ca.getSvcHoldRatio6());
            cell.setCellStyle(cellStyle1);
            sh.getRow(19).createCell(idx).setCellValue(ca.getSvcTotalWo6());
            float v = BigDecimal.valueOf(ca.getSvcAvgWo6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            sh.getRow(20).createCell(idx).setCellValue(v);
            cell = sh.getRow(21).createCell(idx);
            cell.setCellValue(ca.getSvcMaxWo6());
            cell.setCellStyle(cellStyle0);
            sh.getRow(22).createCell(idx).setCellValue(ca.getSvcExits6());
            cell = sh.getRow(23).createCell(idx);
            cell.setCellValue(ca.getSvcExitsRatio6());
            cell.setCellStyle(cellStyle1);
            sh.getRow(24).createCell(idx).setCellValue(ca.getSvcMeasure6());
            cell = sh.getRow(25).createCell(idx);
            cell.setCellValue(ca.getSvcMeasureRatio6());
            cell.setCellStyle(cellStyle0);
            v = BigDecimal.valueOf(ca.getSvc12_6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            sh.getRow(26).createCell(idx).setCellValue(v);
            v = BigDecimal.valueOf(ca.getSvc8to11_6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            sh.getRow(27).createCell(idx).setCellValue(v);
            v = BigDecimal.valueOf(ca.getSvc4to7_6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            sh.getRow(28).createCell(idx).setCellValue(v);
            v = BigDecimal.valueOf(ca.getSvc1to3_6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            sh.getRow(29).createCell(idx).setCellValue(v);
            v = BigDecimal.valueOf(ca.getSvc0_6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            sh.getRow(30).createCell(idx).setCellValue(v);
            sh.getRow(41).createCell(idx).setCellValue(ca.getCmPostFlyer6());
            sh.getRow(42).createCell(idx).setCellValue(ca.getCmHandFlyer6());
            v = BigDecimal.valueOf(ca.getCmHandFlyerHours6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            sh.getRow(43).createCell(idx).setCellValue(v);
            v = BigDecimal.valueOf(ca.getCmOutGpHours6()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            sh.getRow(44).createCell(idx).setCellValue(v);
            sh.getRow(45).createCell(idx).setCellValue(ca.getCmOutGp6());
            sh.getRow(46).createCell(idx).setCellValue(ca.getCmCpBox6());
            sh.getRow(47).createCell(idx).setCellValue(ca.getCmOutGot6());
            sh.getRow(48).createCell(idx).setCellValue(ca.getCmInGot6());
            sh.getRow(49).createCell(idx).setCellValue(ca.getCmBlogGot6());
            sh.getRow(50).createCell(idx).setCellValue(ca.getCmBagGot6());
            sh.getRow(51).createCell(idx).setCellValue(ca.getCmTotalGot6());
            sh.getRow(53).createCell(idx).setCellValue(ca.getCmCallIn6());
            sh.getRow(54).createCell(idx).setCellValue(ca.getCmOutGotCall6());
            sh.getRow(55).createCell(idx).setCellValue(ca.getCmInGotCall6());
            sh.getRow(56).createCell(idx).setCellValue(ca.getCmBlogGotCall6());
            sh.getRow(57).createCell(idx).setCellValue(ca.getCmBagGotCall6());
            sh.getRow(59).createCell(idx).setCellValue(ca.getCmOwnRefs6());
            sh.getRow(60).createCell(idx).setCellValue(ca.getCmOtherRefs6());
            sh.getRow(61).createCell(idx).setCellValue(ca.getCmNewspaper6());
            sh.getRow(62).createCell(idx).setCellValue(ca.getCmTv6());
            sh.getRow(63).createCell(idx).setCellValue(ca.getCmInternet6());
            sh.getRow(64).createCell(idx).setCellValue(ca.getCmSign6());
            sh.getRow(65).createCell(idx).setCellValue(ca.getCmMate6());
            sh.getRow(66).createCell(idx).setCellValue(ca.getCmOthers6());
            sh.getRow(67).createCell(idx).setCellValue(ca.getCmMailAgpIn6());
            sh.getRow(68).createCell(idx).setCellValue(ca.getCmPostFlyerAgpIn6());
            sh.getRow(69).createCell(idx).setCellValue(ca.getCmHandFlyerAgpIn6());
            sh.getRow(70).createCell(idx).setCellValue(ca.getCmCpAgpIn6());
            sh.getRow(71).createCell(idx).setCellValue(ca.getCmOutAgpOut6());
            sh.getRow(72).createCell(idx).setCellValue(ca.getCmInAgpOut6());
            sh.getRow(73).createCell(idx).setCellValue(ca.getCmBlogAgpOut6());
            sh.getRow(74).createCell(idx).setCellValue(ca.getCmBagAgpOut6());
            sh.getRow(75).createCell(idx).setCellValue(ca.getCmApoTotal6());
            cell = sh.getRow(76).createCell(idx);
            cell.setCellValue(ca.getCmInApptRatio6());
            cell.setCellStyle(cellStyle0);
            cell = sh.getRow(77).createCell(idx);
            cell.setCellValue(ca.getCmOutApptRatio6());
            cell.setCellStyle(cellStyle0);
            cell = sh.getRow(81).createCell(idx);
            cell.setCellValue(ca.getCmBrAgpRatio6());
            cell.setCellStyle(cellStyle0);
            sh.getRow(82).createCell(idx).setCellValue(ca.getCmFaSum6());
            cell = sh.getRow(83).createCell(idx);
            cell.setCellValue(ca.getCmShowRatio6());
            cell.setCellStyle(cellStyle0);
            sh.getRow(88).createCell(idx).setCellValue(ca.getSalesAch6());
            sh.getRow(89).createCell(idx).setCellValue(ca.getSalesMonthly6());
            sh.getRow(90).createCell(idx).setCellValue(ca.getSalesAllPrepay6());
            sh.getRow(91).createCell(idx).setCellValue(ca.getSalesTotal6());
            cell = sh.getRow(92).createCell(idx);
            cell.setCellValue(ca.getSalesRatio6());
            cell.setCellStyle(cellStyle0);
            cell = sh.getRow(93).createCell(idx);
            cell.setCellValue(ca.getSalesAchAppRatio6());
            cell.setCellStyle(cellStyle0);
            sh.getRow(118).createCell(idx).setCellValue(ca.getClubAch6());
            sh.getRow(119).createCell(idx).setCellValue(ca.getClubMm6());
            sh.getRow(120).createCell(idx).setCellValue(ca.getClubApp6());
            sh.getRow(121).createCell(idx).setCellValue(ca.getClubNs6());
            sh.getRow(122).createCell(idx).setCellValue(ca.getClubLx6());
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
        assert tmp != null;
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
class DtReturn {
    public DtReturn(int draw, int recordsTotal, int recordsFiltered, Collection<List<String>> data, String error) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
        this.error = error;
    }
    int draw;
    int recordsTotal;
    int recordsFiltered;
    Collection<List<String>> data;
    String error;

    public int getDraw() {
        return draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public Collection<List<String>> getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
