package com.curves.franchise.util;

import com.curves.franchise.domain.Club;
import com.curves.franchise.repository.CaRepository;
import com.curves.franchise.repository.ClubRepository;
import com.curves.franchise.repository.PjSumRepository;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurvesParser {
    private Logger logger = LoggerFactory.getLogger(CurvesParser.class);

    Pattern chinesePattern = Pattern.compile("([\\u4e00-\\u9fa5]+)");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Map<String, String> invalidSheets = new HashMap<>();
    List<File> allOthers = new ArrayList<>();
    Map<String, Integer> nameId = new HashMap<>();
    int year, month;
    String dir = null;

    CaRepository caRepo;
    PjSumRepository pjSumRepo;
    ClubRepository clubRepo;

    FormulaEvaluator evaluator = null;

    public CurvesParser(PjSumRepository pjSumRepo, CaRepository caRepo, ClubRepository clubRepo, String dir) {
        this.pjSumRepo = pjSumRepo;
        this.caRepo = caRepo;
        this.clubRepo = clubRepo;
        this.dir = dir;
    }

    public void process() {
        buildClubNameIdMap();

        String home = System.getProperty("user.home");
        String fdl = home + File.separator + "curves_data";
        logger.info("user.home + curves data folder: " + fdl);
        if ("ALL".equals(dir)) {
            // ?dir=ALL
            for (int y = 2011; y < 2015; y++) {
                this.year = y;
                for (int m = 0; m < 12; m++) {
                    this.month = m;
                    String folder = fdl + File.separator + y + File.separator + y + (m < 9 ? "0" + (m+1) : (m+1));
                    logger.info("---ALL "+y+"-"+(m+1)+" enter folder---"+folder);
                    processWorkbooks(FileUtils.listFiles(new File(folder), null, true));
                }
            }
        } else if (dir.equals("TEST")) {
            fdl += File.separator + "test";
            // ?dir=TEST
            for (int y = 2011; y < 2015; y++) {
                this.year = y;
                for (int m = 0; m < 12; m++) {
                    this.month = m;
                    logger.info("---TEST "+y+"-"+(m+1)+" enter folder---"+fdl+"");
                    processWorkbooks(FileUtils.listFiles(new File(fdl), null, true));
                }
            }
        } else if (dir.startsWith("TEST")) {
            fdl += File.separator + "test";
            // ?dir=TEST-201310
            String[] ym = dir.split("-");
            this.year = Integer.parseInt(ym[ym.length - 1].substring(0, 4));
            this.month = Integer.parseInt(ym[ym.length - 1].substring(4, 6)) - 1;
            logger.info("---TEST-"+ym[ym.length - 1]+" enter folder---"+fdl);
            processWorkbooks(FileUtils.listFiles(new File(fdl), null, true));
        } else {
            // ?dir=201408
            String[] ym = dir.split("-");
            String y = ym[ym.length - 1].substring(0, 4);
            String m = ym[ym.length - 1].substring(4, 6);
            this.year = Integer.parseInt(y);
            this.month = Integer.parseInt(m) - 1;
            fdl += File.separator + y + File.separator + y + m;
            logger.info("---"+ym[ym.length - 1]+" enter folder---"+fdl);
            processWorkbooks(FileUtils.listFiles(new File(fdl), null, true));
        }

        logger.info("=======others================================");
        for (File f : allOthers) {
            logger.info(">>>Invalid files<<<"+f);
        }
        for (String s : invalidSheets.keySet()) {
            logger.info("***Invalid sheets***"+s+" --- "+invalidSheets.get(s));
        }
    }

    private void processWorkbooks(Collection<File> allFiles) {
        for (File f : allFiles) {
            int clubId = -1;
            Workbook wb = null;
            try {
                wb = WorkbookFactory.create(f);
                clubId = getIdByName(parseChinese(f.getName()));
            } catch (Exception e) {
                allOthers.add(f);
                continue;
            }

            logger.info("--- open file ---"+f);

            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sh = wb.getSheetAt(i);
                if (processSheets(f, clubId, wb, sh)) {
                    break;
                }
            }
            try {
                wb.close();
            } catch (Exception e) {
            }
        }
    }

    private boolean processSheets(File f, int clubId, Workbook wb, Sheet sh) {
        String sheetName = sh.getSheetName();
        int rows = sh.getLastRowNum();
        if (rows <= 0) {
            return false;
        } else if (rows == 65535) {
            rows = 45;
        }
        try {
            int columns = sh.getRow(0).getLastCellNum();
            if (rows > 150 && rows < 170 && columns > 10 && columns < 15) {
                if (clubId == -1) {
                    String clubName = sh.getRow(0).getCell(2).getStringCellValue();
                    clubId = getIdByName(clubName);
                }
                if (clubId == -1) {
                    invalidSheets.put(sheetName, f.getPath());
                    return false;
                }
                boolean bTimeMatch = false;
                try {
                    int yIdx = sheetName.indexOf("" + year);
                    if (yIdx != -1) {
                        int mIdx = sheetName.substring(yIdx).indexOf("" + (month + 1));
                        if (mIdx != -1) {
                            bTimeMatch  = true;
                        }
                    }
                    if (!bTimeMatch) {
                        Calendar c = Calendar.getInstance();
                        c.setTime(sh.getRow(0).getCell(7).getDateCellValue());
                        if (c.get(Calendar.YEAR) == year && c.get(Calendar.MONTH) == month) {
                            bTimeMatch = true;
                        }
                    }
                } catch (Exception e) {
                }
                if (bTimeMatch) {
                    logger.info("*** Found CA *** sheet: " + sheetName + ", clubId: " + clubId);
                    evaluator = wb.getCreationHelper().createFormulaEvaluator();
                    return new CaDataHandler(this).processCA(sh, evaluator, clubId);
                } else {
                    invalidSheets.put(sheetName, f.getPath());
                }
            } else if (rows > 38 && rows < 50 && columns < 45 && columns > 35) {
                boolean bTimeMatch = false;
                Calendar c = Calendar.getInstance();
                c.setTime(sh.getRow(15).getCell(0).getDateCellValue());
                if (c.get(Calendar.YEAR) == year && c.get(Calendar.MONTH) == month) {
                    bTimeMatch = true;
                }

                if (clubId == -1) {
                    clubId = (int)sh.getRow(1).getCell(0).getNumericCellValue();
                }

                if (bTimeMatch && clubId > 0) {
                    logger.info("### Found PJ ### sheet: "+sheetName+", clubId: "+clubId);
                    evaluator = wb.getCreationHelper().createFormulaEvaluator();
                    return new PjDataHandler(this).processPJ(sh, evaluator, clubId);
                } else {
                    invalidSheets.put(sheetName, f.getPath());
                }
            } else {
                invalidSheets.put(sheetName, f.getPath());
            }
        } catch (Exception e) {
            logger.error(">>> ERROR <<< file: " + f + ", sheet: " + sheetName, e);
        }
        return false;
    }

    int getCellIntValue(Sheet sh, int row, int col) {
        int value = 0;
        try {
            Cell cell = sh.getRow(row).getCell(col);
            if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                CellValue cellValue = evaluator.evaluate(cell);
                value = (int)cellValue.getNumberValue();
            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                value = (int)cell.getNumericCellValue();
            }
        } catch (Exception e) {
        }
        return value;
    }

    String parseChinese(String s) {
        Matcher mat = chinesePattern.matcher(s);
        if (mat.find()) {
            return mat.group(0);
        }
        return "";
    }

    String replaceChinese(String src, String target) {
        Matcher mat = chinesePattern.matcher(src);
        if (mat.find()) {
            return mat.replaceAll(target);
        }
        return "";
    }

    String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return sdf.format(cell.getDateCellValue());
                } else {
                    return "" + cell.getNumericCellValue();
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return "" + cell.getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                CellValue cellValue = evaluator.evaluate(cell);
                if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    double d = cellValue.getNumberValue();
                    if (d < 1 && d > 0) {
                        return ((int)(d * 100)) + "%";
                    }
                } else if (cellValue.getCellType() == Cell.CELL_TYPE_ERROR) {
                    return null;
                }
                return cellValue.formatAsString();
            default:
                return "";
        }
    }

    void buildClubNameIdMap() {
        try {
            Iterable<Club> clubs = clubRepo.findAll();
            for (Club club : clubs) {
                nameId.put(club.getName(), club.getClubId());
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    int getIdByName(String clubName) {
        Set<String> keys = nameId.keySet();
        for (String key : keys) {
            if (key.contains(clubName)) {
                return nameId.get(key);
            }
        }
        return -1;
    }
}
