package com.curves.franchise.web;

import com.curves.franchise.domain.Ca;
import com.curves.franchise.domain.Club;
import com.curves.franchise.domain.Goal;
import com.curves.franchise.domain.PjSum;
import com.curves.franchise.repository.CaRepository;
import com.curves.franchise.repository.ClubRepository;
import com.curves.franchise.repository.GoalRepository;
import com.curves.franchise.repository.PjSumRepository;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.DecimalFormat;
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

    @RequestMapping(value = "/rest/user", method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(@RequestParam("clubId") int clubId, @RequestParam("name") String name, @RequestParam("owner") String owner) {
        Club club = new Club();
        club.setClubId(clubId);
        club.setName(name);
        club.setOwner(owner);
        try {
            clubRepo.save(club);
        } catch (Exception e) {
            return null;
        }
        return "";
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
    public Map<String, Map<String, String>> findCaAll(@RequestParam("caYear") int caYear, @RequestParam("caMonth") int caMonth) {
        logger.info("---findCaAll---caYear: " + caYear + ", caMonth: " + caMonth);
        return new CaAllHelper().fillCaAllClubs(caRepo, clubRepo, caYear, caMonth);
    }

    @RequestMapping(value = "/rest/CaAll/stat")
    @ResponseBody
    public Map<String, Map<String, String>> findCaAllStat(@RequestParam("caYear") int caYear, @RequestParam("caMonth") int caMonth) {
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

        Map<String, Map<String, String>> itemStatMap = new CaAllHelper().fillCaAllStat(caRepo, caYear, caMonth);
        Map<String, Map<String, String>> clubMap = new CaAllHelper().fillCaAllClubs(caRepo, clubRepo, caYear, caMonth);
        Set<String> keyItem = itemStatMap.keySet();
        int rowIdx = 1;
        for (String item : keyItem) {
            Map<String, String> statMap = itemStatMap.get(item);
            Row row = sh.getRow(rowIdx);
            row.createCell(3, Cell.CELL_TYPE_STRING).setCellValue(formatValue(item, statMap.get("sum")));
            row.createCell(4, Cell.CELL_TYPE_STRING).setCellValue(formatValue(item, statMap.get("avg")));
            row.createCell(5, Cell.CELL_TYPE_STRING).setCellValue(formatValue(item, statMap.get("highest")));
            row.createCell(6, Cell.CELL_TYPE_STRING).setCellValue(formatValue(item, statMap.get("lowest")));
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
            Cell cell = row.createCell(clubCellIdx, Cell.CELL_TYPE_STRING);
            cell.setCellValue(clubName);
            cell.setCellStyle(row.getCell(3).getCellStyle());

            Map<String, String> itemMap = clubMap.get(clubName);
            Set<String> items = itemMap.keySet();
            rowIdx = 1;
            for (String item : items) {
                row = sh.getRow(rowIdx);
                row.createCell(clubCellIdx, Cell.CELL_TYPE_STRING).setCellValue(formatValue(item, itemMap.get(item)));
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

    private String formatValue(String item, String v) {
        float value = Float.parseFloat(v);
        if (item.endsWith("Ratio")) {
            return (int) (value * 100) + "%";
        } else if (item.equals("SvcAvgWo6") || item.equals("SvcMaxWo6") || item.equals("SvcMaxWo6")
                || item.equals("Svc12_6") || item.equals("Svc8to11_6") || item.equals("Svc4to7_6")
                || item.equals("Svc1to3_6") || item.equals("Svc0_6") || item.equals("CmHandFlyerHours6")
                || item.equals("CmOutGpHours6") || item.equals("CmHandHoursPerApo6") || item.equals("CmOutGpHoursPerApo6")) {
            return new DecimalFormat("#.0").format(value);
        } else {
            return (int) (value) + "";
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
    public Map<String, Map<String, Float>> findBenchmarking(@AuthenticationPrincipal UserDetails user,
                                                              @RequestParam("year") int year,
                                                              @RequestParam("month") int month) {
        // NOTE: This user isn't 000000, but a logged on franchisee
        int clubId = Integer.parseInt(user.getUsername());
        logger.info("---findBenchmarking---clubId: "+clubId+", year: "+year+", month: "+month);
        Map<String, Map<String, Float>> valueV = new HashMap<>(17);
        List<Ca> cas = caRepo.findByCaYearAndCaMonth(year, month, new Sort(Sort.Direction.DESC, "clubId"));
        List<PjSum> pjs = pjSumRepo.findByYearAndMonth(year, month, new Sort(Sort.Direction.DESC, "clubId"));
        if (cas.size() > 0) {
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return ca2.getCmPostFlyer6() - ca1.getCmPostFlyer6();
                }
            });
            Map<String, Float> valueX = new HashMap<>(5);
            valueV.put("cmPostFlyer6", valueX);
            valueX.put("max", (float)cas.get(0).getCmPostFlyer6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmPostFlyer6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmPostFlyer6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmPostFlyer6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return ca2.getCmHandFlyer6() - ca1.getCmHandFlyer6();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("cmHandFlyer6", valueX);
            valueX.put("max", (float)cas.get(0).getCmHandFlyer6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmHandFlyer6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmHandFlyer6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmHandFlyer6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return ca2.getCmOutGp6() - ca1.getCmOutGp6();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("cmOutGp6", valueX);
            valueX.put("max", (float)cas.get(0).getCmOutGp6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmOutGp6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmOutGp6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmOutGp6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return (int)(ca2.getCmHandFlyerHours6()*10 - ca1.getCmHandFlyerHours6()*10);
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("cmHandFlyerHours6", valueX);
            valueX.put("max", (float)cas.get(0).getCmHandFlyerHours6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmHandFlyerHours6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmHandFlyerHours6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmHandFlyerHours6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return (int)(ca2.getCmOutGpHours6()*10 - ca1.getCmOutGpHours6()*10);
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("cmOutGpHours6", valueX);
            valueX.put("max", (float)cas.get(0).getCmOutGpHours6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmOutGpHours6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmOutGpHours6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmOutGpHours6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return ca2.getCmCpBox6() - ca1.getCmCpBox6();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("cmCpBox6", valueX);
            valueX.put("max", (float)cas.get(0).getCmCpBox6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmCpBox6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmCpBox6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmCpBox6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return ca2.getCmInAgpOut6() - ca1.getCmInAgpOut6();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("cmInAgpOut6", valueX);
            valueX.put("max", (float)cas.get(0).getCmInAgpOut6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmInAgpOut6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmInAgpOut6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmInAgpOut6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return ca2.getCmOutAgpOut6() - ca1.getCmOutAgpOut6();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("cmOutAgpOut6", valueX);
            valueX.put("max", (float)cas.get(0).getCmOutAgpOut6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmOutAgpOut6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmOutAgpOut6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmOutAgpOut6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return ca2.getCmApoTotal6() - ca1.getCmApoTotal6();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("cmApoTotal6", valueX);
            valueX.put("max", (float)cas.get(0).getCmApoTotal6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmApoTotal6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmApoTotal6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmApoTotal6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return ca2.getCmFaSum6() - ca1.getCmFaSum6();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("cmFaSum6", valueX);
            valueX.put("max", (float)cas.get(0).getCmFaSum6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmFaSum6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmFaSum6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmFaSum6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return ca2.getSalesAch6() - ca1.getSalesAch6();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("salesAch6", valueX);
            valueX.put("max", (float)cas.get(0).getSalesAch6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getSalesAch6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getSalesAch6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getSalesAch6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return ca2.getCmOwnRefs6() - ca1.getCmOwnRefs6();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("cmOwnRefs6", valueX);
            valueX.put("max", (float)cas.get(0).getCmOwnRefs6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getCmOwnRefs6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getCmOwnRefs6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getCmOwnRefs6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
            Collections.sort(cas, new Comparator<Ca>() {
                @Override
                public int compare(Ca ca1, Ca ca2) {
                    return (int)(ca2.getSalesRatio6()*1000 - ca1.getSalesRatio6()*1000);
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("salesRatio6", valueX);
            valueX.put("max", (float)cas.get(0).getSalesRatio6());
            valueX.put("mid", (float)cas.get(cas.size() / 2).getSalesRatio6());
            valueX.put("min", (float)cas.get(cas.size() - 1).getSalesRatio6());
            for (int i = 0; i < cas.size(); i++) {
                if (cas.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)cas.get(i).getSalesRatio6());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)cas.size());
                    break;
                }
            }
        }
        if (pjs.size() > 0) {
            Collections.sort(pjs, new Comparator<PjSum>() {
                @Override
                public int compare(PjSum p1, PjSum p2) {
                    return p2.getNewSalesRevenue() - p1.getNewSalesRevenue();
                }
            });
            Map<String, Float> valueX = new HashMap<>(5);
            valueV.put("newSalesRevenue", valueX);
            valueX.put("max", (float)pjs.get(0).getNewSalesRevenue());
            valueX.put("mid", (float)pjs.get(pjs.size() / 2).getNewSalesRevenue());
            valueX.put("min", (float)pjs.get(pjs.size() - 1).getNewSalesRevenue());
            for (int i = 0; i < pjs.size(); i++) {
                if (pjs.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)pjs.get(i).getNewSalesRevenue());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)pjs.size());
                    break;
                }
            }
            Collections.sort(pjs, new Comparator<PjSum>() {
                @Override
                public int compare(PjSum p1, PjSum p2) {
                    return p2.getDuesDraftsRevenue() - p1.getDuesDraftsRevenue();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("duesDraftsRevenue", valueX);
            valueX.put("max", (float)pjs.get(0).getDuesDraftsRevenue());
            valueX.put("mid", (float)pjs.get(pjs.size() / 2).getDuesDraftsRevenue());
            valueX.put("min", (float)pjs.get(pjs.size() - 1).getDuesDraftsRevenue());
            for (int i = 0; i < pjs.size(); i++) {
                if (pjs.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)pjs.get(i).getDuesDraftsRevenue());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)pjs.size());
                    break;
                }
            }
            Collections.sort(pjs, new Comparator<PjSum>() {
                @Override
                public int compare(PjSum p1, PjSum p2) {
                    return p2.getRevenue() - p1.getRevenue();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("revenue", valueX);
            valueX.put("max", (float)pjs.get(0).getRevenue());
            valueX.put("mid", (float)pjs.get(pjs.size() / 2).getRevenue());
            valueX.put("min", (float)pjs.get(pjs.size() - 1).getRevenue());
            for (int i = 0; i < pjs.size(); i++) {
                if (pjs.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)pjs.get(i).getRevenue());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)pjs.size());
                    break;
                }
            }
            Collections.sort(pjs, new Comparator<PjSum>() {
                @Override
                public int compare(PjSum p1, PjSum p2) {
                    return p2.getProductsRevenue() - p1.getProductsRevenue();
                }
            });
            valueX = new HashMap<>(5);
            valueV.put("productsRevenue", valueX);
            valueX.put("max", (float)pjs.get(0).getProductsRevenue());
            valueX.put("mid", (float)pjs.get(pjs.size() / 2).getProductsRevenue());
            valueX.put("min", (float)pjs.get(pjs.size() - 1).getProductsRevenue());
            for (int i = 0; i < pjs.size(); i++) {
                if (pjs.get(i).getClubId() == clubId) {
                    valueX.put("you", (float)pjs.get(i).getProductsRevenue());
                    valueX.put("rank", (float)i);
                    valueX.put("total", (float)pjs.size());
                    break;
                }
            }
        }

        return valueV;
    }

    private void putValues(int clubId, int cId, float value, Map<String, Float> valueX) {
        float max = valueX.get("max");
        if (value > max) {
            valueX.put("max", value);
        }
        float min = valueX.get("min");
        if (value < min) {
            valueX.put("min", value);
        }
        if (cId == clubId) {
            valueX.put("you", value);
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

    @RequestMapping(value = "/rest/caSummary")
    @ResponseBody
    public List<Ca> findCaSummaryByClub(@RequestParam("clubId") int clubId,
                                        @RequestParam("yStart") int yStart, @RequestParam("mStart") int mStart,
                                        @RequestParam("yEnd") int yEnd, @RequestParam("mEnd") int mEnd) {
        logger.info("---findCaSummaryByClub---clubId: "+clubId+", start: "+yStart+"-"+mStart+", end: "+yEnd+"-"+mEnd);
        return caRepo.findByClubIdAndCaYearBetweenAndCaMonthBetween(clubId, yStart, yEnd, mStart, mEnd);
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
